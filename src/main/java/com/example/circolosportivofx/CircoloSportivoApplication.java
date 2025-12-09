package com.example.circolosportivofx;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class CircoloSportivoApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Data data = Data.getInstance();
        Data.getInstance().startOutput();
        SceneController sceneController = new SceneController(stage);

        sceneController.changeScene("login");

        stage.setOnCloseRequest(e -> {
            data.saveUsers();
            data.saveActivities();
            Data.getInstance().endOutput();
            System.out.println("Application closed.");
        });


    }
}
