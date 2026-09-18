package grupo_4.clientes.controller;

import grupo_4.clientes.Ventanas;
import grupo_4.clientes.model.Sesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtClave;

    @FXML private void iniciarSesion(ActionEvent event) {
        acceder();
    }

    @FXML private void manejarTecla(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            event.consume();
            acceder();
        }
    }

    private void acceder() {
        String usuario = txtUsuario.getText().trim();
        String clave = txtClave.getText();
        if (usuario.isBlank() || clave.isBlank()) {
            Ventanas.alerta(stage(), Alert.AlertType.WARNING, "Ingrese su usuario y contraseña");
            return;
        }
        // Contraseña y Usuario correctos
        if (!usuario.equals("admin") || !clave.equals("1234")) {
            Ventanas.alerta(stage(), Alert.AlertType.ERROR, "Usuario o Crontraseña incorrectos");
            return;
        }
        try {
            Sesion sesion = new Sesion(usuario);
            Stage principal = Ventanas.<PrincipalController>cargar(
                    "principal.fxml", "Gestión de clientes", null,
                    controller -> controller.setSesion(sesion));
            principal.setMinWidth(720);
            principal.setMinHeight(520);
            Ventanas.confirmarCierre(principal);
            principal.show();
            stage().close();
        } catch (IOException e) {
            Ventanas.alerta(stage(), Alert.AlertType.ERROR,
                    "No se pudo abrir la ventana principal: " + e.getMessage());
        }
    }

    @FXML private void salir(ActionEvent event) {
        Ventanas.salir(stage());
    }

    private Stage stage() {
        return (Stage) txtUsuario.getScene().getWindow();
    }
}
