package ni.edu.uam.grupo_4.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ni.edu.uam.grupo_4.config.AppContext;
import ni.edu.uam.grupo_4.dao.ClientDao;
import ni.edu.uam.grupo_4.model.Client;
import ni.edu.uam.grupo_4.util.AlertUtil;

import java.io.File;
import java.time.LocalDate;

public class ClientDetailController {
    private final ClientDao clientDao = AppContext.getClientDao();
    private Client client;
    private String photoPath;

    @FXML private BorderPane root;
    @FXML private Label idLabel;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private ComboBox<String> clientTypeCombo;
    @FXML private ComboBox<String> cityCombo;
    @FXML private DatePicker birthDatePicker;
    @FXML private ComboBox<String> requestTypeCombo;
    @FXML private TextArea servicesArea;
    @FXML private ImageView photoView;

    @FXML
    private void initialize() {
        clientTypeCombo.setItems(FXCollections.observableArrayList("Natural", "Empresa", "Institución"));
        cityCombo.setItems(FXCollections.observableArrayList(
                "Managua", "Masaya", "Granada", "León", "Chinandega", "Estelí", "Matagalpa", "Jinotega", "Rivas"));
        requestTypeCombo.setItems(FXCollections.observableArrayList("Información", "Cotización", "Soporte"));
    }

    public void setClient(Client client) {
        this.client = client;
        this.photoPath = client.getFotografiaRuta();
        idLabel.setText("Cliente #" + client.getId());
        firstNameField.setText(client.getNombres());
        lastNameField.setText(client.getApellidos());
        clientTypeCombo.setValue(client.getTipoCliente());
        cityCombo.setValue(client.getCiudad());
        birthDatePicker.setValue(client.getFechaNacimiento());
        requestTypeCombo.setValue(client.getTipoSolicitud());
        servicesArea.setText(String.join(", ", client.getServiciosInteres()));
        showPhoto(photoPath);
    }

    @FXML
    private void choosePhoto() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Cambiar fotografía");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File file = chooser.showOpenDialog(root.getScene().getWindow());
        if (file != null) {
            photoPath = file.getAbsolutePath();
            showPhoto(photoPath);
        }
    }

    @FXML
    private void saveChanges() {
        if (firstNameField.getText().isBlank() || lastNameField.getText().isBlank()
                || clientTypeCombo.getValue() == null || cityCombo.getValue() == null
                || birthDatePicker.getValue() == null || requestTypeCombo.getValue() == null) {
            AlertUtil.warning("Información incompleta", "Complete todos los campos del detalle.");
            return;
        }
        if (!birthDatePicker.getValue().isBefore(LocalDate.now())) {
            AlertUtil.warning("Fecha inválida", "La fecha de nacimiento debe ser anterior a hoy.");
            return;
        }

        client.setNombres(firstNameField.getText().trim());
        client.setApellidos(lastNameField.getText().trim());
        client.setTipoCliente(clientTypeCombo.getValue());
        client.setCiudad(cityCombo.getValue());
        client.setFechaNacimiento(birthDatePicker.getValue());
        client.setTipoSolicitud(requestTypeCombo.getValue());
        client.setFotografiaRuta(photoPath);

        if (clientDao.update(client)) {
            AlertUtil.info("Cambios guardados", "La información del cliente fue actualizada.");
            close();
        } else {
            AlertUtil.error("No se pudo actualizar", "El cliente ya no existe en el repositorio.");
        }
    }

    @FXML
    private void delete() {
        if (AlertUtil.confirm("Eliminar cliente", "Esta acción eliminará definitivamente el registro. ¿Continuar?")) {
            clientDao.deleteById(client.getId());
            close();
        }
    }

    @FXML
    private void close() {
        ((Stage) root.getScene().getWindow()).close();
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            close();
            event.consume();
        }
    }

    private void showPhoto(String path) {
        if (path == null || path.isBlank()) {
            photoView.setImage(null);
            return;
        }
        File file = new File(path);
        if (file.isFile()) {
            photoView.setImage(new Image(file.toURI().toString(), true));
        }
    }
}
