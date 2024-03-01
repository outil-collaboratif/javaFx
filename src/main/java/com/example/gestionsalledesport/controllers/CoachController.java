package com.example.gestionsalledesport.controllers;

import com.example.gestionsalledesport.models.Coach;
import com.example.gestionsalledesport.models.Cours;
import com.example.gestionsalledesport.services.CoachService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class CoachController {

    @FXML
    private TextField specialtyField;

    @FXML
    private TextField bioField;

    @FXML
    private TextField availabilityField;

    @FXML
    private TextField courseIdField;

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

        coachesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldEntity, newEntity) -> {
            if (newEntity != null) {
                List<String> entityDates = coachService.getEntityDates(newEntity.getId());
                showEntityDatesDialog(newEntity.getId(), newEntity.getSpecialty(), entityDates);
            }
        });
    }

    private void initializeTable() {
        TableColumn<Coach, String> specialtyColumn = new TableColumn<>("Specialty");
        TableColumn<Coach, String> bioColumn = new TableColumn<>("Bio");
        TableColumn<Coach, String> availabilityColumn = new TableColumn<>("Availability");
        TableColumn<Coach, Long> courseIdColumn = new TableColumn<>("Course ID");

        specialtyColumn.setCellValueFactory(new PropertyValueFactory<>("specialty"));
        bioColumn.setCellValueFactory(new PropertyValueFactory<>("bio"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));
        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("courseId"));

        coachesTable.getColumns().addAll(specialtyColumn, bioColumn, availabilityColumn, courseIdColumn);
    }



    private void createEntity() {
        String specialty = specialtyField.getText();
        String bio = bioField.getText();
        String availability = availabilityField.getText();
        long courseId = Long.parseLong(courseIdField.getText());

        Coach coach = new Coach(specialty, bio, availability, courseId);
        coachService.insertEntity(coach);

    }

    private void updateEntity() {
        Coach selectedCoach = coachesTable.getSelectionModel().getSelectedItem();
        if (selectedCoach != null) {
            selectedCoach.setSpecialty(specialtyField.getText());
            selectedCoach.setBio(bioField.getText());
            selectedCoach.setAvailability(availabilityField.getText());
            List<Cours> courses = parseCourses(courseIdField.getText());
            selectedCoach.setCourses(courses);
            coachService.updateEntity(selectedCoach);

        }
    }
    private List<Cours> parseCourses(String coursesString) {
        List<Cours> coursesList = new ArrayList<>();
        // Supposons que les noms des cours sont séparés par des virgules dans la chaîne
        String[] coursesArray = coursesString.split(",");
        for (String courseName : coursesArray) {
            // Créez un objet Cours pour chaque nom de cours et ajoutez-le à la liste
            Cours course = new Cours(courseName.trim()); // Supprimez les espaces autour du nom du cours
            coursesList.add(course);
        }
        return coursesList;
    }
    private void deleteEntity() {
        Coach selectedCoach = coachesTable.getSelectionModel().getSelectedItem();
        if (selectedCoach != null) {
            coachService.deleteEntity(selectedCoach.getId());

        }
    }

    private void showEntityDatesDialog(long entityId, String specialty, List<String> entityDates) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dates des cours pour le coach " + specialty);
        alert.setHeaderText("Dates des cours associées au coach " + specialty + " (ID: " + entityId + ")");
        alert.setContentText(String.join("\n", entityDates));
        alert.showAndWait();
    }
}
