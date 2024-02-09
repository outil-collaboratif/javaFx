package com.example.gestionsalledesport.controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import com.example.gestionsalledesport.services.LoginService;
import com.example.gestionsalledesport.HelloApplication;
import com.example.gestionsalledesport.models.Admin;

public class LoginController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private LoginService loginService;

    public LoginController() {
        loginService = new LoginService();
    }

    // Handle login button action
    @FXML
    public void login(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Validate credentials using LoginService
        Admin admin = loginService.getUserByEmail(email);
        if (admin != null && admin.getPassword().equals(password)) {
            navigateToMenuPage(admin.getNom(), admin.getPrenom());
        } else {
            errorLabel.setText("Invalid email or password");
        }
    }

   // Navigate to menu.fxml
private void navigateToMenuPage(String nom, String prenom) {
    try {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("menu.fxml"));
        Parent root = loader.load();

        // Pass the nom and prenom to the MenuController
        MenuController menuController = loader.getController();
        menuController.setLoggedInUser(nom, prenom);

        Scene scene = new Scene(root);
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.setScene(scene);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
