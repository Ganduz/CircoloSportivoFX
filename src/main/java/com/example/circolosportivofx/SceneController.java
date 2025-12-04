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
    private HashMap<String , URL> cssMap = new HashMap<>();
    private Stage stage;

    public static SceneController getInstance(Stage stage)  {
        if (instance == null) {
            instance = new SceneController(stage);
        }
        return instance;
    }

    public SceneController(Stage stage) {
        setStage(stage);
        initMaps();
    }

    public void changeScene(String sceneName) throws IOException {
        Pane pane = FXMLLoader.load(screenMap.get(sceneName));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        if(sceneName.contains("manage"))
            stage.getScene().getStylesheets().add(cssMap.get("manage").toExternalForm());
        else
            stage.getScene().getStylesheets().add(cssMap.get(sceneName).toExternalForm());
    }

    public Stage getStage() {
        return stage;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
        setStageProperties();
    }

    public Scene getCurrentScene(){
        return stage.getScene();
    }

    private void initMaps(){
        screenMap.put("login", CircoloSportivoApplication.class.getResource("views/login-view.fxml"));
        screenMap.put("member", CircoloSportivoApplication.class.getResource("views/member-view.fxml"));
        screenMap.put("admin", CircoloSportivoApplication.class.getResource("views/admin-view.fxml"));
        screenMap.put("manageMembers", CircoloSportivoApplication.class.getResource("views/manage-member-view.fxml"));
        screenMap.put("manageActivities",CircoloSportivoApplication.class.getResource("views/manage-activities-view.fxml"));
        cssMap.put("login", CircoloSportivoApplication.class.getResource("styles/login-view.css"));
        cssMap.put("member", CircoloSportivoApplication.class.getResource("styles/member-view.css"));
        cssMap.put("admin", CircoloSportivoApplication.class.getResource("styles/admin-view.css"));
        cssMap.put("manage", CircoloSportivoApplication.class.getResource("styles/manage-view.css"));

    }

    private void setStageProperties(){
        stage.centerOnScreen();
        stage.setTitle("");
        stage.resizableProperty().setValue(false);
        stage.setResizable(false);
    }

}
