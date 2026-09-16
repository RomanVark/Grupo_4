package ni.edu.uam.grupo_4.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import ni.edu.uam.grupo_4.config.AppContext;
import ni.edu.uam.grupo_4.dao.ClientDao;
import ni.edu.uam.grupo_4.model.Client;
import ni.edu.uam.grupo_4.util.AlertUtil;
import ni.edu.uam.grupo_4.util.Navigator;
import ni.edu.uam.grupo_4.util.View;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class MainController {
    private final ClientDao clientDao = AppContext.getClientDao();

    @FXML private BorderPane root;
    @FXML private Label clientCountLabel;

    @FXML
    private void initialize() {
        refreshCount();
        Platform.runLater(root::requestFocus);
    }

    @FXML
    private void openRegistration() {
        Navigator.goTo(View.CLIENT_FORM);
    }

    @FXML
    private void openClientList() {
        Navigator.goTo(View.CLIENT_LIST);
    }

    @FXML
    private void exportClients() {
        if (clientDao.findAll().isEmpty()) {
            AlertUtil.warning("Sin información", "Registre al menos un cliente antes de exportar.");
            return;
        }

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Seleccione la carpeta de exportación");
        File directory = chooser.showDialog(root.getScene().getWindow());
        if (directory == null) {
            return;
        }

        Path output = directory.toPath().resolve("clientes.csv");
        StringBuilder csv = new StringBuilder("ID,NOMBRE COMPLETO,TIPO,CIUDAD,NACIMIENTO,SOLICITUD,SERVICIOS\n");
        for (Client client : clientDao.findAll()) {
            csv.append(client.getId()).append(',')
                    .append(csvValue(client.getNombreCompleto())).append(',')
                    .append(csvValue(client.getTipoCliente())).append(',')
                    .append(csvValue(client.getCiudad())).append(',')
                    .append(client.getFechaNacimiento()).append(',')
                    .append(csvValue(client.getTipoSolicitud())).append(',')
                    .append(csvValue(String.join("; ", client.getServiciosInteres()))).append('\n');
        }

        try {
            Files.writeString(output, csv.toString(), StandardCharsets.UTF_8);
            AlertUtil.info("Exportación completada", "Archivo creado en:\n" + output);
        } catch (IOException exception) {
            AlertUtil.error("Error al exportar", exception.getMessage());
        }
    }

    @FXML
    private void showSummaryDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Resumen del sistema");
        dialog.setHeaderText("Estado actual de la aplicación");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        VBox content = new VBox(10,
                new Label("Clientes registrados: " + clientDao.findAll().size()),
                new Label("Persistencia: DAO en memoria durante la ejecución"),
                new Label("Exportación disponible: archivo CSV"));
        content.getStyleClass().add("dialog-content");
        dialog.getDialogPane().setContent(content);
        dialog.showAndWait();
    }

    @FXML
    private void logout() {
        if (AlertUtil.confirm("Cerrar sesión", "¿Desea volver al inicio de sesión?")) {
            Navigator.goTo(View.LOGIN);
        }
    }

    @FXML
    private void exit() {
        if (AlertUtil.confirm("Salir", "¿Desea cerrar la aplicación?")) {
            Platform.exit();
        }
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            logout();
            event.consume();
        }
    }

    private void refreshCount() {
        clientCountLabel.setText(String.valueOf(clientDao.findAll().size()));
    }

    private String csvValue(String value) {
        return '"' + (value == null ? "" : value.replace("\"", "\"\"")) + '"';
    }
}
