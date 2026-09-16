package ni.edu.uam.grupo_4.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ni.edu.uam.grupo_4.App;
import ni.edu.uam.grupo_4.controller.ClientDetailController;
import ni.edu.uam.grupo_4.model.Client;

import java.io.IOException;

public final class Navigator {
    private static final String VIEW_BASE = "/ni/edu/uam/grupo_4/view/";
    private static final String STYLE = "/ni/edu/uam/grupo_4/css/application.css";
    private static Stage primaryStage;

    private Navigator() {
    }

    public static void initialize(Stage stage) {
        primaryStage = stage;
        primaryStage.setMinWidth(820);
        primaryStage.setMinHeight(560);
    }

    public static void goTo(View view) {
        ensureInitialized();
        try {
            Parent root = loader(view.getFxml()).load();
            Scene scene = new Scene(root, view.getWidth(), view.getHeight());
            scene.getStylesheets().add(App.class.getResource(STYLE).toExternalForm());
            primaryStage.setTitle(view.getTitle());
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();
            root.requestFocus();
        } catch (IOException exception) {
            AlertUtil.error("Error de navegación", "No se pudo abrir la ventana: " + exception.getMessage());
        }
    }

    public static void showClientDetail(Client client) {
        ensureInitialized();
        try {
            FXMLLoader loader = loader("client-detail-view.fxml");
            Parent root = loader.load();
            ClientDetailController controller = loader.getController();
            controller.setClient(client);

            Stage detailStage = new Stage();
            Scene scene = new Scene(root, 760, 690);
            scene.getStylesheets().add(App.class.getResource(STYLE).toExternalForm());
            detailStage.setTitle("Detalle del cliente");
            detailStage.initOwner(primaryStage);
            detailStage.initModality(Modality.WINDOW_MODAL);
            detailStage.setScene(scene);
            detailStage.setMinWidth(680);
            detailStage.setMinHeight(620);
            detailStage.showAndWait();
        } catch (IOException exception) {
            AlertUtil.error("Error", "No se pudo abrir el detalle: " + exception.getMessage());
        }
    }

    private static FXMLLoader loader(String fxml) {
        return new FXMLLoader(App.class.getResource(VIEW_BASE + fxml));
    }

    private static void ensureInitialized() {
        if (primaryStage == null) {
            throw new IllegalStateException("Navigator no ha sido inicializado");
        }
    }
}
