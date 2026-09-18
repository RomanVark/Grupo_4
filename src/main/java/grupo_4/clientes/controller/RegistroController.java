package grupo_4.clientes.controller;

import grupo_4.clientes.Ventanas;
import grupo_4.clientes.model.Cliente;
import grupo_4.clientes.model.Sesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegistroController {
    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidos;
    @FXML private ComboBox<String> cmbTipo;
    @FXML private ComboBox<String> cmbCiudad;
    @FXML private DatePicker dpNacimiento;
    @FXML private ToggleGroup grupoSolicitud;
    @FXML private CheckBox chkInternet;
    @FXML private CheckBox chkTelefonia;
    @FXML private CheckBox chkSoporte;
    @FXML private ImageView imgFoto;
    @FXML private Label lblFoto;
    private Sesion sesion;
    private String fotoUri = "";

    @FXML
    private void initialize() {
        cmbTipo.getItems().setAll("Regular", "Preferencial", "Corporativo");
        cmbCiudad.getItems().setAll("Managua", "León", "Granada", "Masaya", "Estelí");
        dpNacimiento.setEditable(false);
    }

    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }

    @FXML
    private void seleccionarFoto(ActionEvent event) {
        FileChooser selector = new FileChooser();
        selector.setTitle("Selecciona la fotografía");
        File archivo = selector.showOpenDialog(stage());
        if (archivo == null) return;
        Image imagen = new Image(archivo.toURI().toString(), 320, 320, true, true);
        if (imagen.isError()) {
            Ventanas.alerta(stage(), Alert.AlertType.WARNING, "No se pudo leer la imagen.");
            return;
        }
        fotoUri = archivo.toURI().toString();
        imgFoto.setImage(imagen);
        lblFoto.setText(archivo.getName());
    }

    @FXML
    private void guardar(ActionEvent event) {
        if (txtNombres.getText().isBlank() || txtApellidos.getText().isBlank()) {
            advertir("Escribe los nombres y apellidos.");
            return;
        }
        if (cmbTipo.getValue() == null || cmbCiudad.getValue() == null) {
            advertir("Selecciona el tipo de cliente y la ciudad.");
            return;
        }
        LocalDate fecha = dpNacimiento.getValue();
        if (fecha == null || fecha.isAfter(LocalDate.now())) {
            advertir("Selecciona una fecha que no sea futura.");
            return;
        }
        if (grupoSolicitud.getSelectedToggle() == null) {
            advertir("Selecciona el tipo de solicitud.");
            return;
        }
        if (!chkInternet.isSelected() && !chkTelefonia.isSelected() && !chkSoporte.isSelected()) {
            advertir("Selecciona al menos un servicio.");
            return;
        }
        RadioButton opcion = (RadioButton) grupoSolicitud.getSelectedToggle();
        Cliente cliente = new Cliente();
        cliente.setNombres(txtNombres.getText().trim());
        cliente.setApellidos(txtApellidos.getText().trim());
        cliente.setTipoCliente(cmbTipo.getValue());
        cliente.setCiudad(cmbCiudad.getValue());
        cliente.setFechaNacimiento(fecha);
        cliente.setTipoSolicitud(opcion.getText());
        cliente.setServicios(serviciosSeleccionados());
        cliente.setFotoUri(fotoUri);
        sesion.getClientes().add(cliente);
        Ventanas.alerta(stage(), Alert.AlertType.INFORMATION, "Cliente registrado.");
        stage().close();
    }

    private List<String> serviciosSeleccionados() {
        List<String> servicios = new ArrayList<>();
        if (chkInternet.isSelected()) servicios.add("Internet");
        if (chkTelefonia.isSelected()) servicios.add("Telefonía");
        if (chkSoporte.isSelected()) servicios.add("Soporte técnico");
        return servicios;
    }

    @FXML
    private void limpiar(ActionEvent event) {
        txtNombres.clear();
        txtApellidos.clear();
        cmbTipo.setValue(null);
        cmbCiudad.setValue(null);
        dpNacimiento.setValue(null);
        grupoSolicitud.selectToggle(null);
        chkInternet.setSelected(false);
        chkTelefonia.setSelected(false);
        chkSoporte.setSelected(false);
        imgFoto.setImage(null);
        fotoUri = "";
        lblFoto.setText("Fotografía opcional");
    }

    @FXML
    private void cancelar(ActionEvent event) {
        stage().close();
    }

    private Stage stage() {
        return (Stage) txtNombres.getScene().getWindow();
    }

    private void advertir(String mensaje) {
        Ventanas.alerta(stage(), Alert.AlertType.WARNING, mensaje);
    }
}