package com.example.circolosportivofx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller class for admin functionalities in the JavaFX application.
 */
public class AdminController {

    /**
     * Navigates to the manage members scene.
     * @param actionEvent button click event
     * @throws IOException if scene change fails
     */
    @FXML
    public void manageMembersButtonAction(ActionEvent actionEvent) throws IOException {
        SceneController.getInstance((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).changeScene("manageMembers");
    }

    /**
     * Navigates to the manage activities scene.
     * @param actionEvent button click event
     * @throws IOException if scene change fails
     */
    @FXML
    public void manageActivitiesButtonAction(ActionEvent actionEvent) throws IOException {
        SceneController.getInstance((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).changeScene("manageActivities");
    }

    /**
     * Navigates to the member (subscribe/unsubscribe activities) scene.
     * @param actionEvent button click event
     * @throws IOException if scene change fails
     */
    public void subscribeActivityButtonAction(ActionEvent actionEvent) throws IOException {
        SceneController.getInstance((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).changeScene("member");
    }

    /**
     * Logs out the current user and navigates to the login scene.
     * @param actionEvent button click event
     * @throws IOException if scene change fails
     */
    public void logout(ActionEvent actionEvent) throws IOException {
        SceneController.getInstance((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).changeScene("login");
        Data.getInstance().writeOutput("Member: " + Data.getInstance().getLoggedUser().getName() + " " + Data.getInstance().getLoggedUser().getSurname() + " logged out.");
        Data.getInstance().setLoggedUser(null);
    }

    /**
     * Navigates to the modify member scene.
     * @param actionEvent button click event
     * @throws IOException if scene change fails
     */
    public void modifyMembersButtonAction(ActionEvent actionEvent) throws IOException {
        SceneController.getInstance((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).changeScene("modifyMember");
    }
}
