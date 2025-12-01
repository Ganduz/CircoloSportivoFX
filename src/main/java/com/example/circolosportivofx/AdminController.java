package com.example.circolosportivofx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AdminController {


    @FXML
    public void manageMembersButtonAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CircoloSportivoApplication.class.getResource("views/manage-member-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 616, 670);
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("styles/manage-view.css")).toExternalForm()
        );
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


    @FXML
    public void manageActivitiesButtonAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CircoloSportivoApplication.class.getResource("views/manage-activities-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 616, 503);
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("styles/manage-view.css")).toExternalForm()
        );
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void subscribeActivityButtonAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CircoloSportivoApplication.class.getResource("views/member-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 540, 360);
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("styles/member-view.css")).toExternalForm()
        );
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
