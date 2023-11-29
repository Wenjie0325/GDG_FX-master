module com.example.gdg_fx {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.gdg_fx to javafx.fxml;
    exports com.example.gdg_fx;
}