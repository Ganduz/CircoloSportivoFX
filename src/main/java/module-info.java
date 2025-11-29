module com.example.circolosportivofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.google.gson;
    requires annotations;
    requires javafx.graphics;
    requires org.json;

    opens com.example.circolosportivofx to javafx.fxml;
    exports com.example.circolosportivofx;
}