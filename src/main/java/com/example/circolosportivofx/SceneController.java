package com.example.circolosportivofx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

public class SceneController {

    //aggiungere hasmmap css
    private static SceneController instance;
    private HashMap<String, URL> screenMap = new HashMap<>();
    private final Stage stage;

    public static SceneController getInstance() throws IOException {
        if (instance == null) {
            instance = new SceneController();
        }
        return instance;
    }

    public SceneController(Stage stage) throws IOException {
        this.stage = stage;
        screenMap.put("login", CircoloSportivoApplication.class.getResource("views/login-view.fxml"));
        screenMap.put("member", CircoloSportivoApplication.class.getResource("views/member-view.fxml"));
        screenMap.put("admin", CircoloSportivoApplication.class.getResource("views/admin-view.fxml"));
        screenMap.put("manageMembers", CircoloSportivoApplication.class.getResource("views/manage-member-view.fxml"));
        screenMap.put("manageActivities",CircoloSportivoApplication.class.getResource("views/manage-activities-view.fxml"));
    }

    public void changeScene(String sceneName) throws IOException {
        Pane pane = FXMLLoader.load(screenMap.get(sceneName));
        stage.getScene().setRoot(pane);
    }

    public Scene getCurrentScene(){
        return stage.getScene();
    }

}
