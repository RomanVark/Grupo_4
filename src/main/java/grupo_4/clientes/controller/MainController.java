package grupo_4.clientes.controller;

import com.empresa.solicitudes.util.AppData;
import com.empresa.solicitudes.util.Navigator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;

public class MainController {
    @FXML private Label lblUsuario;
    @FXML private Label lblResumen;
    @FXML private ListView<String> lstAcciones;

    // Al ingresar un registro de usuario al sistema, el sistema agrega su nombre y
    // presenta las posibles acciones a realizar.
    @FXML
    private void initialize() {
        lblUsuario.setText("Usuario: " + AppData.getUsuarioActual());
        actualizarResumen();
        lstAcciones.getItems().setAll(
                "Registrar un nuevo cliente",
                "Consultar clientes registrados",
                "Abrir carpeta de evidencias"
        );
    }

    public void actualizarResumen() {
        lblResumen.setText("Clientes registrados en memoria: " + AppData.getClientes().size());
    }

    @FXML
    private void abrirRegistro(ActionEvent event) { abrir("register.fxml", "Registro de cliente", 760, 720); }

    @FXML
    private void abrirConsulta(ActionEvent event) { abrir("clients.fxml", "Consulta de clientes", 980, 620); }

    @FXML
    private void abrirDesdeLista() {
        String seleccion = lstAcciones.getSelectionModel().getSelectedItem();
        if (seleccion == null) return;
        if (seleccion.startsWith("Registrar")) abrirRegistro(null);
        else if (seleccion.startsWith("Consultar")) abrirConsulta(null);
    }

    @FXML
    private void abrirAyuda(ActionEvent event) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Ayuda");
        dialog.setHeaderText("Sistema de Solicitudes");
        dialog.setContentText("Use el menú o la barra de herramientas para registrar y consultar clientes.\n" +
                "También puede usar doble clic en la tabla para ver el detalle.");
        ButtonType cerrar = new ButtonType("Cerrar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(cerrar);
        dialog.showAndWait();
    }

    // Confirmacion antes de cerrar el sistema.
    @FXML
    private void cerrarAplicacion(ActionEvent event) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea cerrar el sistema?", ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirmación");
        confirm.showAndWait().ifPresent(r -> { if (r == ButtonType.YES) Platform.exit(); });
    }

    @FXML
    private void contextMenu(ContextMenuEvent event) { }

    // Verificacion de errores y su existencia
    private void abrir(String fxml, String title, double width, double height) {
        try { Navigator.open(fxml, title, width, height, true); }
        catch (Exception ex) { new Alert(Alert.AlertType.ERROR, "Error al abrir la ventana: " + ex.getMessage()).showAndWait(); }
        actualizarResumen();
    }
}
