package com.example.gestionsalledesport;
import com.example.gestionsalledesport.services.GymFacilitiesTestRunner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("gymFacilities.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 240);
        stage.setTitle("RentGymFacility");
        stage.setScene(scene);
        stage.show();
        runGymFacilityTests();
    }
    private void runGymFacilityTests() {
        // Instantiate and run the test runner class for Gym Facility tests
        GymFacilitiesTestRunner testRunner = new GymFacilitiesTestRunner();
        testRunner.runTests();
    }

    public static void main(String[] args) {
        launch();
    }

}