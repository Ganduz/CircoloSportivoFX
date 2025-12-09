package com.example.circolosportivofx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

/**
 * SceneController is a singleton class that manages scene transitions in the JavaFX application.
 * It maintains mappings of scene names to their corresponding FXML file URLs and CSS file URLs.
 * The class provides methods to change scenes and set stage properties.
 */

public class SceneController {

    private static SceneController instance;
    private final HashMap<String, URL> screenMap = new HashMap<>();
    private final HashMap<String , URL> cssMap = new HashMap<>();
    private Stage stage;

    /**
     * Returns the singleton instance of SceneController.
     * If the instance does not exist, it creates a new one with the provided stage.
     *
     * @param stage The primary stage of the application.
     * @return The singleton instance of SceneController.
     */
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

    /**
     * Changes the current scene to the specified scene name.
     *
     * @param sceneName The name of the scene to switch to.
     * @throws IOException If the FXML file cannot be loaded.
     */
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

    /**
     * Initializes the mappings of scene names to their corresponding FXML and CSS file URLs.
     */
    private void initMaps(){
        screenMap.put("login", CircoloSportivoApplication.class.getResource("views/login-view.fxml"));
        screenMap.put("member", CircoloSportivoApplication.class.getResource("views/member-view.fxml"));
        screenMap.put("admin", CircoloSportivoApplication.class.getResource("views/admin-view.fxml"));
        screenMap.put("manageMembers", CircoloSportivoApplication.class.getResource("views/manage-member-view.fxml"));
        screenMap.put("manageActivities",CircoloSportivoApplication.class.getResource("views/manage-activities-view.fxml"));
        screenMap.put("modifyMember", CircoloSportivoApplication.class.getResource("views/modify-member-view.fxml"));
        cssMap.put("login", CircoloSportivoApplication.class.getResource("styles/login-view.css"));
        cssMap.put("member", CircoloSportivoApplication.class.getResource("styles/member-view.css"));
        cssMap.put("admin", CircoloSportivoApplication.class.getResource("styles/admin-view.css"));
        cssMap.put("manage", CircoloSportivoApplication.class.getResource("styles/manage-view.css"));
        cssMap.put("modifyMember", CircoloSportivoApplication.class.getResource("styles/modify-member-view.css"));

    }

    /**
     * Sets properties for the primary stage
     */
    private void setStageProperties(){
        stage.centerOnScreen();
        stage.setTitle("");
        stage.resizableProperty().setValue(false);
        stage.setResizable(false);
    }

}
