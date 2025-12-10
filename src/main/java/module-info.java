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
    requires javafx.base;

    opens com.example.circolosportivofx to javafx.fxml;
    exports com.example.circolosportivofx;
    exports com.example.circolosportivofx.controllers;
    opens com.example.circolosportivofx.controllers to javafx.fxml;
}