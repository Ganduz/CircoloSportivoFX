package com.example.circolosportivofx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CircoloSportivoApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CircoloSportivoApplication.class.getResource("add-member-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 473, 365);
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("styles/addMember.css")).toExternalForm()
        );
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }
}
