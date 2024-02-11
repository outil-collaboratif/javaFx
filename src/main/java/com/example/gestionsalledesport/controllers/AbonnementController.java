package com.example.gestionsalledesport.controllers;

import com.example.gestionsalledesport.HelloApplication;
import com.example.gestionsalledesport.models.Abonnement;
import com.example.gestionsalledesport.models.User;
import com.example.gestionsalledesport.services.AbonnementService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AbonnementController {

    @FXML
    private TextField dateField;

    @FXML
    private TextField dureeField;

    @FXML
    private TextField tarifField;

    @FXML
    private TextField userIdField;

    @FXML
    private TableView<Abonnement> abonnementsTable;

    private final AbonnementService abonnementService = new AbonnementService();

    @FXML
    private void createAbonnement() {
        String date = dateField.getText();
        String duree = dureeField.getText();
        double tarif = Double.parseDouble(tarifField.getText());
        long userId = Long.parseLong(userIdField.getText());

        Abonnement abonnement = new Abonnement(null, date, duree, tarif, new User(userId));
        abonnementService.insertAbonnement(abonnement);
        clearFields();
        loadAbonnements();
    }

    @FXML
    private void updateAbonnement() {
        Abonnement selectedAbonnement = abonnementsTable.getSelectionModel().getSelectedItem();
        if (selectedAbonnement != null) {
            selectedAbonnement.setDate(dateField.getText());
            selectedAbonnement.setDurée(dureeField.getText());
            selectedAbonnement.setTarif(Double.parseDouble(tarifField.getText()));
            selectedAbonnement.getUser().setId(Long.parseLong(userIdField.getText()));
            abonnementService.updateAbonnement(selectedAbonnement);
            clearFields();
            loadAbonnements();
        }
    }

    @FXML
    private void deleteAbonnement() {
        Abonnement selectedAbonnement = abonnementsTable.getSelectionModel().getSelectedItem();
        if (selectedAbonnement != null) {
            abonnementService.deleteAbonnement(selectedAbonnement);
            clearFields();
            loadAbonnements();
        }
    }

    @FXML
    private void loadAbonnements() {
        abonnementsTable.getItems().clear();
        List<Abonnement> abonnements = abonnementService.getAllAbonnements();
        abonnementsTable.getItems().addAll(abonnements);
    }

    private void clearFields() {
        dateField.clear();
        dureeField.clear();
        tarifField.clear();
        userIdField.clear();
    }

    @FXML
    private void returnToMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("menu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) dateField.getScene().getWindow(); // Use any field or element in the current scene
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
