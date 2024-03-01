package com.example.gestionsalledesport.controllers;

import java.io.IOException;

import com.example.gestionsalledesport.HelloApplication;
import com.example.gestionsalledesport.models.User;
import com.example.gestionsalledesport.services.UserService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddUserController {
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField birthdayField;
    @FXML
    private TableView<User> userTableView;
    @FXML
    private TextField dateField;

    private final UserService userService = new UserService();

    public void initialize() {
        loadUsers();

         // Add a listener to the TableView selection model to handle selection changes
        userTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                if (newValue != null) {
                    // Populate input fields with selected user's information
                    nomField.setText(newValue.getNom());
                    prenomField.setText(newValue.getPrenom());
                    emailField.setText(newValue.getEmail());
                    birthdayField.setText(newValue.getBirthday());
                }
            }
        });
    }

    public void addUser() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String birthday = birthdayField.getText();

        User user = new User(null, nom, prenom, email, birthday);
        userService.insertUser(user);
        loadUsers(); // Reload the users after adding a new one
        clearFields(); // Clear input fields after adding user
    }

    public void updateUser() {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            selectedUser.setNom(nomField.getText());
            selectedUser.setPrenom(prenomField.getText());
            selectedUser.setEmail(emailField.getText());
            selectedUser.setBirthday(birthdayField.getText());

            userService.updateUser(selectedUser);
            loadUsers(); // Reload the users after updating
            clearFields(); // Clear input fields after updating user
        }
    }

    public void deleteUser() {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            userService.deleteUser(selectedUser.getId());
            loadUsers(); // Reload the users after deleting
            clearFields(); // Clear input fields after deleting user
        }
    }

    private void loadUsers() {
        ObservableList<User> users = FXCollections.observableArrayList(userService.getAllUsers());
        userTableView.setItems(users);
    }

    private void clearFields() {
        nomField.clear();
        prenomField.clear();
        emailField.clear();
        birthdayField.clear();
    }

@FXML
private void returnToMenu(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("menu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
