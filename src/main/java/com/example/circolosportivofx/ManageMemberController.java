package com.example.circolosportivofx;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;

/**
 * Controller class for managing members (admin feature) in the JavaFX application.
 */
public class ManageMemberController implements Initializable{

    @FXML
    private ComboBox<Member> memberComboBox;

    @FXML
    private Label createUserLabel;

    @FXML
    private TextField nameField, surnameField, emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private RadioButton radioMember;

    @FXML
    private RadioButton radioAdmin;

    private final Admin currentUser = (Admin) Data.getInstance().getLoggedUser();

    /**
     * Initializes the controller class (method inherited from interface).
     * @param location
     * @param resources
     */
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        ToggleGroup tg = new ToggleGroup();
        radioMember.setToggleGroup(tg);
        radioAdmin.setToggleGroup(tg);
        populateMemberList();
        radioMember.setSelected(true);
    }

    /**
     * Populates the member combo box with members excluding the logged-in user.
     */
    private void populateMemberList() {
        memberComboBox.getItems().clear();
        memberComboBox.getSelectionModel().clearSelection();
        memberComboBox.setValue(null);

        for (Member member : Data.getInstance().getMembers()) {
            if (member instanceof Admin && !member.isEqual(currentUser)) {
                memberComboBox.getItems().add(0, member);
            }
            else if (member instanceof Member && !member.isEqual(currentUser)) {
                memberComboBox.getItems().add(member);
            }
        }
    }

    /**
     * Removes the selected member from the system.
     * @param actionEvent button click event
     */
    public void removeMember(ActionEvent actionEvent) {
        Member selected = memberComboBox.getSelectionModel().getSelectedItem();
        currentUser.removeMember(selected, Data.getInstance());
        Data.getInstance().writeOutput("Member " + selected.getName() + " " + selected.getSurname() + " removed by Admin " + currentUser.getName() + " " + currentUser.getSurname());
        populateMemberList();
    }

    /**
     * Creates a new member or admin based on input fields.
     * @param actionEvent button click event
     */
    public void createMember(ActionEvent actionEvent) {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (nameField.getText().isEmpty() || surnameField.getText().isEmpty() || emailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            createUserLabel.setText("Please fill all the fields");
            PauseTransition createUserErrorTransition = new PauseTransition();
            createUserErrorTransition.setDuration(Duration.seconds(1.5));
            createUserErrorTransition.setOnFinished(e -> createUserLabel.setText(""));
            createUserErrorTransition.play();
            Data.getInstance().writeOutput("Create Member Error: there are empty fields");
            return;
        }
        else if (!emailIsAvailable(email)) {
            createUserLabel.setText("There is already a user with this email");
            PauseTransition createUserErrorTransition = new PauseTransition();
            createUserErrorTransition.setDuration(Duration.seconds(1.5));
            createUserErrorTransition.setOnFinished(e -> createUserLabel.setText(""));
            createUserErrorTransition.play();
            Data.getInstance().writeOutput("Create Member Error: email " + email + " is already in use");
            return;
        }

        if (radioAdmin.isSelected()) {
            currentUser.addAdmin(new Admin(name, surname, email, SHA256Hashing.generateSHA256Hash(password)), Data.getInstance());
            Data.getInstance().writeOutput("Admin " + name + " " + surname + " created by Admin " + currentUser.getName() + " " + currentUser.getSurname());
            createUserLabel.setText("Admin created successfully");
        } else {
            currentUser.addMember(name, surname, email, SHA256Hashing.generateSHA256Hash(password), Data.getInstance());
            Data.getInstance().writeOutput("Member " + name + " " + surname + " created by Admin " + currentUser.getName() + " " + currentUser.getSurname());
            createUserLabel.setText("Member created successfully");

        }
        Data.getInstance().writeOutput(Data.getInstance().getMembers().toString());
        populateMemberList();
        createUserLabel.setStyle("-fx-text-fill: green;");
        PauseTransition createUserErrorTransition = new PauseTransition();
        createUserErrorTransition.setDuration(Duration.seconds(1.5));
        createUserErrorTransition.setOnFinished(e -> {createUserLabel.setText(""); createUserLabel.setStyle("-fx-text-fill: red;");});
        createUserErrorTransition.play();
        clearFields();
    }


    /**
     * Checks if the provided email is available (not already used by another member).
     * @param email the email to check
     * @return true if the email is available, false otherwise
     */
    private Boolean emailIsAvailable(String email) {
        for (Member member : Data.getInstance().getMembers()) {
            if (member.getEmail().equals(email))
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
        SceneController.getInstance((Stage) memberComboBox.getScene().getWindow()).changeScene("admin");
    }

    /**
     * Clears the input fields.
     */
    private void clearFields() {
        nameField.clear();
        surnameField.clear();
        emailField.clear();
        passwordField.clear();
    }

}
