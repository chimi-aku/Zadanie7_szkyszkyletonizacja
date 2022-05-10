module com.example.zadanie7_szkyszkyletonizacja {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.zadanie7_szkyszkyletonizacja to javafx.fxml;
    exports com.example.zadanie7_szkyszkyletonizacja;
}