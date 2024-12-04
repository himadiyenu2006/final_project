package lk.ijse.gdse.pizzahubsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import lk.ijse.gdse.pizzahubsystem.dto.tm.SupplierTM;

public class SupplierController {

    @FXML
    private Label SupplierContactName;

    @FXML
    private Label SupplierContactNumber;

    @FXML
    private Label SupplierName;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnERefresh;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<SupplierTM, String> colAdress;

    @FXML
    private TableColumn<SupplierTM, String> colContactName;

    @FXML
    private TableColumn<SupplierTM, ?> colContactNumber;

    @FXML
    private TableColumn<SupplierTM, ?> colName;

    @FXML
    private TableColumn<SupplierTM, ?> colSupplierID;

    @FXML
    private Label lblUserId;

    @FXML
    private Label sup_addId;

    @FXML
    private Label supplierId;

    @FXML
    private TableView<?> tblsupplier;

    @FXML
    private TextField txtContactName;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtaddress;

    @FXML
    private TextField txtcontactNumber;

    @FXML
    void addOnAction(ActionEvent event) {

    }

    @FXML
    void deleteOnAction(ActionEvent event) {

    }

    @FXML
    void refreshOnAction(ActionEvent event) {

    }

    @FXML
    void saveOnAction(ActionEvent event) {

    }

    @FXML
    void updateOnAction(ActionEvent event) {
    }

}






//package lk.ijse.gdse.pizzahubsystem.controller;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import lk.ijse.gdse.pizzahubsystem.db.DBConnection;
//import lk.ijse.gdse.pizzahubsystem.dto.tm.SupplierTM;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class SupplierController {
//
//    private final Connection connection;
//    @FXML
//    private TableView<SupplierTM> tblSupplier;
//    @FXML
//    private TableColumn<SupplierTM, String> colSupplierID;
//    @FXML
//    private TableColumn<SupplierTM, String> colName;
//    @FXML
//    private TableColumn<SupplierTM, String> colContactNumberr;
//    @FXML
//    private TableColumn<SupplierTM, String> colEmail;
//    @FXML
//    private TableColumn<SupplierTM, String> colAdress;
//
//
//    @FXML
//    private Button btnAdd;
//
//    @FXML
//    private Button btnDelete;
//
//    @FXML
//    private Button btnERefresh;
//
//    @FXML
//    private Button btnSave;
//
//    @FXML
//    private Button btnUpdate;
//
//    @FXML
//    private Label lblUserId;
//
//    @FXML
//    private TextField txtEmail;
//
//    @FXML
//    private TextField txtName;
//
//    @FXML
//    private Label SupplierContactNumber;
//
//    @FXML
//    private Label SupplierEmail;
//
//    @FXML
//    private Label SupplierName;
//
//    @FXML
//    private Label SupplierContactName;
//
//    @FXML
//    private TableColumn<?, ?> colContactName;
//
//    @FXML
//    private TableColumn<?, ?> colContactNumber;
//
//    @FXML
//    private TextField txtContactName;
//
//
//    @FXML
//    private TextField txtcontactNumber;
//
//
//    public SupplierController() throws SQLException {
//        this.connection = DBConnection.getInstance().getConnection();
//    }
//
//    /**
//     * @return
//     * @throws SQLException
//     */
//    private Connection getConnection() throws SQLException {
//        String url = "jdbc:mysql://localhost:3306/pizzahubsystemdb";
//        String username = "root";
//        String password = "1234";
//
//        return DriverManager.getConnection(url, username, password);
//    }
//
//    private boolean validateInputs() {
//        if (txtName.getText().isEmpty()) {
//            showAlert("Name field cannot be empty.");
//            return false;
//        }
//
//        if (txtEmail.getText().isEmpty()) {
//            showAlert("Email field cannot be empty.");
//            return false;
//        } else if (!txtEmail.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
//            showAlert("Please enter a valid email address.");
//            return false;
//        }
//
//
//
//    private void showAlert(String message) {
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        alert.setTitle("Validation Error");
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//
//
//    private void startTransaction() {
//        try {
//            connection.setAutoCommit(false);
//        } catch (SQLException e) {
//            showAlert("Error", "Failed to start transaction: " + e.getMessage());
//        }
//    }
//
//    private void showAlert(String error, String s) {
//    }
//
//    private void commitTransaction() {
//        try {
//            connection.commit();
//            connection.setAutoCommit(true);
//        } catch (SQLException e) {
//            showAlert("Error", "Failed to commit transaction: " + e.getMessage());
//        }
//    }
//
//    private void rollbackTransaction() {
//        try {
//            connection.rollback();
//            connection.setAutoCommit(true);
//        } catch (SQLException e) {
//            showAlert("Error", "Failed to rollback transaction: " + e.getMessage());
//        }
//    }
//
//    @FXML
//    void btnSaveOnAction() {
//        if (validateInputs()) {
//            startTransaction();
//
//            try {
//                String sql = "INSERT INTO supplier (name, email, password, role) VALUES (?, ?, ?, ?)";
//                try (PreparedStatement statement = connection.prepareStatement(sql)) {
//                    statement.setString(1, txtName.getText());
//                    statement.setString(2, txtEmail.getText());
//                    statement.setString(3, txtPassword.getText());
//                    statement.setString(4, txtRole.getText());
//
//                    statement.executeUpdate();
//                    commitTransaction();
//                    showAlert("Success", "Supplier saved successfully.");
//                }
//            } catch (SQLException e) {
//                rollbackTransaction();
//                showAlert("Error", "An error occurred while saving supplier details. Transaction rolled back.");
//            }
//        }
//    }
//
//    @FXML
//    void btnUpdateOnAction() {
//        if (validateInputs()) {
//            startTransaction();
//
//            try {
//                String sql = "UPDATE supplier SET  supplier_name = ?, contact_name = ?, contact_number = ? , address = ?, WHERE supplier_id = ?";
//                try (PreparedStatement statement = connection.prepareStatement(sql)) {
//                    statement.setString(1, supplierId.getText());
//                    statement.setString(1, txtName.getText());
//                    statement.setString(2, txtEmail.getText());
//                    statement.setString(3, txtPassword.getText());
//                    statement.setString(4, txtRole.getText());
//                    statement.setInt(5, Integer.parseInt(lblUserId.getText()));
//
//                    statement.executeUpdate();
//                    commitTransaction();
//                    showAlert("Success", "Supplier updated successfully.");
//                }
//            } catch (SQLException e) {
//                rollbackTransaction();
//                showAlert("Error", "An error occurred while updating supplier details. Transaction rolled back.");
//            }
//        }
//    }
//
//    @FXML
//    void btnDeleteOnAction() {
//        startTransaction();
//
//        try {
//            String sql = "DELETE FROM supplier WHERE supplier_id = ?";
//            try (PreparedStatement statement = connection.prepareStatement(sql)) {
//                statement.setInt(1, Integer.parseInt(lblUserId.getText()));
//
//                statement.executeUpdate();
//                commitTransaction();
//                showAlert("Success", "Supplier deleted successfully.");
//            }
//        } catch (SQLException e) {
//            rollbackTransaction();
//            showAlert("Error", "An error occurred while deleting supplier. Transaction rolled back.");
//        }
//    }
//
//    @FXML
//    void addOnAction(ActionEvent event) {
//
//    }
//
//    @FXML
//    void refreshOnAction(ActionEvent event) {
//
//    }
//
//
//}
//
//    private void showAlert(String s) {
//        return;
//    }
//    }
//
//
