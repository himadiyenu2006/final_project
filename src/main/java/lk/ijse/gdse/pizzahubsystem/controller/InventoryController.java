
package lk.ijse.gdse.pizzahubsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.pizzahubsystem.dto.InventoryDTO;
import lk.ijse.gdse.pizzahubsystem.dto.tm.InventoryTM;
import model.InventoryModel;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class InventoryController {
    private final InventoryModel inventoryModel = new InventoryModel();


    public TextField txtlast_updated;

    public TextField quantityId;

    public Button btnUpdated;

    public Label priceFeild;

    public TextField txtproductId;

    public TableColumn colLast_Updated;

    public TableColumn colinventoryId;

    public Button btnSave;

    @FXML
    private TableView<InventoryTM> inventorytable;

    @FXML
    private TableColumn<InventoryTM, String> colInventoryId;

    @FXML
    private TableColumn<InventoryTM, String> colProductId;

    @FXML
    private TableColumn<InventoryTM, String> colSupplierId;

    @FXML
    private TableColumn<InventoryTM, Integer> colQuantity;

    @FXML
    private TableColumn<InventoryTM, String> colLastUpdated;

    @FXML
    private TextField txtInventoryId;


    @FXML
    private TextField txtSupplierId;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    private static final Pattern QUANTITY_PATTERN = Pattern.compile("^[1-9][0-9]*$");

    @FXML
    public void initialize() throws SQLException {
        setCellValue();
        loadTableData();
    }
    private void setCellValue() {
        colinventoryId.setCellValueFactory(new PropertyValueFactory<>("inventory_id"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colLast_Updated.setCellValueFactory(new PropertyValueFactory<>("last_updated"));

    }
//    @FXML
//    public void initialize() throws SQLException {
//        colinventoryId.setCellValueFactory(new PropertyValueFactory<>("inventory_id"));
//        colProductId.setCellValueFactory(new PropertyValueFactory<>("product_id"));
//        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
//        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//        colLast_Updated.setCellValueFactory(new PropertyValueFactory<>("last_updated"));
//
//        loadTableData();
//        generateNextInventoryId();
//    }

    @FXML
    public void tblInventory(SortEvent<TableView> event) {
        System.out.println("Sort event triggered: " + event);
    }


    private void loadTableData() throws SQLException {
        ObservableList<InventoryTM> inventoryTMS = FXCollections.observableArrayList();
        List<InventoryDTO> sList = inventoryModel.getAllInventoryItems();
        for (InventoryDTO inventoryDTO : sList) {
            InventoryTM inventoryTM = new InventoryTM(
                    inventoryDTO.getInventory_id(),
                    inventoryDTO.getProduct_id(),
                    inventoryDTO.getSupplier_id(),
                    inventoryDTO.getQuantity(),
                    inventoryDTO.getLast_updated()
            );
            System.out.println(inventoryTM);
            inventoryTMS.add(inventoryTM);
        }

        inventorytable.setItems(inventoryTMS);
    }

    private void generateNextInventoryId() {
        try {
            String nextId = inventoryModel.getNextInventoryId();
            txtInventoryId.setText(nextId);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error Generating ID", e.getMessage());
        }
    }

    @FXML
    public void handleAdd(ActionEvent actionEvent) {
        if (validateInputs()) {
            try {
                InventoryDTO inventoryDTO = new InventoryDTO(
                        txtInventoryId.getText(),
                        txtproductId.getText(),
                        txtSupplierId.getText(),
                        Integer.parseInt(quantityId.getText()),
                        txtlast_updated.getText()
                );

                if (inventoryModel.saveInventory(inventoryDTO)) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Inventory item added successfully.");
                    refreshPage();
                } else {
                    showAlert(Alert.AlertType.WARNING, "Failure", "Failed to add inventory item.");
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
            }
        }
    }

    @FXML
    public void handleUpdate(ActionEvent actionEvent) {
        if (validateInputs()) {
            try {
                InventoryDTO inventoryDTO = new InventoryDTO(
                        txtInventoryId.getText(),
                        txtproductId.getText(),
                        txtSupplierId.getText(),
                        Integer.parseInt(quantityId.getText()),
                        txtlast_updated.getText()
                );

                if (inventoryModel.updateInventory(inventoryDTO)) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Inventory item updated successfully.");
                    refreshPage();
                } else {
                    showAlert(Alert.AlertType.WARNING, "Failure", "Failed to update inventory item.");
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
            }
        }
    }

    @FXML
    public void handleDelete(ActionEvent actionEvent) {
        try {
            String inventoryId = txtInventoryId.getText();
            if (inventoryModel.deleteInventory(Integer.parseInt(inventoryId))) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Inventory item deleted successfully.");
                refreshPage();
            } else {
                showAlert(Alert.AlertType.WARNING, "Failure", "Failed to delete inventory item.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    private boolean validateInputs() {
        if (!QUANTITY_PATTERN.matcher(quantityId.getText()).matches()) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Quantity must be a positive integer.");
            return false;
        }
        if (txtproductId.getText().isEmpty() || txtSupplierId.getText().isEmpty() || txtlast_updated.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "All fields are required.");
            return false;
        }
        return true;
    }
    private void handleSave(ActionEvent actionEvent) {
        try {
            String inventoryId = inventoryModel.getNextInventoryId();
            String productId = txtproductId.getText();
            String supplierId = txtSupplierId.getText();
            String quantity = quantityId.getText();
            String last_updated = txtlast_updated.getText();

            InventoryDTO inventoryDTO = new InventoryDTO(inventoryId, productId, supplierId, Integer.parseInt(quantity), last_updated);
            System.out.println(inventoryDTO);
            boolean isSaved = inventoryModel.saveInventory(inventoryDTO);

            if (isSaved) {
                showInfo("Success", "Inventory saved successfully!");
                loadTableData();
            } else {
                showError("Error", "Failed to save supplier.");
            }
        } catch (SQLException e) {
            showError("Error", "Error saving supplier: " + e.getMessage());
        }
    }


    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void refreshPage() throws SQLException {
        loadTableData();
        generateNextInventoryId();
        txtproductId.clear();
        txtSupplierId.clear();
        quantityId.clear();
        txtlast_updated.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        handleDelete(actionEvent);
    }

    public void UpdateOnAction(ActionEvent actionEvent) {
        handleUpdate(actionEvent);
    }

    public void AddOnAction(ActionEvent actionEvent) {
        handleAdd(actionEvent);
    }

    public void SaveOnAction(ActionEvent actionEvent) {
        handleSave(actionEvent);
    }


}

