package com.example.circolosportivofx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CircoloSportivoApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Data data = Data.getInstance();
        Data.getInstance().startOutput();
        SceneController sceneController = SceneController.getInstance();

        /*
        FXMLLoader fxmlLoader = new FXMLLoader(CircoloSportivoApplication.class.getResource("views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("styles/login-view.css")).toExternalForm()
        );
        */
        stage.setScene(sceneController.getCurrentScene());
        stage.centerOnScreen();
        stage.setTitle("");
        stage.resizableProperty().setValue(false);
        stage.show();
        stage.setOnCloseRequest(e -> {
            data.saveUsers();
            data.saveActivities();
            Data.getInstance().endOutput();
            System.out.println("Application closed.");
        });


    }
}
