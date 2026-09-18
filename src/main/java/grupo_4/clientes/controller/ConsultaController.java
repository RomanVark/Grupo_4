package grupo_4.clientes.controller;

import grupo_4.clientes.Ventanas;
import grupo_4.clientes.model.Cliente;
import grupo_4.clientes.model.Sesion;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.IOException;

public class ConsultaController {
    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colTipo;
    @FXML private TableColumn<Cliente, String> colCiudad;
    @FXML private TableColumn<Cliente, String> colNacimiento;
    @FXML private TableColumn<Cliente, String> colSolicitud;

    @FXML
    private void initialize() {
        colNombre.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getNombreCompleto()));
        colTipo.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getTipoCliente()));
        colCiudad.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getCiudad()));
        colNacimiento.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getFechaNacimiento().toString()));
        colSolicitud.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getTipoSolicitud()));
        tablaClientes.setPlaceholder(new Label("Todavía no hay clientes registrados."));

        // Doble clic sobre una fila con datos: el MouseEvent de la rubrica.
        tablaClientes.setRowFactory(tabla -> {
            TableRow<Cliente> fila = new TableRow<>();
            fila.setOnMouseClicked(event -> {
                if (!fila.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    mostrarDetalle(fila.getItem());
                }
            });
            return fila;
        });
    }

    public void setSesion(Sesion sesion) {
        tablaClientes.setItems(sesion.getClientes());
    }

    @FXML
    private void verDetalle(ActionEvent event) {
        Cliente cliente = tablaClientes.getSelectionModel().getSelectedItem();
        if (cliente == null) {
            Ventanas.alerta(stage(), Alert.AlertType.WARNING, "Selecciona un cliente de la tabla.");
            return;
        }
        mostrarDetalle(cliente);
    }

    private void mostrarDetalle(Cliente cliente) {
        try {
            Ventanas.<DetalleController>cargar("detalle.fxml", "Detalle del cliente", stage(),
                    controller -> controller.setCliente(cliente)).showAndWait();
        } catch (IOException e) {
            Ventanas.alerta(stage(), Alert.AlertType.ERROR, "No se pudo abrir el detalle.");
        }
    }

    @FXML
    private void volver(ActionEvent event) {
        stage().close();
    }

    private Stage stage() {
        return (Stage) tablaClientes.getScene().getWindow();
    }
}