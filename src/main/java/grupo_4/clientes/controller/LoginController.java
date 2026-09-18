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

import java.awt.*;

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
        String clave = txtClave.getText().trim();
        if (usuario.isBlank() || clave.isBlank()) {
            Ventanas.alerta(stage(), Alert.AlertType.WARNING, "Ingrese su usuario y contraseña");
            return;
        }


    }
}
