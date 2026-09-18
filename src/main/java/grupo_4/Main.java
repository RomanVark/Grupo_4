package grupo_4;

import grupo_4.clientes.Ventanas;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/grupo_4/view/login.fxml"));
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
