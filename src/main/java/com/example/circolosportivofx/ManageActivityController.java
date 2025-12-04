package com.example.circolosportivofx;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

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


    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        ToggleGroup tg = new ToggleGroup();
        radioCourse.setToggleGroup(tg);
        radioCompetition.setToggleGroup(tg);
        populateActivityList();
        radioCourse.setSelected(true);
    }

    private void populateActivityList() {
        activitiesComboBox.getItems().clear();
        activitiesComboBox.getSelectionModel().clearSelection();
        activitiesComboBox.setValue(null);

        activitiesComboBox.getItems().addAll(Data.getInstance().getActivities());
    }

    public void removeActivity(ActionEvent actionEvent) {
        Activity selected = activitiesComboBox.getSelectionModel().getSelectedItem();
        currentUser.removeActivity(selected, Data.getInstance());
        Data.getInstance().writeOutput("Activity " + selected.getName() + " removed by Admin " + currentUser.getName() + " " + currentUser.getSurname());
        populateActivityList();
    }

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
        return;
    }


    private Boolean nameIsAvailable(String name) {
        for (Activity activity : Data.getInstance().getActivities()) {
            if (activity.getName().toLowerCase().equals(name.toLowerCase()))
                return false;
        }
        return true;
    }

    public void previousScene(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CircoloSportivoApplication.class.getResource("views/admin-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 520);
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("styles/admin-view.css")).toExternalForm()
        );
        Stage stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.resizableProperty().setValue(false);
        stage.show();
    }
}
