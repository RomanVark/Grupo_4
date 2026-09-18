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

public final class Ventanas {
    private Ventanas() { }

    public static <T> Stage Cargar(String archivo, String titulo, Window owner, Consumer<T> configurar)
        throws IOException {
        URL recurso = Ventanas.class.getResource("/grupo_4/view/" + archivo);
        if (recurso == null) {
            throw new IOException("No se pudo encontrar el recurso: " + archivo);
        }
        FXMLLoader = loader = new FXMLLoader(recurso);
        Parent root = loader.load();
        T controller = loader.getController();
        if (configurar != null) configurar.accept(controller); // recibe los datos antes de mostrar la ventana
        Stage stage = new Stage();
        if (owner != null) {
            stage.initOwner(owner);
            stage.initModality(Modality.WINDOW_MODAL);
        }

    }


}
