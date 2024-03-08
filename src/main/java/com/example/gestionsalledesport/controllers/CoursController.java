package com.example.gestionsalledesport.controllers;

import com.example.gestionsalledesport.models.Cours;
import com.example.gestionsalledesport.models.User;
import com.example.gestionsalledesport.services.CoursService;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

public class CoursController {

    @FXML
    private TextField libelleField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField dureeField;

    @FXML
    private TextField dateField;

    @FXML
    private TextField coachIdField;

    @FXML
    private TableView<Cours> coursTable;

    private final CoursService coursService = new CoursService();

    @FXML
    private void createCours() {
        String libelle = libelleField.getText();
        String description = descriptionField.getText();
        String duree = dureeField.getText();
        String date = dateField.getText();
        long coachId = Long.parseLong(coachIdField.getText());

        User coach = new User(coachId); // Assuming you have a User class
        Cours cours = new Cours(null, libelle, description, duree, date, coach, null); // Assuming participants are null initially
        coursService.insertCours(cours);
        clearFields();
        loadCours();
    }

    @FXML
    private void updateCours() {
        Cours selectedCours = coursTable.getSelectionModel().getSelectedItem();
        if (selectedCours != null) {
            selectedCours.setLibelle(libelleField.getText());
            selectedCours.setDescription(descriptionField.getText());
            selectedCours.setDuree(dureeField.getText());
            selectedCours.setDate(dateField.getText());
            long coachId = Long.parseLong(coachIdField.getText());
            selectedCours.getCoach().setId(coachId);
            coursService.updateCours(selectedCours);
            clearFields();
            loadCours();
        }
    }

    @FXML
    private void deleteCours() {
        Cours selectedCours = coursTable.getSelectionModel().getSelectedItem();
        if (selectedCours != null) {
            coursService.deleteCours(selectedCours);
            clearFields();
            loadCours();
        }
    }

    @FXML
    private void loadCours() {
        coursTable.getItems().clear();
        List<Cours> coursList = coursService.getAllCours();
        coursTable.getItems().addAll(coursList);
    }

    private void clearFields() {
        libelleField.clear();
        descriptionField.clear();
        dureeField.clear();
        dateField.clear();
        coachIdField.clear();
    }
}
