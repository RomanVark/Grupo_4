package grupo_4.clientes;

import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main  extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("login.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Gestión de clientes | Acceso");
        stage.setResizable(false);
        Ventanas.confirmarCierre(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
