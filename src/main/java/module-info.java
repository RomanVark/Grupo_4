module ni.edu.uam.grupo_4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ni.edu.uam.grupo_4 to javafx.fxml;
    exports ni.edu.uam.grupo_4;
}