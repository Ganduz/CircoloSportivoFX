package com.example.circolosportivofx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {

    @FXML
    private Button buttonLogin;

    @FXML
    private TextField emailLogin;

    @FXML
    private PasswordField passwordLogin;

    public void login(ActionEvent actionEvent) throws IOException {
        String email = emailLogin.getText();
        String password = passwordLogin.getText();

        for (Member member : Data.getInstance().getMembers()) {
            if (member.getEmail().equals(email) && member.getPassword().equals(password)) {
                // Successful login
                System.out.println("Login successful for: " + member.getEmail());
                Data.getInstance().writeOutput("Login successful for: " + member.getName() + " " + member.getSurname());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Successful");
                alert.setContentText("You have successfully logged in!");
                alert.setResizable(false);
                alert.showAndWait();
                Data.getInstance().setLoggedUser(member);
                Scene scene;
                FXMLLoader fxmlLoader;
                if (member instanceof Admin) {
                    fxmlLoader = new FXMLLoader(CircoloSportivoApplication.class.getResource("views/admin-view.fxml"));
                    scene = new Scene(fxmlLoader.load(), 600, 400);

                }
                else {
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Login Failed");
        alert.setContentText("Incorrect email or password");
        alert.setResizable(false);
        alert.showAndWait();
    }
}
