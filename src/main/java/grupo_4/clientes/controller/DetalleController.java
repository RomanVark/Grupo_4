package grupo_4.clientes.controller;

import grupo_4.clientes.model.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class DetalleController {
    @FXML private Label lblNombre;
    @FXML private Label lblTipo;
    @FXML private Label lblCiudad;
    @FXML private Label lblNacimiento;
    @FXML private Label lblSolicitud;
    @FXML private Label lblFoto;
    @FXML private ListView<String> listaServicios;
    @FXML private ImageView imgFoto;

    public void setCliente(Cliente cliente) {
        lblNombre.setText(cliente.getNombreCompleto());
        lblTipo.setText(cliente.getTipoCliente());
        lblCiudad.setText(cliente.getCiudad());
        lblNacimiento.setText(cliente.getFechaNacimiento().toString());
        lblSolicitud.setText(cliente.getTipoSolicitud());
        listaServicios.getItems().setAll(cliente.getServicios());

        if (cliente.getFotoUri().isBlank()) {
            lblFoto.setText("Sin fotografía");
            return;
        }
        Image imagen = new Image(cliente.getFotoUri(), 320, 320, true, true);
        if (imagen.isError()) {
            lblFoto.setText("Fotografía no disponible");
            return;
        }
        imgFoto.setImage(imagen);
        lblFoto.setText("Fotografía del cliente");
    }

    @FXML
    private void volver(ActionEvent event) {
        ((Stage) lblNombre.getScene().getWindow()).close();
    }
}