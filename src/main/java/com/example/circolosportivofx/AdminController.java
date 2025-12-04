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
        SceneController.getInstance((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).changeScene("manageMembers");
    }


    @FXML
    public void manageActivitiesButtonAction(ActionEvent actionEvent) throws IOException {
        SceneController.getInstance((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).changeScene("manageActivities");
    }

    public void subscribeActivityButtonAction(ActionEvent actionEvent) throws IOException {
        SceneController.getInstance((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).changeScene("member");
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        SceneController.getInstance((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).changeScene("login");
        Data.getInstance().writeOutput("Member: " + Data.getInstance().getLoggedUser().getName() + Data.getInstance().getLoggedUser().getSurname() + " logged out.");
        Data.getInstance().setLoggedUser(null);
    }

}
