module com.lab1.lab1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lab1.lab1 to javafx.fxml;
    exports com.lab1.lab1;
}