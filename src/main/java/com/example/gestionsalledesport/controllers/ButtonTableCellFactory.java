package com.example.gestionsalledesport.controllers;

import com.example.gestionsalledesport.models.GymFacility;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class ButtonTableCellFactory implements Callback<TableColumn<GymFacility, Integer>, TableCell<GymFacility, Integer>> {
    @Override
    public TableCell<GymFacility, Integer> call(TableColumn<GymFacility, Integer> param) {
        return new ButtonTableCell();
    }

    private class ButtonTableCell extends TableCell<GymFacility, Integer> {
        private final Button rentButton;
        private GymFacility currentFacility; // Store the GymFacility object

        public ButtonTableCell() {
            this.rentButton = new Button("Rent");
            this.rentButton.setOnAction(event -> {
                if (currentFacility != null) {
                    CalendarScene.display(currentFacility); // Pass the GymFacility object to CalendarScene
                }
            });
        }

        @Override
        protected void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                setGraphic(null);
            } else {
                currentFacility = (GymFacility) getTableRow().getItem();
                setGraphic(rentButton);
            }
        }
    }
}
