package lk.ijse.gdse.pizzahubsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.pizzahubsystem.dto.DeliveryDTO;
import lk.ijse.gdse.pizzahubsystem.dto.tm.DeliveryTM;
import model.DeliveryModel;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class DeliveryController {

    public TextField deliveryorderIdfeild;
    public TextField deliverydateField;
    public TextField deliveryaddressField;
    public TextField deliveryStatusField1;
    public ImageView imageview;
    public TextField EmployeeIdField1;
    @FXML
    private TextField deliveryIdField, deliveryAddressField, deliveryDateField, deliveryOrderIdField, deliveryStatusField, employeeIdField;
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

    public DeliveryController() {
    }

    @FXML
    public void initialize() throws SQLException {
        if (tblDelivery != null) {
            loadDeliveryData();
        } else {
            System.out.println("TableView is not initialized!");
        }
    }

    private boolean validateInputs() {
        try {
            if (deliveryIdField.getText().isEmpty() || deliveryAddressField.getText().isEmpty() ||
                    deliveryDateField.getText().isEmpty() || deliveryOrderIdField.getText().isEmpty() ||
                    deliveryStatusField.getText().isEmpty()) {
                showAlert("Validation Error", "All fields are required.", Alert.AlertType.ERROR);
                return false;
            }

            Integer.parseInt(deliveryIdField.getText());
            Integer.parseInt(deliveryOrderIdField.getText());

            LocalDate.parse(deliveryDateField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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
            } catch (Exception e) {
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
        deliveryAddressField.clear();
        deliveryDateField.clear();
        deliveryOrderIdField.clear();
        deliveryStatusField.clear();
        employeeIdField.clear();
    }

//    @FXML
//    void btnSaveOnAction(ActionEvent event) {
//        if (validateInputs()) {
//            DeliveryTM delivery = new DeliveryTM(
//              (deliveryIdField.getText()),
//                    deliveryAddressField.getText(),
//                    LocalDate.parse(deliveryDateField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
//                    deliveryStatusField.getText(),
//                    Integer.parseInt(deliveryOrderIdField.getText())
//            );
//
//            try {
//                boolean saved = deliveryService.saveDelivery(delivery);
//
//                if (saved) {
//                    showAlert("Success", "Delivery saved successfully.", Alert.AlertType.INFORMATION);
//                    resetFields();
//                    loadDeliveryData();
//                } else {
//                    showAlert("Error", "Failed to save delivery.", Alert.AlertType.ERROR);
//                }
//            } catch (Exception e) {
//                showAlert("Error", "An error occurred while saving delivery: " + e.getMessage(), Alert.AlertType.ERROR);
//            }
//        }
//    }

//    @FXML
//    void btnUpdateOnAction(ActionEvent event) {
//        if (validateInputs()) {
//            DeliveryTM delivery = new DeliveryTM(
//                    (deliveryIdField.getText()),
//                    deliveryAddressField.getText(),
//                    LocalDate.parse(deliveryDateField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
//                    deliveryStatusField.getText(),
//                    (deliveryOrderIdField.getText())
//            );
//
//            try {
//                boolean updated = deliveryService.updateDelivery(delivery);
//
//                if (updated) {
//                    showAlert("Success", "Delivery updated successfully.", Alert.AlertType.INFORMATION);
//                    resetFields();
//                    loadDeliveryData();
//                } else {
//                    showAlert("Error", "Failed to update delivery.", Alert.AlertType.ERROR);
//                }
//            } catch (Exception e) {
//                showAlert("Error", "An error occurred while updating delivery: " + e.getMessage(), Alert.AlertType.ERROR);
//            }
//        }
//    }

    @FXML
    void onClickTable(MouseEvent event) {
        DeliveryTM selectedDelivery = (DeliveryTM) tblDelivery.getSelectionModel().getSelectedItem();

        if (selectedDelivery != null) {
            deliveryIdField.setText((selectedDelivery.getDelivery_id()));
            deliveryOrderIdField.setText((selectedDelivery.getOrder_id()));
            deliveryAddressField.setText(selectedDelivery.getDelivery_Address());
            deliveryDateField.setText(selectedDelivery.getDelivery_Date().toString());
            deliveryStatusField.setText(selectedDelivery.getDelivery_status());
            employeeIdField.setText((selectedDelivery.getEmployee_id()));
        }
    }

//    private void loadDeliveryData() {
//        tblDelivery.getItems().clear();
//        try {
//            tblDelivery.getItems().addAll(deliveryService.getAllDeliveries());
//        } catch (Exception e) {
//            showAlert("Error", "An error occurred while loading deliveries: " + e.getMessage(), Alert.AlertType.ERROR);
//        }
//    }

//    private static class DeliveryService {
//        public boolean deleteDelivery(int deliveryId) {
//            return true;
//        }
//
//        public boolean saveDelivery(DeliveryTM delivery) {
//            return true;
//        }
//
//        public boolean updateDelivery(DeliveryTM delivery) {
//            return true;
//        }
//
//        public Collection<? extends ItemTM> getAllDeliveries() {
//            return new java.util.ArrayList<>();
//        }
//    }

    private void loadView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/gdse/pizzahubsystem/view/DeliveryView.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load Delivery view: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

    }
}
