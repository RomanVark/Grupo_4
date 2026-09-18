package grupo_4.clientes.controller;

import grupo_4.clientes.Ventanas;
import grupo_4.clientes.model.Cliente;
import grupo_4.clientes.model.Sesion;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ConsultaController {
    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colTipo;
    @FXML private TableColumn<Cliente, String> colCiudad;
    @FXML private TableColumn<Cliente, String> colNacimiento;
    @FXML private TableColumn<Cliente, String> colSolicitud;

    @FXML
    private void initialize() {
        colNombre.setCellValueFactory(cell -> new ReadOnlyStringWrapper(
                cell.getValue().getNombreCompleto()));
        colTipo.setCellValueFactory(cell -> new ReadOnlyStringWrapper(
                cell.getValue().getTipoCliente()));
        colCiudad.setCellValueFactory(cell -> new ReadOnlyStringWrapper(
                cell.getValue().getCiudad()));
        colNacimiento.setCellValueFactory(cell -> new ReadOnlyStringWrapper(
                cell.getValue().getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        colSolicitud.setCellValueFactory(cell -> new ReadOnlyStringWrapper(
                cell.getValue().getTipoSolicitud()));
        tablaClientes.setPlaceholder(new Label("Todavía no hay clientes registrados."));
        tablaClientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        tablaClientes.setRowFactory(tabla -> {
            TableRow<Cliente> fila = new TableRow<>();
            fila.setOnMouseClicked((MouseEvent event) -> {
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
            Ventanas.alerta(stage(), Alert.AlertType.ERROR,
                    "No se pudo abrir el detalle: " + e.getMessage());
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
