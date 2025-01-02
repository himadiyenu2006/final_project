package lk.ijse.gdse.pizzahubsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.pizzahubsystem.dto.SupplierDTO;
import lk.ijse.gdse.pizzahubsystem.dto.tm.SupplierTM;
import model.SupplierModel;

import java.sql.SQLException;
import java.util.List;

public class SupplierController {

    public TableColumn<?,?> colAdress;

    public TableColumn<?,?> colContactNumber;

    public TableColumn<?,?> colContactName;

    public TableColumn <?,?>colName;

    public TableColumn<?,?> colSupplierID;

    public TableView<SupplierTM> tblsupplier;

    public Button btnERefresh;

    public Button btnAdd;

    public TextField txtaddress;

    public Label sup_addId;

    public TextField txtcontactNumber;

    public Label SupplierContactNumber;

    public Label SupplierContactName;

    public TextField txtName;

    public Label SupplierName;

    public Label lblUserId;

    public Label supplierId;
    public TextField txtsupplierid;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private TextField txtContactName;


    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    private SupplierModel supplierModel = new SupplierModel();

    @FXML
    public void initialize() {
        try {
            setCellValue();
            loadSupplierTableData();
        } catch (SQLException e) {
            showError("Error", "Failed to load supplier data.");
        }
    }

    private void setCellValue() {
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        colAdress.setCellValueFactory(new PropertyValueFactory<>("adress"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contact_number"));
        colContactName.setCellValueFactory(new PropertyValueFactory<>("contact_name"));
        colName.setCellValueFactory(new PropertyValueFactory<>("supplier_name"));

    }

    private void loadSupplierTableData() throws SQLException {
       // ObservableList<SupplierDTO> suppliers = FXCollections.observableArrayList(supplierModel.getAllSuppliers());

        ObservableList<SupplierTM> supplierTMs = FXCollections.observableArrayList();
        List<SupplierDTO> sList = supplierModel.getAllSuppliers();
        for (SupplierDTO supplierDTO : sList) {
            SupplierTM supplierTM = new SupplierTM(
                    supplierDTO.getSupplier_id(),
                    supplierDTO.getSupplier_name(),
                    supplierDTO.getContact_name(),
                    supplierDTO.getContact_number(),
                    supplierDTO.getAddress()
            );
            supplierTMs.add(supplierTM);
        }

        tblsupplier.setItems(supplierTMs);
    }

    @FXML
    public void saveSupplier() {
        try {
            String supplierId = supplierModel.getNextSupplierId();
            String supplierName = txtName.getText();
            String contactName = txtContactName.getText();
            String contactNumber = txtcontactNumber.getText();
            String address = txtaddress.getText();

            SupplierDTO supplierDTO = new SupplierDTO(supplierId, supplierName, contactName, contactNumber, address);

            boolean isSaved = supplierModel.saveSupplier(supplierDTO);

            if (isSaved) {
                showInfo("Success", "Supplier saved successfully!");
                loadSupplierTableData();
            } else {
                showError("Error", "Failed to save supplier.");
            }
        } catch (SQLException e) {
            showError("Error", "Error saving supplier: " + e.getMessage());
        }
    }


    @FXML
    public void updateSupplier() {
        try {
            SupplierTM selectedSupplier = tblsupplier.getSelectionModel().getSelectedItem();

            if (selectedSupplier == null) {
                showError("Error", "Please select a supplier to update.");
                return;
            }

            String supplierName = txtName.getText();
            String contactName = txtContactName.getText();
            String contactNumber = txtcontactNumber.getText();
            String address = txtaddress.getText();


            selectedSupplier.setSupplier_name(supplierName);
            selectedSupplier.setContact_name(contactName);
            selectedSupplier.setContact_number(contactNumber);
            selectedSupplier.setAddress(address);


            boolean isUpdated = supplierModel.updateSupplier(selectedSupplier);

            if (isUpdated) {
                showInfo("Success", "Supplier updated successfully!");
                loadSupplierTableData();
            } else {
                showError("Error", "Failed to update supplier.");
            }
        } catch (SQLException e) {
            showError("Error", "Error updating supplier: " + e.getMessage());
        }
    }


    @FXML
    public void deleteSupplier() {
        try {
            SupplierTM selectedSupplier = tblsupplier.getSelectionModel().getSelectedItem();

            if (selectedSupplier == null) {
                showError("Error", "Please select a supplier to delete.");
                return;
            }

            String supplierId = selectedSupplier.getSupplier_id();


            boolean isDeleted = supplierModel.deleteSupplier(supplierId);

            if (isDeleted) {
                showInfo("Success", "Supplier deleted successfully!");
                loadSupplierTableData();
            } else {
                showError("Error", "Failed to delete supplier.");
            }
        } catch (SQLException e) {
            showError("Error", "Error deleting supplier: " + e.getMessage());
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

    @FXML
    public void onTableSelect() {
        SupplierTM selectedSupplier = tblsupplier.getSelectionModel().getSelectedItem();
        if (selectedSupplier != null) {
            txtSupplierId.setText(selectedSupplier.getSupplier_id());
            txtName.setText(selectedSupplier.getSupplier_name());
            txtContactName.setText(selectedSupplier.getContact_name());
            txtcontactNumber.setText(selectedSupplier.getContact_number());
            txtaddress.setText(selectedSupplier.getAddress());
        }
    }

    @FXML
    public void saveOnAction(ActionEvent actionEvent) {
        try {
            String supplierId = txtsupplierid.getText() ;
            String supplierName = txtName.getText();
            String contactName = txtContactName.getText();
            String contactNumber = txtcontactNumber.getText();
            String address = txtaddress.getText();
            System.out.println(supplierId);
            SupplierDTO supplierDTO = new SupplierDTO(supplierId, supplierName, contactName, contactNumber, address);

            boolean isSaved = supplierModel.saveSupplier(supplierDTO);

            if (isSaved) {
                showInfo("Success", "Supplier saved successfully!");
                loadSupplierTableData();
                clearForm();
            } else {
                showError("Error", "Failed to save supplier.");
            }
        } catch (SQLException e) {
            showError("Error", "Error saving supplier: " + e.getMessage());
        }
    }

    private void clearForm() {

    }

    @FXML
    public void updateOnAction(ActionEvent actionEvent) {
        try {
            SupplierTM selectedSupplier = tblsupplier.getSelectionModel().getSelectedItem();

            if (selectedSupplier == null) {
                showError("Error", "Please select a supplier to update.");
                return;
            }

            String supplierName = txtName.getText();
            String contactName = txtContactName.getText();
            String contactNumber = txtcontactNumber.getText();
            String address = txtaddress.getText();

            selectedSupplier.setSupplier_name(supplierName);
            selectedSupplier.setContact_name(contactName);
            selectedSupplier.setContact_number(contactNumber);
            selectedSupplier.setAddress(address);

            boolean isUpdated = supplierModel.updateSupplier(selectedSupplier);

            if (isUpdated) {
                showInfo("Success", "Supplier updated successfully!");
                loadSupplierTableData();
                clearForm();
            } else {
                showError("Error", "Failed to update supplier.");
            }
        } catch (SQLException e) {
            showError("Error", "Error updating supplier: " + e.getMessage());
        }
    }

    @FXML
    public void deleteOnAction(ActionEvent actionEvent) {
        try {
            SupplierTM selectedSupplier = tblsupplier.getSelectionModel().getSelectedItem();

            if (selectedSupplier == null) {
                showError("Error", "Please select a supplier to delete.");
                return;
            }

            String supplierId = selectedSupplier.getSupplier_id();

            boolean isDeleted = supplierModel.deleteSupplier(supplierId);

            if (isDeleted) {
                showInfo("Success", "Supplier deleted successfully!");
                loadSupplierTableData();
                clearForm();
            } else {
                showError("Error", "Failed to delete supplier.");
            }
        } catch (SQLException e) {
            showError("Error", "Error deleting supplier: " + e.getMessage());
        }
    }

    @FXML
    public void addOnAction(ActionEvent actionEvent) throws SQLException {
        clearForm();
        String newSupplierId = supplierModel.getNextSupplierId();
        txtSupplierId.setText(newSupplierId);
    }

    @FXML
    public void refreshOnAction(ActionEvent actionEvent) {
        try {
            loadSupplierTableData();
        } catch (SQLException e) {
            showError("Error", "Error refreshing supplier data: " + e.getMessage());
        }
    }

}
