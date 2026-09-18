package grupo_4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        URL fxmlLocation = Main.class.getResource(
                "/grupo_4/view/login.fxml"
        );

        Objects.requireNonNull(
                fxmlLocation,
                "No se encontró /grupo_4/view/login.fxml"
        );

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(loader.load());

        stage.setTitle("Sistema de gestión de clientes");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}