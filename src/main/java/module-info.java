module grupo_4 {
    requires javafx.controls;
    requires javafx.fxml;

    requires static lombok;

    exports grupo_4;
    exports grupo_4.clientes;
    exports grupo_4.clientes.model;

    opens grupo_4 to javafx.fxml;
    opens grupo_4.clientes to javafx.fxml;
    opens grupo_4.clientes.controller to javafx.fxml;
    opens grupo_4.clientes.model to javafx.base, javafx.fxml;
}