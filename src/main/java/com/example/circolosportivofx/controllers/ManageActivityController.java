package com.example.circolosportivofx.controllers;

import com.example.circolosportivofx.*;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;

/**
 * Controller class for managing activities (admin feature) in the JavaFX application.
 */
public class ManageActivityController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<Activity> activitiesComboBox;

    @FXML
    private RadioButton radioCourse;

    @FXML
    private RadioButton radioCompetition;

    @FXML
    private Label infoLabel;

    private final Admin currentUser = (Admin) Data.getInstance().getLoggedUser();


    /**
     * Initializes the controller class (method inherited from interface).
     * @param location
     * @param resources
     */
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        ToggleGroup tg = new ToggleGroup();
        radioCourse.setToggleGroup(tg);
        radioCompetition.setToggleGroup(tg);
        populateActivityList();
        radioCourse.setSelected(true);
    }

    /**
     * Populates the activity combo box with all activities.
     */
    private void populateActivityList() {
        activitiesComboBox.getItems().clear();
        activitiesComboBox.getSelectionModel().clearSelection();
        activitiesComboBox.setValue(null);

        activitiesComboBox.getItems().addAll(Data.getInstance().getActivities());
    }

    /**
     * Removes the selected activity.
     * @param actionEvent button click event
     */
    public void removeActivity(ActionEvent actionEvent) {
        Activity selected = activitiesComboBox.getSelectionModel().getSelectedItem();
        currentUser.removeActivity(selected, Data.getInstance());
        Data.getInstance().writeOutput("Activity " + selected.getName() + " removed by Admin " + currentUser.getName() + " " + currentUser.getSurname());
        populateActivityList();
    }

    /**
     * Creates a new activity based on input fields.
     * @param actionEvent button click event
     */
    public void createActivity(ActionEvent actionEvent) {
        String name = nameField.getText();

        if (nameField.getText().isEmpty()) {
            infoLabel.setText("Please fill the field");
            PauseTransition createUserErrorTransition = new PauseTransition();
            createUserErrorTransition.setDuration(Duration.seconds(1.5));
            createUserErrorTransition.setOnFinished(e -> infoLabel.setText(""));
            createUserErrorTransition.play();
            Data.getInstance().writeOutput("Create Activity Error: the field is empty");
            return;
        }
        else if (!nameIsAvailable(name)) {
            infoLabel.setText("There is already an activity with this name");
            PauseTransition createUserErrorTransition = new PauseTransition();
            createUserErrorTransition.setDuration(Duration.seconds(1.5));
            createUserErrorTransition.setOnFinished(e -> infoLabel.setText(""));
            createUserErrorTransition.play();
            Data.getInstance().writeOutput("Create Activity Error: an activity with name " + name + " already exists");
            return;
        }

        if (radioCompetition.isSelected()) {
            currentUser.addActivity(new Competition(name), Data.getInstance());
            Data.getInstance().writeOutput("Competition " + name + " created by Admin " + currentUser.getName() + " " + currentUser.getSurname());
            infoLabel.setText("Admin created successfully");
        } else {
            currentUser.addActivity(new Course(name), Data.getInstance());
            Data.getInstance().writeOutput("Course " + name +  " created by Admin " + currentUser.getName() + " " + currentUser.getSurname());
            infoLabel.setText("Course created successfully");

        }
        Data.getInstance().writeOutput(Data.getInstance().getMembers().toString());
        populateActivityList();
        infoLabel.setStyle("-fx-text-fill: green;");
        PauseTransition createUserErrorTransition = new PauseTransition();
        createUserErrorTransition.setDuration(Duration.seconds(1.5));
        createUserErrorTransition.setOnFinished(e -> {infoLabel.setText(""); infoLabel.setStyle("-fx-text-fill: red;");});
        createUserErrorTransition.play();
        nameField.clear();
    }

    /**
     * Checks if the activity name is available (not already used).
     * @param name the activity name to check
     * @return true if the name is available, false otherwise
     */
    private Boolean nameIsAvailable(String name) {
        for (Activity activity : Data.getInstance().getActivities()) {
            if (activity.getName().toLowerCase().equals(name.toLowerCase()))
                return false;
        }
        return true;
    }

    /**
     * Navigates back to the admin scene.
     * @param actionEvent button click event
     * @throws IOException if scene change fails
     */
    public void previousScene(ActionEvent actionEvent) throws IOException {
        SceneController.getInstance((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).changeScene("admin");
    }
}
