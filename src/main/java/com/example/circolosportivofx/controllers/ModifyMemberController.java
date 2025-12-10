package com.example.circolosportivofx.controllers;

import com.example.circolosportivofx.Admin;
import com.example.circolosportivofx.Data;
import com.example.circolosportivofx.Member;
import com.example.circolosportivofx.SHA256Hashing;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for modifying member information in the JavaFX application.
 */

public class ModifyMemberController implements Initializable {

    @FXML
    private ComboBox<Member> memberComboBox;

    @FXML
    private TextField nameField, surnameField, emailField,  passwordField;

    @FXML
    private Button backButton, confirmButton;

    @FXML
    private Label infoLabel;

    public void initialize(URL location, ResourceBundle resources) {
        populateMemberList();
    }

    /**
     * Navigates back to the admin scene.
     * @param actionEvent button click event
     * @throws IOException if scene change fails
     */
    public void previousScene(ActionEvent actionEvent) throws IOException {
        SceneController.getInstance((javafx.stage.Stage) backButton.getScene().getWindow()).changeScene("admin");
    }

    /**
     * Populates the member combo box with members excluding the logged-in user.
     */
    private void populateMemberList() {
        memberComboBox.getItems().clear();
        memberComboBox.getSelectionModel().clearSelection();
        memberComboBox.setValue(null);

        for (Member member : Data.getInstance().getMembers()) {
            if (member instanceof Admin && !member.isEqual(Data.getInstance().getLoggedUser())) {
                memberComboBox.getItems().add(0, member);
            }
            else if (member instanceof Member && !member.isEqual(Data.getInstance().getLoggedUser())) {
                memberComboBox.getItems().add(member);
            }
        }
    }

    /**
     * Modifies the selected member's information based on input fields.
     * @param actionEvent button click event
     */
    public void modifyMember(ActionEvent actionEvent) {
        Admin admin = (Admin) Data.getInstance().getLoggedUser();
        if(memberComboBox.getValue() == null) {
            infoLabel.setText("Select a member to modify.");
            PauseTransition selectMemberErrorTransition = new PauseTransition();
            selectMemberErrorTransition.setDuration(Duration.seconds(1.5));
            selectMemberErrorTransition.setOnFinished(e -> infoLabel.setText(""));
            selectMemberErrorTransition.play();
        }
        else {
            if(nameField.getText().isEmpty() || surnameField.getText().isEmpty() || emailField.getText().isEmpty()) {
                infoLabel.setText("All fields must be filled.");
                PauseTransition fillFieldErrorTransition = new PauseTransition();
                fillFieldErrorTransition.setDuration(Duration.seconds(1.5));
                fillFieldErrorTransition.setOnFinished(e -> infoLabel.setText(""));
                fillFieldErrorTransition.play();
            } else {
                infoLabel.setStyle("-fx-text-fill: green");
                String password;
                if(passwordField.getText().isEmpty()) {
                    Data.getInstance().writeOutput("Modified member info : " + memberComboBox.getValue().getName() + " " + memberComboBox.getValue().getSurname() + ", " + memberComboBox.getValue().getEmail() + " -> " + nameField.getText() + " " + surnameField.getText() + ", " + emailField.getText() + " by Admin " + admin.getName() + " " + admin.getSurname() + " (password unchanged)");
                    infoLabel.setText("Successfully modified (password unchanged)");
                    password = memberComboBox.getValue().getPassword();
                } else {
                    Data.getInstance().writeOutput("Modified member info : " + memberComboBox.getValue().getName() + " " + memberComboBox.getValue().getSurname() + ", " + memberComboBox.getValue().getEmail() + " -> " + nameField.getText() + " " + surnameField.getText() + ", " + emailField.getText() + " by Admin " + admin.getName() + " " + admin.getSurname() + "(password changed with " + passwordField.getText() + ")");
                    infoLabel.setText("Successfully modified");
                    password = passwordField.getText();
                }
                admin.modifyMember(memberComboBox.getValue(), nameField.getText(), surnameField.getText(), emailField.getText(), SHA256Hashing.generateSHA256Hash(passwordField.getText()));
                PauseTransition modifyMemberTransition = new PauseTransition();
                modifyMemberTransition.setDuration(Duration.seconds(1.5));
                modifyMemberTransition.setOnFinished(e -> { infoLabel.setText(""); infoLabel.setStyle("-fx-text-fill: red;");});
                modifyMemberTransition.play();
                clearFields();

            }
        }
    }

    /**
     * Handles member selection from the combo box and populates fields.
     * @param actionEvent selection event
     */
    public void memberSelected(ActionEvent actionEvent) {
        if(memberComboBox.getValue() == null) {
            nameField.setText("");
            surnameField.setText("");
            emailField.setText("");
            passwordField.setText("");
        } else {
            Member selected = memberComboBox.getValue();
            nameField.setText(selected.getName());
            surnameField.setText(selected.getSurname());
            emailField.setText(selected.getEmail());
            passwordField.setText("");
        }
    }

    /**
     * Clears all input fields and selections.
     */
    private void clearFields() {
        nameField.clear();
        surnameField.clear();
        emailField.clear();
        passwordField.clear();
        memberComboBox.getSelectionModel().clearSelection();
    }
}
