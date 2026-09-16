package ni.edu.uam.grupo_4;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import ni.edu.uam.grupo_4.util.AlertUtil;
import ni.edu.uam.grupo_4.util.Navigator;
import ni.edu.uam.grupo_4.util.View;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Navigator.initialize(stage);
        stage.setOnCloseRequest(event -> {
            event.consume();
            if (AlertUtil.confirm("Salir", "¿Desea cerrar la aplicación?")) {
                Platform.exit();
            }
        });
        Navigator.goTo(View.LOGIN);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
