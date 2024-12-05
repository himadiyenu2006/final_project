package lk.ijse.gdse.pizzahubsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.pizzahubsystem.db.DBConnection;
import lk.ijse.gdse.pizzahubsystem.dto.DeliveryDTO;
import lk.ijse.gdse.pizzahubsystem.dto.tm.DeliveryTM;
import model.DeliveryModel;
import  javafx.scene.control.TableView;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class DeliveryController {

    public TextField EmployeeIdField1;
    public ImageView imageview;
    public TextField deliveryStatusField1;
    public TextField deliveryaddressField;
    public TextField deliverydateField;
    public TextField deliveryorderIdfeild;
    public TableView tbldelivery;
    @FXML
    private TextField deliveryIdField;
    @FXML
    private TextField deliveryAddressField;
    @FXML
    private TextField deliveryDateField;
    @FXML
    private TextField deliveryOrderIdField;
    @FXML
    private TextField deliveryStatusField;
    @FXML
    private TextField employeeIdField;
    @FXML
    private ImageView imageView;
    @FXML
    private Label titleId;
    @FXML
    private Button btnDelete, btnReset, btnSave, btnUpdate;
    @FXML
    private TableView<DeliveryTM> tblDelivery;
    @FXML
    private TableColumn<DeliveryTM, Integer> colDeliveryId, colOrderId;
    @FXML
    private TableColumn<DeliveryTM, String> colDeliveryAddress, colDeliveryStatus;
    @FXML
    private TableColumn<DeliveryTM, LocalDate> colDeliveryDate;

    private final DeliveryService deliveryService = new DeliveryService();

    @FXML
    public void initialize() throws SQLException {
        if (tblDelivery != null) {
            loadDeliveryData();
        } else {
            showAlert("Error", "TableView is not initialized!", Alert.AlertType.ERROR);
        }
    }


    private boolean validateInputs() {
        try {
            if (deliveryIdField.getText().isEmpty() || deliveryaddressField.getText().isEmpty() ||
                    deliverydateField.getText().isEmpty() || deliveryorderIdfeild.getText().isEmpty() ||
                    deliveryStatusField1.getText().isEmpty()) {
                showAlert("Validation Error", "All fields are required.", Alert.AlertType.ERROR);
                return false;
            }

            Integer.parseInt(deliveryIdField.getText());
            Integer.parseInt(deliveryorderIdfeild.getText());

            LocalDate.parse(deliverydateField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;

        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Delivery ID and Order ID must be numbers.", Alert.AlertType.ERROR);
            return false;
        } catch (DateTimeParseException e) {
            showAlert("Validation Error", "Delivery Date must be in 'yyyy-MM-dd' format.", Alert.AlertType.ERROR);
            return false;
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (validateInputs()) {
            int deliveryId = Integer.parseInt(deliveryIdField.getText());
            try {
                boolean deleted = deliveryService.deleteDelivery(deliveryId);

                if (deleted) {
                    showAlert("Success", "Delivery deleted successfully.", Alert.AlertType.INFORMATION);
                    resetFields();
                    loadDeliveryData();
                } else {
                    showAlert("Error", "Failed to delete delivery.", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                showAlert("Error", "An error occurred while deleting delivery: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private void loadDeliveryData() throws SQLException {
        ArrayList<DeliveryDTO> deliveryq = DeliveryModel.getAllDelivery();
        ObservableList<DeliveryTM> deliveryTMS = FXCollections.observableArrayList();
        for (DeliveryDTO delivery : deliveryq) {
            deliveryTMS.add(new DeliveryTM(
                    delivery.getDelivery_id(),
                    delivery.getOrder_id(),
                    delivery.getDelivery_address(),
                    delivery.getDelivery_date(),
                    delivery.getDelivery_status(),
                    delivery.getEmployee_id()
            ));
        }
        tblDelivery.setItems(deliveryTMS);
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        resetFields();
    }

    private void resetFields() {
        deliveryIdField.clear();
        deliveryaddressField.clear();
        deliverydateField.clear();
        deliveryorderIdfeild.clear();
        deliveryStatusField1.clear();
        EmployeeIdField1.clear();
    }

    @FXML
    void onClickTable(MouseEvent event) {
        DeliveryTM selectedDelivery = (DeliveryTM) tblDelivery.getSelectionModel().getSelectedItem();

        if (selectedDelivery != null) {
            deliveryIdField.setText(String.valueOf(selectedDelivery.getDelivery_id()));
            deliveryorderIdfeild.setText(String.valueOf(selectedDelivery.getOrder_id()));
            deliveryaddressField.setText(selectedDelivery.getDelivery_Address());
            deliverydateField.setText(selectedDelivery.getDelivery_Date().toString());
            deliveryStatusField1.setText(selectedDelivery.getDelivery_status());
            EmployeeIdField1.setText(selectedDelivery.getEmployee_id());
        }
    }

    private String generateNewDeliveryId() {
        try {
            ResultSet rst = DBConnection.getConnection().createStatement().executeQuery("SELECT delivery_id FROM delivery ORDER BY delivery_id DESC LIMIT 1");

            if (rst.next()) {
                String lastId = rst.getString(1);
                String substring = lastId.substring(1);
                int lastIdInt = Integer.parseInt(substring);
                int newIdIndex = lastIdInt + 1;
                return String.format("D%03d", newIdIndex);
            }
        } catch (SQLException e) {
            showAlert("Error", "An error occurred while generating new delivery ID: " + e.getMessage(), Alert.AlertType.ERROR);
        }
        return "D001";
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if (validateInputs()) {
            String newDeliveryId = generateNewDeliveryId();

            try {
                LocalDate deliveryDateLocal = LocalDate.parse(deliveryDateField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                java.sql.Date deliveryDate = java.sql.Date.valueOf(deliveryDateLocal);

                DeliveryDTO deliveryDTO = new DeliveryDTO(
                        newDeliveryId,
                        deliveryorderIdfeild.getText(),
                        deliveryaddressField.getText(),
                        deliveryDate,
                        deliveryStatusField.getText(),
                        EmployeeIdField1.getText()
                );

                boolean saved = deliveryService.saveDelivery(deliveryDTO);

                if (saved) {
                    showAlert("Success", "Delivery saved successfully.", Alert.AlertType.INFORMATION);
                    resetFields();
                    loadDeliveryData();
                } else {
                    showAlert("Error", "Failed to save delivery.", Alert.AlertType.ERROR);
                }
            } catch (DateTimeParseException e) {
                showAlert("Validation Error", "Invalid date format. Please use 'yyyy-MM-dd'.", Alert.AlertType.ERROR);
            } catch (SQLException e) {
                showAlert("Error", "An error occurred while saving delivery: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (validateInputs()) {
            try {
                LocalDate deliveryDateLocal = LocalDate.parse(deliverydateField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                java.sql.Date deliveryDate = java.sql.Date.valueOf(deliveryDateLocal);

                DeliveryDTO deliveryDTO = new DeliveryDTO(
                        deliveryIdField.getText(),
                        deliveryorderIdfeild.getText(),
                        deliveryaddressField.getText(),
                        deliveryDate,
                        deliveryStatusField1.getText(),
                        EmployeeIdField1.getText()
                );

                boolean updated = deliveryService.updateDelivery(deliveryDTO);

                if (updated) {
                    showAlert("Success", "Delivery updated successfully.", Alert.AlertType.INFORMATION);
                    resetFields();
                    loadDeliveryData();
                } else {
                    showAlert("Error", "Failed to update delivery.", Alert.AlertType.ERROR);
                }
            } catch (DateTimeParseException e) {
                showAlert("Validation Error", "Invalid date format. Please use 'yyyy-MM-dd'.", Alert.AlertType.ERROR);
            } catch (SQLException e) {
                showAlert("Error", "An error occurred while updating delivery: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }


    private static class DeliveryService {
        public boolean saveDelivery(DeliveryDTO deliveryDTO) throws SQLException {
            return DBConnection.getConnection().prepareStatement(
                    "INSERT INTO delivery(delivery_id, order_id, delivery_address, delivery_date, delivery_status, employee_id) VALUES (?,?,?,?,?,?)"
            ).executeUpdate() > 0;
        }

        public boolean updateDelivery(DeliveryDTO deliveryDTO) throws SQLException {
            return DBConnection.getConnection().prepareStatement(
                    "UPDATE delivery SET order_id=?, delivery_address=?, delivery_date=?, delivery_status=?, employee_id=? WHERE delivery_id=?"
            ).executeUpdate() > 0;
        }

        public boolean deleteDelivery(int delivery_id) throws SQLException {
            return DBConnection.getConnection().prepareStatement(
                    "DELETE FROM delivery WHERE delivery_id=?"
            ).executeUpdate() > 0;
        }
    }
}
