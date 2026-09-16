package ni.edu.uam.grupo_4.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import ni.edu.uam.grupo_4.config.AppContext;
import ni.edu.uam.grupo_4.dao.ClientDao;
import ni.edu.uam.grupo_4.model.Client;
import ni.edu.uam.grupo_4.util.AlertUtil;
import ni.edu.uam.grupo_4.util.Navigator;
import ni.edu.uam.grupo_4.util.View;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ClientListController {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final ClientDao clientDao = AppContext.getClientDao();
    private final ObservableList<Client> tableData = FXCollections.observableArrayList();

    @FXML private BorderPane root;
    @FXML private TextField searchField;
    @FXML private TableView<Client> clientTable;
    @FXML private TableColumn<Client, String> nameColumn;
    @FXML private TableColumn<Client, String> typeColumn;
    @FXML private TableColumn<Client, String> cityColumn;
    @FXML private TableColumn<Client, String> birthDateColumn;
    @FXML private TableColumn<Client, String> requestColumn;

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombreCompleto()));
        typeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTipoCliente()));
        cityColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCiudad()));
        birthDateColumn.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getFechaNacimiento().format(DATE_FORMAT)));
        requestColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTipoSolicitud()));
        clientTable.setItems(tableData);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> applyFilter(newValue));
        reload();
    }

    @FXML
    private void handleTableClick(MouseEvent event) {
        if (event.getClickCount() == 2 && event.isPrimaryButtonDown()) {
            openSelected();
        }
    }

    @FXML
    private void openSelected() {
        Client selected = clientTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.warning("Seleccione un cliente", "Seleccione un registro para ver su detalle.");
            return;
        }
        Navigator.showClientDetail(selected);
        reload();
    }

    @FXML
    private void deleteSelected() {
        Client selected = clientTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.warning("Seleccione un cliente", "Seleccione el registro que desea eliminar.");
            return;
        }
        if (AlertUtil.confirm("Eliminar cliente", "¿Desea eliminar a " + selected.getNombreCompleto() + "?")) {
            clientDao.deleteById(selected.getId());
            reload();
            AlertUtil.info("Cliente eliminado", "El registro fue eliminado correctamente.");
        }
    }

    @FXML
    private void openRegistration() {
        Navigator.goTo(View.CLIENT_FORM);
    }

    @FXML
    private void reload() {
        searchField.clear();
        tableData.setAll(clientDao.findAll());
    }

    @FXML
    private void backToMain() {
        Navigator.goTo(View.MAIN);
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            openSelected();
            event.consume();
        } else if (event.getCode() == KeyCode.DELETE) {
            deleteSelected();
            event.consume();
        } else if (event.getCode() == KeyCode.ESCAPE) {
            backToMain();
            event.consume();
        }
    }

    private void applyFilter(String text) {
        String query = text == null ? "" : text.trim().toLowerCase(Locale.ROOT);
        if (query.isEmpty()) {
            tableData.setAll(clientDao.findAll());
            return;
        }
        tableData.setAll(clientDao.findAll().stream()
                .filter(client -> client.getNombreCompleto().toLowerCase(Locale.ROOT).contains(query)
                        || client.getCiudad().toLowerCase(Locale.ROOT).contains(query)
                        || client.getTipoCliente().toLowerCase(Locale.ROOT).contains(query))
                .toList());
    }
}
