package com.example.gestionsalledesport.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.gestionsalledesport.HelloApplication;
import com.example.gestionsalledesport.services.StatisticsService;

import java.io.IOException;

public class MenuController {
    @FXML
    private Label userNameLabel;
    @FXML
    private Label totalAbonnementsLabel;
    @FXML
    private Label activeAbonnementsLabel;
    @FXML
    private Label totalUsersLabel;
    @FXML
    private Label activeUsersLabel;
    @FXML
    private Label totalCoachesLabel;

    private String nom;
    private String prenom;
    private final StatisticsService statisticsService = new StatisticsService();

    // Update the username label when the controller is initialized
    public void initialize() {
        if (nom != null && prenom != null) {
            userNameLabel.setText("Logged in as: " + nom + " " + prenom);
        }
        updateStatisticsLabels();
    }

    // Setter method to set the logged-in user's name
    public void setLoggedInUser(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;

        // If the controller is already initialized, update the username label immediately
        if (userNameLabel != null) {
            userNameLabel.setText("Logged in as: " + nom + " " + prenom);
        }
    }

    // Method to navigate to the Gestion d'abonnement interface
    public void navigateToAbonnement(ActionEvent event) {
        navigateToInterface("abonnement.fxml");
    }

    // Method to navigate to the Gestion des cours interface
    public void navigateToCours(ActionEvent event) {
        navigateToInterface("cours.fxml");
    }

    // Method to navigate to the Gestion des cours interface
    public void addNewUser(ActionEvent event) {
        navigateToInterface("add_user.fxml");
    }
    public void navigateToCoach(ActionEvent event) {
        navigateToInterface("coach.fxml");
    }

    @FXML
    private void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            
            // Get the current stage
            Stage stage = (Stage) userNameLabel.getScene().getWindow();
            
            // Set the new scene to the stage
            stage.setScene(scene);
            
            // Close the current stage (optional, depends on your requirements)
            // stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    // Generic method to navigate to any interface based on FXML file name
    private void navigateToInterface(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlFileName));
            Parent root = loader.load();

            // Pass the logged-in user's name to the controller of the next scene
            if (fxmlFileName.equals("abonnement.fxml") || fxmlFileName.equals("cours.fxml") || fxmlFileName.equals("add_user.fxml")) {
                Scene scene = new Scene(root);
                Stage stage = (Stage) userNameLabel.getScene().getWindow();
                stage.setScene(scene);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateStatisticsLabels() {
        totalAbonnementsLabel.setText("Total Abonnements: " + statisticsService.getTotalAbonnements());
        activeAbonnementsLabel.setText("Active Abonnements: " + statisticsService.getActiveAbonnements());
        totalUsersLabel.setText("Total Users: " + statisticsService.getTotalUsers());
        totalCoachesLabel.setText("Total Coaches: " + statisticsService.getTotalCoaches());
    }
}
