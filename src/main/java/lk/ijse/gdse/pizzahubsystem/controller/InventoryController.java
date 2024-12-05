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

    public TextField txtlast_updated;

    public TextField quantityId;

    public Button btnUpdated;

    public Label priceFeild;

    public TextField txtproductId;

    public TableColumn colLast_Updated;

    public TableColumn colinventoryId;

    public TableView inventorytable;
    public Button btnSave;

    @FXML
    private TableView<InventoryTM> inventoryTable;

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
    private TextField txtProductId;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtLastUpdated;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    private static final Pattern QUANTITY_PATTERN = Pattern.compile("^[1-9][0-9]*$");

    private final InventoryModel inventoryModel = new InventoryModel();

    @FXML
    public void initialize() throws SQLException {
        colinventoryId.setCellValueFactory(new PropertyValueFactory<>("inventory_id"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colLast_Updated.setCellValueFactory(new PropertyValueFactory<>("last_updated"));

        loadTableData();
        generateNextInventoryId();
    }

    @FXML
    public void tblInventory(SortEvent<javafx.scene.control.TableView> event) {
        System.out.println("Sort event triggered: " + event);
    }


    private void loadTableData() throws SQLException {
        ObservableList<InventoryDTO> addInventory = FXCollections.observableArrayList();

        List<InventoryDTO> list = inventoryModel.getAllInventoryItems();

        for (InventoryDTO inventoryDTO : list) {
            System.out.println("qwertyu");
            addInventory.add(inventoryDTO);
        }
        inventorytable.setItems(addInventory);
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
                        txtProductId.getText(),
                        txtSupplierId.getText(),
                        Integer.parseInt(txtQuantity.getText()),
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
        if (validateInputs()) {
            try {
                InventoryDTO inventoryDTO = new InventoryDTO(
                        txtInventoryId.getText(),
                        txtproductId.getText(),
                        txtSupplierId.getText(),
                        Integer.parseInt(txtQuantity.getText()),
                        txtLastUpdated.getText()
                );

                if (txtInventoryId.getText().isEmpty() || inventoryModel.getInventoryById(txtInventoryId.getText()) == null) {
                    if (inventoryModel.saveInventory(inventoryDTO)) {
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Inventory item added successfully.");
                    } else {
                        showAlert(Alert.AlertType.WARNING, "Failure", "Failed to add inventory item.");
                    }
                } else {
                    if (inventoryModel.updateInventory(inventoryDTO)) {
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Inventory item updated successfully.");
                    } else {
                        showAlert(Alert.AlertType.WARNING, "Failure", "Failed to update inventory item.");
                    }
                }
                refreshPage();

            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
            }
        }
    }

    private void refreshPage() throws SQLException {
        loadTableData();
        generateNextInventoryId();
        txtproductId.clear();
        txtSupplierId.clear();
        txtQuantity.clear();
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
