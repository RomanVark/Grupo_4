package ni.edu.uam.grupo_4.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ni.edu.uam.grupo_4.util.AlertUtil;
import ni.edu.uam.grupo_4.util.Navigator;
import ni.edu.uam.grupo_4.util.View;

public class LoginController {
    @FXML private BorderPane root;
    @FXML private TextField userField;
    @FXML private PasswordField passwordField;

    @FXML
    private void initialize() {
        Platform.runLater(userField::requestFocus);
    }

    @FXML
    private void login() {
        String user = userField.getText().trim();
        String password = passwordField.getText();

        if (user.isBlank() || password.isBlank()) {
            AlertUtil.warning("Información incompleta", "Ingrese el usuario y la contraseña.");
            return;
        }
        Navigator.goTo(View.MAIN);
    }

    @FXML
    private void exit() {
        if (AlertUtil.confirm("Salir", "¿Desea cerrar la aplicación?")) {
            Platform.exit();
        }
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            login();
            event.consume();
        } else if (event.getCode() == KeyCode.ESCAPE) {
            exit();
            event.consume();
        }
    }
}
