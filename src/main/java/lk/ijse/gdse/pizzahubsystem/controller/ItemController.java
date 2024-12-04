package lk.ijse.gdse.pizzahubsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.pizzahubsystem.dto.tm.ItemTM;
import net.sf.jasperreports.components.items.Item;

public class ItemController {

    public TableView iterable;
    @FXML
    private Button btnDeleteItem;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSaveItem;

    @FXML
    private Button btnUpdateItem;

    @FXML
    private ImageView imageItem;

    @FXML
    private TableColumn<ItemTM, Integer> itemIdColumn;

    @FXML
    private TableColumn<ItemTM, String> itemNameColumn;

    @FXML
    private TableColumn<ItemTM, Double> itemPriceColumn;

    @FXML
    private TableColumn<ItemTM, String> itemQuantityColumn;

    @FXML
    private Label lblItemId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQuantity;

    public TableView<Item> itemTable;

    private boolean validateInputs() {
        String name = txtName.getText().trim();
        String priceText = txtPrice.getText().trim();
        String quantityText = txtQuantity.getText().trim();

        if (name.isEmpty()) {
            showAlert("Validation Error", "Item name cannot be empty.", Alert.AlertType.ERROR);
            return false;
        }
        if (!priceText.matches("\\d+(\\.\\d{1,2})?")) {
            showAlert("Validation Error", "Price must be a valid number (e.g., 10 or 10.99).", Alert.AlertType.ERROR);
            return false;
        }
        if (!quantityText.matches("\\d+")) {
            showAlert("Validation Error", "Quantity must be a whole number.", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void btnSaveItemOnAction(ActionEvent event) {
        if (validateInputs()) {
            String name = txtName.getText();
            double price = Double.parseDouble(txtPrice.getText());
            String quantity = txtQuantity.getText();

            ItemTM newItem = new ItemTM(0, name, price, quantity);
            itemTable.getItems().add((Item) newItem);
            showAlert("Success", "Item saved successfully.", Alert.AlertType.INFORMATION);
            resetForm();
        }
    }

    @FXML
    void btnUpdateItemOnAction(ActionEvent event) {
        ItemTM selectedItem = (ItemTM) itemTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null && validateInputs()) {
            selectedItem.setItemName(txtName.getText());
            selectedItem.setItemPrice(Double.parseDouble(txtPrice.getText()));
            selectedItem.setItemQuantity(txtQuantity.getText());

            itemTable.refresh();
            showAlert("Success", "Item updated successfully.", Alert.AlertType.INFORMATION);
            resetForm();
        } else {
            showAlert("Selection Error", "Please select an item to update.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void btnDeleteItemOnAction(ActionEvent event) {
        ItemTM selectedItem = (ItemTM) itemTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            itemTable.getItems().remove(selectedItem);
            showAlert("Success", "Item deleted successfully.", Alert.AlertType.INFORMATION);
            resetForm();
        } else {
            showAlert("Selection Error", "Please select an item to delete.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        resetForm();
    }
    private void resetForm() {
        lblItemId.setText("ID");
        txtName.clear();
        txtPrice.clear();
        txtQuantity.clear();
        itemTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    void itemTableOnMouseClicked(MouseEvent event) {
        ItemTM selectedItem = (ItemTM) itemTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblItemId.setText(String.valueOf(selectedItem.getItemId()));
            txtName.setText(selectedItem.getItemName());
            txtPrice.setText(String.valueOf(selectedItem.getItemPrice()));
            txtQuantity.setText(selectedItem.getItemQuantity());
        }
    }
}
