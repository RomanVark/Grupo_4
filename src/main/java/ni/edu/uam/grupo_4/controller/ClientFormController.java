package ni.edu.uam.grupo_4.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import ni.edu.uam.grupo_4.config.AppContext;
import ni.edu.uam.grupo_4.dao.ClientDao;
import ni.edu.uam.grupo_4.model.Client;
import ni.edu.uam.grupo_4.util.AlertUtil;
import ni.edu.uam.grupo_4.util.Navigator;
import ni.edu.uam.grupo_4.util.View;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientFormController {
    private final ClientDao clientDao = AppContext.getClientDao();
    private String photoPath;

    @FXML private BorderPane root;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private ComboBox<String> clientTypeCombo;
    @FXML private ComboBox<String> cityCombo;
    @FXML private DatePicker birthDatePicker;
    @FXML private ToggleGroup requestTypeGroup;
    @FXML private CheckBox installationCheck;
    @FXML private CheckBox maintenanceCheck;
    @FXML private CheckBox consultingCheck;
    @FXML private CheckBox supportCheck;
    @FXML private ImageView photoView;

    @FXML
    private void initialize() {
        clientTypeCombo.setItems(FXCollections.observableArrayList("Natural", "Empresa", "Institución"));
        cityCombo.setItems(FXCollections.observableArrayList(
                "Managua", "Masaya", "Granada", "León", "Chinandega", "Estelí", "Matagalpa", "Jinotega", "Rivas"));

        TextFormatter<String> lettersOnly = new TextFormatter<>(change ->
                change.getControlNewText().matches("[\\p{L} .'-]*") ? change : null);
        TextFormatter<String> lettersOnlyLastName = new TextFormatter<>(change ->
                change.getControlNewText().matches("[\\p{L} .'-]*") ? change : null);
        firstNameField.setTextFormatter(lettersOnly);
        lastNameField.setTextFormatter(lettersOnlyLastName);
    }

    @FXML
    private void choosePhoto() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Seleccionar fotografía");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File file = chooser.showOpenDialog(root.getScene().getWindow());
        if (file != null) {
            photoPath = file.getAbsolutePath();
            photoView.setImage(new Image(file.toURI().toString(), true));
        }
    }

    @FXML
    private void save() {
        if (!isValid()) {
            return;
        }

        Client client = Client.builder()
                .nombres(firstNameField.getText().trim())
                .apellidos(lastNameField.getText().trim())
                .tipoCliente(clientTypeCombo.getValue())
                .ciudad(cityCombo.getValue())
                .fechaNacimiento(birthDatePicker.getValue())
                .tipoSolicitud(selectedRequestType())
                .serviciosInteres(selectedServices())
                .fotografiaRuta(photoPath)
                .build();

        clientDao.save(client);
        AlertUtil.info("Registro guardado", "El cliente " + client.getNombreCompleto() + " fue registrado correctamente.");
        Navigator.goTo(View.CLIENT_LIST);
    }

    @FXML
    private void clear() {
        firstNameField.clear();
        lastNameField.clear();
        clientTypeCombo.getSelectionModel().clearSelection();
        cityCombo.getSelectionModel().clearSelection();
        birthDatePicker.setValue(null);
        requestTypeGroup.selectToggle(null);
        installationCheck.setSelected(false);
        maintenanceCheck.setSelected(false);
        consultingCheck.setSelected(false);
        supportCheck.setSelected(false);
        photoView.setImage(null);
        photoPath = null;
        firstNameField.requestFocus();
    }

    @FXML
    private void cancel() {
        Navigator.goTo(View.MAIN);
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            cancel();
            event.consume();
        } else if (event.getCode() == KeyCode.ENTER && event.isControlDown()) {
            save();
            event.consume();
        }
    }

    private boolean isValid() {
        if (firstNameField.getText().isBlank() || lastNameField.getText().isBlank()
                || clientTypeCombo.getValue() == null || cityCombo.getValue() == null
                || birthDatePicker.getValue() == null || requestTypeGroup.getSelectedToggle() == null) {
            AlertUtil.warning("Información incompleta", "Complete todos los campos obligatorios marcados con *.");
            return false;
        }
        if (!birthDatePicker.getValue().isBefore(LocalDate.now())) {
            AlertUtil.warning("Fecha inválida", "La fecha de nacimiento debe ser anterior a hoy.");
            return false;
        }
        if (selectedServices().isEmpty()) {
            AlertUtil.warning("Servicios de interés", "Seleccione al menos un servicio de interés.");
            return false;
        }
        return true;
    }

    private String selectedRequestType() {
        Toggle toggle = requestTypeGroup.getSelectedToggle();
        return toggle == null ? null : String.valueOf(toggle.getUserData());
    }

    private List<String> selectedServices() {
        List<String> services = new ArrayList<>();
        addIfSelected(installationCheck, "Instalación", services);
        addIfSelected(maintenanceCheck, "Mantenimiento", services);
        addIfSelected(consultingCheck, "Consultoría", services);
        addIfSelected(supportCheck, "Soporte", services);
        return services;
    }

    private void addIfSelected(CheckBox checkBox, String value, List<String> services) {
        if (checkBox.isSelected()) {
            services.add(value);
        }
    }
}
