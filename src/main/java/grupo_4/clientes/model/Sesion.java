package grupo_4.clientes.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Sesion {
    private final String usuario;
    // Esta misma lista se comparte entre principal, registro y consulta.
    private final ObservableList<Cliente> clientes = FXCollections.observableArrayList();
}
