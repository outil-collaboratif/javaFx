// CalendarScene.java
package com.example.gestionsalledesport.controllers;

import com.example.gestionsalledesport.models.GymFacility;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class CalendarScene {

    public static void display(GymFacility facility) {
        Stage window = new Stage();
        window.setTitle("Select Date");

        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());

        VBox layout = new VBox(10);
        layout.getChildren().add(datePicker);

        Scene scene = new Scene(layout, 300, 200);
        window.setScene(scene);

        datePicker.setOnAction(event -> {
            LocalDate selectedDate = datePicker.getValue();
            if (!isAvailable(facility, selectedDate)) {
                displayUnavailableMessage();
            } else {
                // Handle renting logic for the facility on selectedDate
                System.out.println("Renting facility " + facility.getName() + " on " + selectedDate);
                window.close(); // Close the calendar scene
            }
        });

        window.show();
    }

    private static boolean isAvailable(GymFacility facility, LocalDate selectedDate) {
        // Implement logic to check if the facility is available on the selected date
        // You may query your database or check your data model for existing bookings
        // For simplicity, I assume the facility is always available in this example
        return true;
    }

    private static void displayUnavailableMessage() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Facility Unavailable");
        alert.setHeaderText(null);
        alert.setContentText("The facility is already rented on the selected date.");
        alert.showAndWait();
    }
}
