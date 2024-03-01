package com.example.gestionsalledesport.controllers;

import com.example.gestionsalledesport.models.GymFacility;
import com.example.gestionsalledesport.services.GymFacilitiesService;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static javafx.beans.binding.Bindings.createObjectBinding;

public class GymFacilitiesController {
    @FXML
    private TableView<GymFacility> facilityTable;

    private GymFacilitiesService facilitiesService;

    public GymFacilitiesController() {
        this.facilitiesService = new GymFacilitiesService();
    }

    @FXML
    private void initialize() throws SQLException {
        // Load facilities into the table
        List<GymFacility> allFacilities = facilitiesService.getAllFacilities();
        facilityTable.getItems().addAll(allFacilities);

// Add custom cell factory to the date picker to disable unavailable dates
        TableColumn<GymFacility, LocalDate> datePickerColumn = new TableColumn<>("Rent Date");
        datePickerColumn.setCellValueFactory(param -> {
            DatePicker datePicker = new DatePicker();
            datePicker.setOnAction(event -> {
                LocalDate selectedDate = datePicker.getValue();
                if (selectedDate != null) {
                    try {
                        boolean available = facilitiesService.isFacilityAvailableForDate(param.getValue().getId(), java.sql.Date.valueOf(selectedDate));
                        if (!available) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText("Facility Not Available");
                            alert.setContentText("Facility is not available on " + selectedDate + ". Please select another date.");
                            alert.showAndWait();
                        }
                    } catch (SQLException e) {
                        // Handle SQLException
                        e.printStackTrace();
                    }
                }
            });
            return datePicker.valueProperty();
        });
        facilityTable.getColumns().add(datePickerColumn);


    }


    private void setupDatePicker(GymFacility facility) {
        TableColumn<GymFacility, LocalDate> datePickerColumn = new TableColumn<>("Rent Date");
        datePickerColumn.setCellValueFactory(param -> {
            DatePicker datePicker = new DatePicker();
            datePicker.setOnAction(event -> {
                LocalDate selectedDate = datePicker.getValue();
                if (selectedDate != null) {
                    try {
                        boolean available = facilitiesService.isFacilityAvailableForDate(facility.getId(), java.sql.Date.valueOf(selectedDate));
                        if (!available) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText("Facility Not Available");
                            alert.setContentText("Facility is not available on " + selectedDate + ". Please select another date.");
                            alert.showAndWait();
                        }
                    } catch (SQLException e) {
                        // Handle SQLException
                        e.printStackTrace();
                    }
                }
            });
            return datePicker.valueProperty();
        });
        facilityTable.getColumns().add(datePickerColumn);
    }



    private Callback<DatePicker, DateCell> getDayCellFactory(GymFacility facility) {
        return datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    try {
                        boolean available = facilitiesService.isFacilityAvailableForDate(facility.getId(), java.sql.Date.valueOf(item));
                        setDisable(!available);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    @FXML
    private void rentFacility() {
        GymFacility selectedFacility = facilityTable.getSelectionModel().getSelectedItem();
        if (selectedFacility != null) {
            // Handle renting logic here
            System.out.println("Renting " + selectedFacility.getName());
        }
    }
}