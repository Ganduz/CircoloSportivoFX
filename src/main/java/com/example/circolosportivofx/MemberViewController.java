package com.example.circolosportivofx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MemberViewController implements Initializable {
    @FXML
    private Label titleLabel;

    @FXML
    private ComboBox<String> comboSubscribe;

    @FXML
    private ComboBox<String> comboUnsubscribe;


    public void initialize(URL location, ResourceBundle resources) {
        titleLabel.setText(titleLabel.getText() + " " + Data.getInstance().getLoggedUser().getName());
        populateComboBox();
    }


    private void populateComboBox() {
        comboUnsubscribe.getItems().clear();
        comboSubscribe.getItems().clear();

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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Unsubscribe Successfully");
            alert.setContentText("You have successfully unsubscribed from " + activity.getName() + "!");
            alert.setResizable(false);
            alert.showAndWait();
        }
    }

    public void subscribe(ActionEvent actionEvent) throws IOException {
        if (comboSubscribe.getSelectionModel().getSelectedItem() != null) {
            Activity activity = Data.getInstance().getActivityByName(comboSubscribe.getSelectionModel().getSelectedItem());
            activity.addSubscriber(Data.getInstance().getLoggedUser());
            populateComboBox();
            Data.getInstance().writeOutput("User " + Data.getInstance().getLoggedUser().getName() + " " + Data.getInstance().getLoggedUser().getSurname() + " subscribed to activity " + activity.getName());
            Data.getInstance().writeOutput("Activity List : " + activity.getSubscribers());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Subscription Successful");
            alert.setContentText("You have successfully subscribed to " + activity.getName() + "!");
            alert.setResizable(false);
            alert.showAndWait();
        }
    }
}
