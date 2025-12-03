package com.example.circolosportivofx;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MemberController implements Initializable {
    @FXML
    private Label titleLabel;

    @FXML
    private ComboBox<String> comboSubscribe;

    @FXML
    private ComboBox<String> comboUnsubscribe;

    @FXML
    private Label resultLabel;


    public void initialize(URL location, ResourceBundle resources) {
        titleLabel.setText(titleLabel.getText() + " " + Data.getInstance().getLoggedUser().getName());
        populateComboBox();
    }

    private void populateComboBox() {
        comboUnsubscribe.getItems().clear();
        comboSubscribe.getItems().clear();
        comboSubscribe.getSelectionModel().clearSelection();
        comboSubscribe.setValue(null);
        comboUnsubscribe.getSelectionModel().clearSelection();
        comboUnsubscribe.setValue(null);

        for (Activity activity : Data.getInstance().getActivities()) {
            if (activity.getSubscribers().contains(Data.getInstance().getLoggedUser())) {
                comboUnsubscribe.getItems().add(activity.getName());
            }
            else {
                comboSubscribe.getItems().add(activity.getName());
            }
        }
        comboSubscribe.getSelectionModel().selectFirst();
        comboUnsubscribe.getSelectionModel().selectFirst();

    }

    public void unsubscribe(ActionEvent actionEvent) throws IOException {
        if (comboUnsubscribe.getSelectionModel().getSelectedItem() != null) {
            Activity activity = Data.getInstance().getActivityByName(comboUnsubscribe.getSelectionModel().getSelectedItem());
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

    public void subscribe(ActionEvent actionEvent) throws IOException {
        if (comboSubscribe.getSelectionModel().getSelectedItem() != null) {
            Activity activity = Data.getInstance().getActivityByName(comboSubscribe.getSelectionModel().getSelectedItem());
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

    public void logout(ActionEvent actionEvent) {
    }

    public void previousScene(ActionEvent actionEvent) {
    }
}
