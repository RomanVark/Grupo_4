package grupo_4.clientes.controller;

import grupo_4.clientes.Ventanas;
import grupo_4.clientes.model.Cliente;
import grupo_4.clientes.model.Sesion;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class PrincipalController {
    @FXML private Label lblUsuario;
    @FXML private Label lblTotal;
    @FXML private ListView<Cliente> listaClientes;
    private Sesion sesion;

    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
        lblUsuario.setText("Sesión de " + sesion.getUsuario());
        listaClientes.setItems(sesion.getClientes());
        lblTotal.textProperty().bind(Bindings.size(sesion.getClientes())
                .asString("Clientes registrados: %d"));
        listaClientes.setPlaceholder(new Label("Registra tu primer cliente."));
        listaClientes.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Cliente cliente, boolean empty) {
                super.updateItem(cliente, empty);
                setText(empty || cliente == null ? null : cliente.getNombreCompleto()
                        + " · " + cliente.getTipoSolicitud());
            }
        });
    }

    @FXML
    private void abrirRegistro(ActionEvent event) {
        try {
            Stage registro = Ventanas.<RegistroController>cargar(
                    "registro.fxml", "Registrar cliente", stage(),
                    controller -> controller.setSesion(sesion));
            registro.setMinWidth(800);
            registro.setMinHeight(620);
            registro.showAndWait();
        } catch (IOException e) {
            error(e);
        }
    }

    @FXML
    private void abrirConsulta(ActionEvent event) {
        try {
            Stage consulta = Ventanas.<ConsultaController>cargar(
                    "consulta.fxml", "Consultar clientes", stage(),
                    controller -> controller.setSesion(sesion));
            consulta.setMinWidth(780);
            consulta.setMinHeight(460);
            consulta.showAndWait();
        } catch (IOException e) {
            error(e);
        }
    }

    @FXML
    private void verSeleccionado(ActionEvent event) {
        Cliente cliente = listaClientes.getSelectionModel().getSelectedItem();
        if (cliente == null) {
            Ventanas.alerta(stage(), Alert.AlertType.WARNING, "Selecciona un cliente en la lista.");
            return;
        }
        try {
            Ventanas.<DetalleController>cargar("detalle.fxml", "Detalle del cliente", stage(),
                    controller -> controller.setCliente(cliente)).showAndWait();
        } catch (IOException e) {
            error(e);
        }
    }

    @FXML
    private void exportarInforme(ActionEvent event) {
        if (sesion.getClientes().isEmpty()) {
            Ventanas.alerta(stage(), Alert.AlertType.WARNING, "Primero registra un cliente.");
            return;
        }
        DirectoryChooser selector = new DirectoryChooser();
        selector.setTitle("Selecciona la carpeta del informe");
        File carpeta = selector.showDialog(stage());
        if (carpeta == null) return;

        // TextInputDialog es una subclase de Dialog<String> y recoge un dato real.
        TextInputDialog dialog = new TextInputDialog("Informe de clientes");
        dialog.initOwner(stage());
        dialog.setTitle("Exportar informe");
        dialog.setHeaderText("Escribe el título que aparecerá dentro del informe.");
        dialog.setContentText("Título:");
        dialog.showAndWait().ifPresent(titulo -> {
            if (titulo.isBlank()) {
                Ventanas.alerta(stage(), Alert.AlertType.WARNING, "El título no puede estar vacío.");
                return;
            }
            try {
                Path archivo = escribirInforme(carpeta.toPath(), titulo.trim());
                Ventanas.alerta(stage(), Alert.AlertType.INFORMATION,
                        "Informe creado en:\n" + archivo.toAbsolutePath());
            } catch (IOException e) {
                error(e);
            }
        });
    }

    public Path escribirInforme(Path carpeta, String titulo) throws IOException {
        StringBuilder texto = new StringBuilder(titulo).append("\n")
                .append("Usuario: ").append(sesion.getUsuario()).append("\n")
                .append("Total: ").append(sesion.getClientes().size()).append("\n\n");
        for (Cliente cliente : sesion.getClientes()) {
            texto.append("Cliente: ").append(cliente.getNombreCompleto()).append("\n")
                    .append("Tipo: ").append(cliente.getTipoCliente()).append("\n")
                    .append("Ciudad: ").append(cliente.getCiudad()).append("\n")
                    .append("Nacimiento: ").append(cliente.getFechaNacimiento()).append("\n")
                    .append("Solicitud: ").append(cliente.getTipoSolicitud()).append("\n")
                    .append("Servicios: ").append(cliente.getServiciosTexto())
                    .append("\n\n");
        }
        // Se genera un nombre único para no sobrescribir otro informe.
        Path archivo = Files.createTempFile(carpeta, "clientes-", ".txt");
        Files.writeString(archivo, texto, StandardCharsets.UTF_8);
        return archivo;
    }

    @FXML
    private void acercaDe(ActionEvent event) {
        Ventanas.alerta(stage(), Alert.AlertType.INFORMATION,
                "Registro y consulta de clientes.\nJavaFX, FXML, Maven y Lombok.\n"
                        + "Los registros permanecen en memoria durante esta ejecución.");
    }

    @FXML
    private void salir(ActionEvent event) {
        Ventanas.salir(stage());
    }

    private Stage stage() {
        return (Stage) lblUsuario.getScene().getWindow();
    }

    private void error(IOException e) {
        Ventanas.alerta(stage(), Alert.AlertType.ERROR,
                "No se pudo completar la operación: " + e.getMessage());
    }
}
