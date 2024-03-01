package com.example.gestionsalledesport.controllers;

import com.example.gestionsalledesport.models.Coach;
import com.example.gestionsalledesport.models.Cours;
import com.example.gestionsalledesport.services.CoachService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CoachController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField specialtyField;

    @FXML
    private TextField bioField;

    @FXML
    private TextField availabilityField;

    @FXML
    private TextField coursesField;

    @FXML
    private Button createButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<Coach> coachesTable;

    private final CoachService coachService = new CoachService();

    @FXML
    public void initialize() {
        initializeTable();

        createButton.setOnAction(event -> createEntity());
        updateButton.setOnAction(event -> updateEntity());
        deleteButton.setOnAction(event -> deleteEntity());
    }

    private void initializeTable() {
        TableColumn<Coach, String> firstNameColumn = new TableColumn<>("First Name");
        TableColumn<Coach, String> lastNameColumn = new TableColumn<>("Last Name");
        TableColumn<Coach, String> emailColumn = new TableColumn<>("Email");
        TableColumn<Coach, String> specialtyColumn = new TableColumn<>("Specialty");
        TableColumn<Coach, String> bioColumn = new TableColumn<>("Bio");
        TableColumn<Coach, String> availabilityColumn = new TableColumn<>("Availability");
        TableColumn<Coach, List<Cours>> coursesColumn = new TableColumn<>("Courses");

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        specialtyColumn.setCellValueFactory(new PropertyValueFactory<>("specialty"));
        bioColumn.setCellValueFactory(new PropertyValueFactory<>("bio"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));
        coursesColumn.setCellValueFactory(new PropertyValueFactory<>("courses"));

        coachesTable.getColumns().addAll(firstNameColumn, lastNameColumn, emailColumn, specialtyColumn, bioColumn, availabilityColumn, coursesColumn);

    }



    @FXML
    private void createEntity() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String specialty = specialtyField.getText();
        String bio = bioField.getText();
        String availability = availabilityField.getText();
        List<Cours> courses = parseCourses(coursesField.getText());

        Coach coach = new Coach(null, firstName, lastName, email, specialty, bio, availability, courses);
        coachService.insertEntity(coach);
    }

    @FXML
    private void updateEntity() {
        Coach selectedCoach = coachesTable.getSelectionModel().getSelectedItem();
        if (selectedCoach != null) {
            selectedCoach.setFirstName(firstNameField.getText());
            selectedCoach.setLastName(lastNameField.getText());
            selectedCoach.setEmail(emailField.getText());
            selectedCoach.setSpecialty(specialtyField.getText());
            selectedCoach.setBio(bioField.getText());
            selectedCoach.setAvailability(availabilityField.getText());
            selectedCoach.setCourses(parseCourses(coursesField.getText()));

            coachService.updateEntity(selectedCoach);
        }
    }

    @FXML
    private void deleteEntity() {
        Coach selectedCoach = coachesTable.getSelectionModel().getSelectedItem();
        if (selectedCoach != null) {
            coachService.deleteEntity(selectedCoach.getId());

        }
    }

    private List<Cours> parseCourses(String coursesString) {
        List<Cours> coursesList = new ArrayList<>();
        String[] coursesArray = coursesString.split(",");
        for (String courseName : coursesArray) {
            Cours course = new Cours(courseName.trim());
            coursesList.add(course);
        }
        return coursesList;
    }





    @FXML
    void handleShowCoachCourses(ActionEvent event) {
        try {
            // Charger l'interface FXML pour ajouter un tournoi existant
            FXMLLoader loader = new FXMLLoader(getClass().getResource("coachCourseView.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la racine chargée
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir du bouton et changer sa racine
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les erreurs d'entrée/sortie si le chargement de l'interface échoue
        }
    }

}
