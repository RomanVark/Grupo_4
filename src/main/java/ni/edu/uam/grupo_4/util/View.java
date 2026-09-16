package ni.edu.uam.grupo_4.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum View {
    LOGIN("login-view.fxml", "Acceso al sistema", 920, 600),
    MAIN("main-view.fxml", "Gestión de solicitudes", 1100, 720),
    CLIENT_FORM("client-form-view.fxml", "Registro de cliente", 1100, 760),
    CLIENT_LIST("client-list-view.fxml", "Consulta de clientes", 1150, 720);

    private final String fxml;
    private final String title;
    private final double width;
    private final double height;
}
