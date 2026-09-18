package grupo_4.clientes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

// Ayuda pequeña para no repetir FXMLLoader, Stage y Alert en cada controlador.
public final class Ventanas {
    private Ventanas() { }

    public static <T> Stage cargar(String archivo, String titulo, Window owner,
                                   Consumer<T> configurar) throws IOException {
        URL recurso = Ventanas.class.getResource("/grupo_4/view/" + archivo);
        if (recurso == null) {
            throw new IOException("No se encontró el archivo " + archivo);
        }
        FXMLLoader loader = new FXMLLoader(recurso);
        Parent root = loader.load();
        T controller = loader.getController();
        if (configurar != null) configurar.accept(controller); // El controlador recibe los datos antes de mostrar la ventana.
        Stage stage = new Stage();
        if (owner != null) {
            stage.initOwner(owner);
            stage.initModality(Modality.WINDOW_MODAL);
        }
        stage.setTitle(titulo);
        stage.setScene(new Scene(root));
        return stage;
    }

    public static void alerta(Window owner, Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);
        if (owner != null) alert.initOwner(owner);
        alert.setTitle("Gestión de clientes");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static boolean confirmar(Window owner, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, mensaje,
                ButtonType.YES, ButtonType.NO);
        alert.initOwner(owner);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        return alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }

    public static void confirmarCierre(Stage stage) {
        stage.setOnCloseRequest(event -> {
            if (!confirmar(stage, "¿Deseas salir? Los registros en memoria se perderán.")) {
                event.consume();
            }
        });
    }

    public static void salir(Stage stage) {
        if (confirmar(stage, "¿Deseas salir? Los registros en memoria se perderán.")) {
            stage.close();
        }
    }
}
