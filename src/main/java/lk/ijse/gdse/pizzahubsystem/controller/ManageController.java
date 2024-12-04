package lk.ijse.gdse.pizzahubsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import lk.ijse.gdse.pizzahubsystem.dto.ManageDTO;
import model.ManageModel;

import java.sql.SQLException;

public class ManageController {

    public ImageView imageManageId;

    public Button cancelable;

    public TableColumn supplierContactNameCol;

    public TableColumn supplierNameCol;

    public TableColumn quantityCol;

    public TableColumn orderIdCol;

    public TextField supplierContactNameField;

    public TextField supplierNameField;

    public TextField quantityField;

    public TextField orderIdField;

    public TextField supplierIdField;

    public TextField inventoryIdField;

    public TextField manageIdField;
    @FXML
    private TableView<ManageDTO> tblManage;

    @FXML
    private TextField txtManageId, txtInventoryId, txtSupplierId, txtOrderId, txtQuantity, txtSupplierName, txtSupplierContact;

    @FXML
    private Button btnSave, btnCancel;

    private final ManageModel manageModel = new ManageModel();

    public ManageController() throws SQLException {
    }

    @FXML
    private void initialize() {
        try {
            loadTableData();
        } catch (SQLException e) {
            showError("Error loading data: " + e.getMessage());
        }
    }

    private void loadTableData() throws SQLException {
        ObservableList<ManageDTO> manageList = FXCollections.observableArrayList(manageModel.getAllManageRecords());
        tblManage.setItems(manageList);
    }

    @FXML
    private void btnSaveOnAction(ActionEvent event) {
        try {
            ManageDTO manageDTO = new ManageDTO(
                     txtManageId.getText(),
                     txtInventoryId.getText(),
                     txtSupplierId.getText(),
                     txtOrderId.getText(),
                     Integer.parseInt(txtQuantity.getText()),
                     txtSupplierName.getText(),
                     txtSupplierContact.getText()
            );
            if (manageModel.saveManage(manageDTO)) {
                showInfo("Saved successfully!");
                loadTableData();
            } else {
                showError("Save failed!");
            }
        } catch (Exception e) {
            showError("Error: " + e.getMessage());
        }
    }

    @FXML
    private void btnCancelOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtManageId.clear();
        txtInventoryId.clear();
        txtSupplierId.clear();
        txtOrderId.clear();
        txtQuantity.clear();
        txtSupplierName.clear();
        txtSupplierContact.clear();
    }

    private void showError(String message) {
        new Alert(Alert.AlertType.ERROR, message).show();
    }

    private void showInfo(String message) {
        new Alert(Alert.AlertType.INFORMATION, message).show();
    }
}
