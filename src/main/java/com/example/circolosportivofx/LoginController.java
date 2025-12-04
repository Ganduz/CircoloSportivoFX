package com.example.circolosportivofx;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button loginButton;

    @FXML
    private TextField emailLogin;

    @FXML
    private PasswordField passwordLogin;

    @FXML
    private Label labelResult;

    public void initialize(URL location, ResourceBundle resources) {
        passwordLogin.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginButton.fire();
            }
        });
    }


    public void login(ActionEvent actionEvent) throws IOException {
        String email = emailLogin.getText();
        String password = passwordLogin.getText();

        for (Member member : Data.getInstance().getMembers()) {
            if (member.getEmail().equals(email) && member.getPassword().equals(SHA256Hashing.generateSHA256Hash(password))) {
                // Successful login
                Data.getInstance().writeOutput("Login Successful: " + member.getName() + " " + member.getSurname() + " logged in.");
                Data.getInstance().setLoggedUser(member);
                if (member instanceof Admin) {
                    SceneController.getInstance((Stage)((Node) actionEvent.getSource()).getScene().getWindow()).changeScene("admin");
                }
                else {
                    SceneController.getInstance((Stage)((Node) actionEvent.getSource()).getScene().getWindow()).changeScene("member");
                }

                return;
            }
        }
        labelResult.setText("Login Failed: Incorrect email or password");
        Data.getInstance().writeOutput("Login Failed: Incorrect email or password");
        emailLogin.setText("");
        passwordLogin.setText("");
        PauseTransition LoginErrorTransition = new PauseTransition();
        LoginErrorTransition.setDuration(Duration.seconds(1.5));
        LoginErrorTransition.setOnFinished(e -> labelResult.setText(""));
        LoginErrorTransition.play();
    }

}
