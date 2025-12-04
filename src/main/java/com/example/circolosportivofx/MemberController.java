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
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

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


    public void initialize(URL location, ResourceBundle resources) {
        titleLabel.setText(titleLabel.getText() + " " + Data.getInstance().getLoggedUser().getName());
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

    public void subscribe(ActionEvent actionEvent) throws IOException {
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

    public void logout(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CircoloSportivoApplication.class.getResource("views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("styles/login-view.css")).toExternalForm()
        );
        Stage stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.resizableProperty().setValue(false);
        stage.show();
        Data.getInstance().writeOutput("Member: " + Data.getInstance().getLoggedUser().getName() + Data.getInstance().getLoggedUser().getSurname() + " logged out.");
        Data.getInstance().setLoggedUser(null);
    }

    public void previousScene(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CircoloSportivoApplication.class.getResource("views/admin-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 520);
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("styles/admin-view.css")).toExternalForm()
        );
        Stage stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        stage.centerOnScreen();
        stage.setTitle("");
        stage.setScene(scene);
        stage.resizableProperty().setValue(false);
        stage.show();
    }
}
