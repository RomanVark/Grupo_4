module ni.edu.uam.grupo_4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens ni.edu.uam.grupo_4 to javafx.fxml;
    opens ni.edu.uam.grupo_4.controller to javafx.fxml;
    exports ni.edu.uam.grupo_4;
    exports ni.edu.uam.grupo_4.model;
}
