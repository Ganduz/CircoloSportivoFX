package com.example.circolosportivofx.controllers;

import com.example.circolosportivofx.Activity;
import com.example.circolosportivofx.Admin;
import com.example.circolosportivofx.Data;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for member activities in the JavaFX application.
 */
public class MemberController implements Initializable {
    @FXML
    private Label titleLabel;

    @FXML
    private ComboBox<Activity> comboSubscribe;

    @FXML
    private ComboBox<Activity> comboUnsubscribe;

    @FXML
    private Label resultLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Button backButton;


    /**
     * Initializes the controller class (method inherited from interface).
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        populateComboBox();
        if (Data.getInstance().getLoggedUser() instanceof Admin) {
            backButton.setVisible(true);
            logoutButton.setVisible(false);
        }
        else  {
            backButton.setVisible(false);
            logoutButton.setVisible(true);
        }
    }

    /**
     * Populates the subscribe and unsubscribe combo boxes based on the logged-in user's subscriptions.
     */
    private void populateComboBox() {
        comboUnsubscribe.getItems().clear();
        comboSubscribe.getItems().clear();
        comboSubscribe.getSelectionModel().clearSelection();
        comboSubscribe.setValue(null);
        comboUnsubscribe.getSelectionModel().clearSelection();
        comboUnsubscribe.setValue(null);

        for (Activity activity : Data.getInstance().getActivities()) {
            if (activity.isSubscribed(Data.getInstance().getLoggedUser())) {
                comboUnsubscribe.getItems().add(activity);
            }
            else {
                comboSubscribe.getItems().add(activity);
            }
        }
        comboSubscribe.getSelectionModel().selectFirst();
        comboUnsubscribe.getSelectionModel().selectFirst();

    }

    /**
     * Unsubscribes the logged-in user from the selected activity.
     * @param actionEvent button click event
     */
    public void unsubscribe(ActionEvent actionEvent) {
        if (comboUnsubscribe.getSelectionModel().getSelectedItem() != null) {
            Activity activity = comboUnsubscribe.getSelectionModel().getSelectedItem();
            activity.removeSubscriber(Data.getInstance().getLoggedUser());
            populateComboBox();
            Data.getInstance().writeOutput("User " + Data.getInstance().getLoggedUser().getName() + " " + Data.getInstance().getLoggedUser().getSurname() + " unsubscribed from activity " + activity.getName());
            Data.getInstance().writeOutput("Activity List : " + activity.getSubscribers());
            resultLabel.setText("You have successfully unsubscribed from " +  activity.getName() + "!");
            PauseTransition unsubscribeTransition = new PauseTransition();
            unsubscribeTransition.setDuration(Duration.seconds(3));
            unsubscribeTransition.setOnFinished(e -> resultLabel.setText(""));
            unsubscribeTransition.play();
        }
    }

    /**
     * Subscribes the logged-in user to the selected activity.
     * @param actionEvent button click event
     */
    public void subscribe(ActionEvent actionEvent) {
        if (comboSubscribe.getSelectionModel().getSelectedItem() != null) {
            Activity activity = comboSubscribe.getSelectionModel().getSelectedItem();
            activity.addSubscriber(Data.getInstance().getLoggedUser());
            populateComboBox();
            Data.getInstance().writeOutput("User " + Data.getInstance().getLoggedUser().getName() + " " + Data.getInstance().getLoggedUser().getSurname() + " subscribed to activity " + activity.getName());
            Data.getInstance().writeOutput("Activity List : " + activity.getSubscribers());
            resultLabel.setText("You have successfully subscribed to " +  activity.getName() + "!");
            PauseTransition subscribeTransition = new PauseTransition();
            subscribeTransition.setDuration(Duration.seconds(3));
            subscribeTransition.setOnFinished(e -> resultLabel.setText(""));
            subscribeTransition.play();
        }
    }

    /**
     * Logs out the current user and returns to the login scene.
     * @param actionEvent button click event
     * @throws IOException if scene change fails
     */
    public void logout(ActionEvent actionEvent) throws IOException {
        SceneController.getInstance((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).changeScene("login");
        Data.getInstance().writeOutput("Member: " + Data.getInstance().getLoggedUser().getName() + Data.getInstance().getLoggedUser().getSurname() + " logged out.");
        Data.getInstance().setLoggedUser(null);
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
