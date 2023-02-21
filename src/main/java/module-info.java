module com.example.wgufinalproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.wgufinalproject to javafx.fxml;
    exports com.example.wgufinalproject;
}