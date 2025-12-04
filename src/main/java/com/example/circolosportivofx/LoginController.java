package com.example.circolosportivofx;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private TextField emailLogin;

    @FXML
    private PasswordField passwordLogin;

    @FXML
    private Label labelResult;


    public void login(ActionEvent actionEvent) throws IOException {
        String email = emailLogin.getText();
        String password = passwordLogin.getText();

        //labelResult.setText("");
        for (Member member : Data.getInstance().getMembers()) {
            if (member.getEmail().equals(email) && member.getPassword().equals(SHA256Hashing.generateSHA256Hash(password))) {
                // Successful login
                Data.getInstance().setLoggedUser(member);
                Scene scene;
                FXMLLoader fxmlLoader;
                if (member instanceof Admin) {
                    Data.getInstance().writeOutput("Admin access granted to: " + member.getName() + " " + member.getSurname());
                    fxmlLoader = new FXMLLoader(CircoloSportivoApplication.class.getResource("views/admin-view.fxml"));
                    scene = new Scene(fxmlLoader.load(), 600, 520);

                }
                else {
                    Data.getInstance().writeOutput("Member access granted to: " + member.getName() + " " + member.getSurname());
                    fxmlLoader = new FXMLLoader(CircoloSportivoApplication.class.getResource("views/member-view.fxml"));
                    scene = new Scene(fxmlLoader.load(), 540, 360);
                }
                scene.getStylesheets().add(
                        Objects.requireNonNull(getClass().getResource("styles/member-view.css")).toExternalForm()
                );


                Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
                return;
            }
        }
        // Failed login
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
