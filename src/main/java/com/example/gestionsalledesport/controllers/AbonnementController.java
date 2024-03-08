package com.example.gestionsalledesport.controllers;

import com.example.gestionsalledesport.models.Abonnement;
import com.example.gestionsalledesport.models.AbonnementType;
import com.example.gestionsalledesport.models.User;
import com.example.gestionsalledesport.services.AbonnementService;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

public class AbonnementController {

    @FXML
    private Button createButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField dureeField;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TextField tarifField;

    @FXML
    private TextField userIdField;

    @FXML
    private TableView<Abonnement> abonnementsTable;

    private BooleanBinding isInputValid;

    private final AbonnementService abonnementService = new AbonnementService();

    @FXML
    private void createAbonnement() {
        LocalDate date = dateField.getValue();
        String duree = dureeField.getText();
        double tarif = Double.parseDouble(tarifField.getText());
        long userId = Long.parseLong(userIdField.getText());
        AbonnementType type = AbonnementType.valueOf(typeComboBox.getValue());
        Abonnement abonnement = new Abonnement(null, date, duree, tarif, new User(userId), type);
        abonnementService.insertAbonnement(abonnement);
        clearFields();
        loadAbonnements();
    }

    @FXML
    private void updateAbonnement() {
        Abonnement selectedAbonnement = abonnementsTable.getSelectionModel().getSelectedItem();
        if (selectedAbonnement != null) {
            LocalDate date = dateField.getValue();
            selectedAbonnement.setDate(date);
            selectedAbonnement.setDurée(dureeField.getText());
            selectedAbonnement.setTarif(Double.parseDouble(tarifField.getText()));
            selectedAbonnement.getUser().setId(Long.parseLong(userIdField.getText()));
            selectedAbonnement.setType(AbonnementType.valueOf(typeComboBox.getValue()));
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
        dateField.setValue(null); // Clear DatePicker value
        dureeField.clear();
        tarifField.clear();
        userIdField.clear();
    }

    @FXML
    public void initialize() {
        isInputValid = Bindings.createBooleanBinding(
                () -> isValidDate(dateField.getValue()) &&
                        isValidText(dureeField.getText()) &&
                        isValidDouble(tarifField.getText()) &&
                        isValidLong(userIdField.getText()),
                dateField.valueProperty(),
                dureeField.textProperty(),
                tarifField.textProperty(),
                userIdField.textProperty());

        createButton.disableProperty().bind(isInputValid.not());
        updateButton.disableProperty().bind(isInputValid.not());
    }

    private boolean isValidDate(LocalDate date) {
        return date != null;
    }

    private boolean isValidText(String text) {
        return !text.isEmpty();
    }

    private boolean isValidDouble(String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidLong(String text) {
        try {
            Long.parseLong(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
