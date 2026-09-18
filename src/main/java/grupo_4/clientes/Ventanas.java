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
        throws Exception


}
