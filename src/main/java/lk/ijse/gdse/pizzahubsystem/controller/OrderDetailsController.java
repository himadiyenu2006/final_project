package lk.ijse.gdse.pizzahubsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import lk.ijse.gdse.pizzahubsystem.dto.OrderDetailsDTO;
import lk.ijse.gdse.pizzahubsystem.dto.tm.OrderDetailsTM;
import model.OrderDetailsModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsController {

    public TableView tblOrderdetails;

    public TableColumn ColOrderDetailsId;

    public Button btnadd_order_details;

    public Button btnupdate_order_details;

    public Button btndelete_order_details;

    public ImageView imageid;

    public TextField txtOrderDetailsId;
    @FXML
    private TableView<OrderDetailsTM> tblOrderDetails;

    @FXML
    private TableColumn<OrderDetailsTM, String> colOrderDetailId;

    @FXML
    private TableColumn<OrderDetailsTM, String> colOrderId;

    @FXML
    private TableColumn<OrderDetailsTM, String> colProductId;

    @FXML
    private TableColumn<OrderDetailsTM, Integer> colQuantity;

    @FXML
    private TableColumn<OrderDetailsTM, Double> colPrice;

    @FXML
    private TextField txtOrderDetailId;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtProductId;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtPrice;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    private final OrderDetailsModel orderDetailsModel = new OrderDetailsModel();

    @FXML
    public void initialize() {
        ColOrderDetailsId.setCellValueFactory(new PropertyValueFactory<>("order_details_id"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        try {
            loadOrderDetailsTableData();
        } catch (SQLException e) {
            showError("Error", "Failed to load order details.");
        }
    }

    private void loadOrderDetailsTableData() throws SQLException {
        ArrayList<OrderDetailsDTO> orderDetailsList = orderDetailsModel.getAllOrderDetails();
        ObservableList<OrderDetailsTM> orderDetailsTMList = FXCollections.observableArrayList();

        for (OrderDetailsDTO orderDetailsDTO : orderDetailsList) {
            orderDetailsTMList.add(new OrderDetailsTM(
                    orderDetailsDTO.getOrderDetail_id(),
                    orderDetailsDTO.getOrder_id(),
                    orderDetailsDTO.getProduct_id(),
                    orderDetailsDTO.getQuantity(),
                    orderDetailsDTO.getPrice()
            ));
        }

        tblOrderDetails.setItems(orderDetailsTMList);
    }

    @FXML
    public void saveOrderDetails(ActionEvent event) {
        try {
            String orderDetailId = txtOrderDetailsId.getText();
            String orderId = txtOrderId.getText();
            String productId = txtProductId.getText();
            int quantity = Integer.parseInt(txtQuantity.getText());
            double price = Double.parseDouble(txtPrice.getText());

            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(orderDetailId, orderId, productId, quantity, price);

            boolean isSaved = orderDetailsModel.saveOrderDetails(orderDetailsDTO);

            if (isSaved) {
                showInfo("Success", "Order details saved successfully!");
                loadOrderDetailsTableData();
                clearForm();
            } else {
                showError("Error", "Failed to save order details.");
            }
        } catch (SQLException | NumberFormatException e) {
            showError("Error", "Error saving order details: " + e.getMessage());
        }
    }

    @FXML
    public void updateOrderDetails(ActionEvent event) {
        try {
            OrderDetailsTM selectedOrder = tblOrderDetails.getSelectionModel().getSelectedItem();

            if (selectedOrder == null) {
                showError("Error", "Please select an order to update.");
                return;
            }

            String orderDetailId = txtOrderDetailsId.getText();
            String orderId = txtOrderId.getText();
            String productId = txtProductId.getText();
            int quantity = Integer.parseInt(txtQuantity.getText());
            double price = Double.parseDouble(txtPrice.getText());

            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(orderDetailId, orderId, productId, quantity, price);

            boolean isUpdated = orderDetailsModel.updateOrderDetails(orderDetailsDTO);

            if (isUpdated) {
                showInfo("Success", "Order details updated successfully!");
                loadOrderDetailsTableData();
                clearForm();
            } else {
                showError("Error", "Failed to update order details.");
            }
        } catch (SQLException | NumberFormatException e) {
            showError("Error", "Error updating order details: " + e.getMessage());
        }
    }

    @FXML
    public void deleteOrderDetails(ActionEvent event) {
        try {
            OrderDetailsTM selectedOrder = tblOrderDetails.getSelectionModel().getSelectedItem();

            if (selectedOrder == null) {
                showError("Error", "Please select an order to delete.");
                return;
            }

            String orderDetailId = selectedOrder.getOrder_details_id();

            boolean isDeleted = orderDetailsModel.deleteOrderDetails(orderDetailId);

            if (isDeleted) {
                showInfo("Success", "Order details deleted successfully!");
                loadOrderDetailsTableData();
                clearForm();
            } else {
                showError("Error", "Failed to delete order details.");
            }
        } catch (SQLException e) {
            showError("Error", "Error deleting order details: " + e.getMessage());
        }
    }

    private void clearForm() {
        txtOrderDetailsId.clear();
        txtOrderId.clear();
        txtProductId.clear();
        txtQuantity.clear();
        txtPrice.clear();
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
        OrderDetailsTM selectedOrder = tblOrderDetails.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            txtOrderDetailsId.setText(selectedOrder.getOrder_details_id());
            txtOrderId.setText(selectedOrder.getOrder_id());
            txtProductId.setText(selectedOrder.getProduct_id());
            txtQuantity.setText(String.valueOf(selectedOrder.getQuantity()));
            txtPrice.setText(String.valueOf(selectedOrder.getPrice()));
        }
    }

    @FXML
    public void handleAddOrderDetail(ActionEvent actionEvent) {
        if (!validateOrderDetails()) {
            try {
                String orderDetailId = txtOrderDetailsId.getText();
                String orderId = txtOrderId.getText();
                String productId = txtProductId.getText();
                int quantity = Integer.parseInt(txtQuantity.getText());
                double price = Double.parseDouble(txtPrice.getText());

                OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(orderDetailId, orderId, productId, quantity, price);

                boolean isSaved = orderDetailsModel.saveOrderDetails(orderDetailsDTO);

                if (isSaved) {
                    showInfo("Success", "Order detail added successfully!");
                    loadOrderDetailsTableData();
                    clearForm();
                } else {
                    showError("Error", "Failed to add order detail.");
                }
            } catch (SQLException | NumberFormatException e) {
                showError("Error", "Error adding order detail: " + e.getMessage());
            }
        }
    }


    @FXML
    public void handleUpdateOrderDetail(ActionEvent actionEvent) {
        if (!validateOrderDetails()) {
            try {
                OrderDetailsTM selectedOrder = tblOrderDetails.getSelectionModel().getSelectedItem();

                if (selectedOrder == null) {
                    showError("Error", "Please select an order detail to update.");
                    return;
                }

                String orderDetailId = txtOrderDetailsId.getText();// selectedOrder.getOrder_details_id();
                String orderId = txtOrderId.getText();
                String productId = txtProductId.getText();
                int quantity = Integer.parseInt(txtQuantity.getText());
                double price = Double.parseDouble(txtPrice.getText());

                OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(orderDetailId, orderId, productId, quantity, price);

                boolean isUpdated = orderDetailsModel.updateOrderDetails(orderDetailsDTO);

                if (isUpdated) {
                    showInfo("Success", "Order detail updated successfully!");
                    loadOrderDetailsTableData();
                    clearForm();
                } else {
                    showError("Error", "Failed to update order detail.");
                }
            } catch (SQLException | NumberFormatException e) {
                showError("Error", "Error updating order detail: " + e.getMessage());
            }
        }
    }

    private boolean validateOrderDetails() {
        String orderDetailId = txtOrderDetailsId.getText().trim();
        String orderId = txtOrderId.getText().trim();
        String productId = txtProductId.getText().trim();
        String quantityText = txtQuantity.getText().trim();
        String priceText = txtPrice.getText().trim();

        if (orderDetailId.isEmpty() || orderId.isEmpty() || productId.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) {
            showError("Validation Error", "All fields must be filled out.");
            return false;
        }

        try {
            int quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                showError("Validation Error", "Quantity must be a positive integer.");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Validation Error", "Quantity must be a valid number.");
            return false;
        }

        try {
            double price = Double.parseDouble(priceText);
            if (price <= 0) {
                showError("Validation Error", "Price must be a positive number.");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Validation Error", "Price must be a valid number.");
            return false;
        }

        return true;
    }



    @FXML
    public void handleDeleteOrderDetail(ActionEvent actionEvent) {
        try {
            OrderDetailsTM selectedOrder = tblOrderDetails.getSelectionModel().getSelectedItem();

            if (selectedOrder == null) {
                showError("Error", "Please select an order detail to delete.");
                return;
            }

            String orderDetailId = selectedOrder.getOrder_details_id();

            boolean isDeleted = orderDetailsModel.deleteOrderDetails(orderDetailId);

            if (isDeleted) {
                showInfo("Success", "Order detail deleted successfully!");
                loadOrderDetailsTableData();
                clearForm();
            } else {
                showError("Error", "Failed to delete order detail.");
            }
        } catch (SQLException e) {
            showError("Error", "Error deleting order detail: " + e.getMessage());
        }
    }

}












































//package lk.ijse.gdse.pizzahubsystem.controller;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.TableView;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.image.ImageView;
//import lk.ijse.gdse.pizzahubsystem.dto.tm.OrderDetailsTM;
//import model.OrderDetailsModel;
//
//import java.net.URL;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.ResourceBundle;
//
//public class OrderDetailsController {
//
//    public TableView tblOrderdetails;
//    @FXML
//    private TableColumn<OrderDetailsTM,String> ColOrderDetailsId;
//
//    @FXML
//    private TableColumn<OrderDetailsTM , String> colOrderId;
//
//    @FXML
//    private TableColumn<OrderDetailsTM , String> colPrice;
//
//    @FXML
//    private TableColumn<OrderDetailsTM , String > colProductId;
//
//    @FXML
//    private TableColumn<OrderDetailsTM , String > colQuantity;
//
//    @FXML
//    private ImageView imageid;
//
//    @FXML
//    private TextField txtOrderDetailsId;
//
//    @FXML
//    private TextField txtOrderId;
//
//    @FXML
//    private TextField txtPrice;
//
//    @FXML
//    private TextField txtProductId;
//
//    @FXML
//    private TextField txtQuantity;
//    @FXML
//    private Button btnadd_order_details;
//
//    @FXML
//    private Button btndelete_order_details;
//
//    @FXML
//    private Button btnupdate_order_details;
//
//    @FXML
//    private TableView<OrderDetailsTM> tblOrderDetails;
//
//    private Connection connection;
//
//    private boolean validateOrderDetails() {
//        if (txtOrderDetailsId.getText().isEmpty()) {
//            showAlert("Order Details ID is required.");
//            return false;
//        }
//        if (txtOrderId.getText().isEmpty()) {
//            showAlert("Order ID is required.");
//            return false;
//        }
//        if (txtPrice.getText().isEmpty()) {
//            showAlert("Price is required.");
//            return false;
//        }
//        try {
//            double price = Double.parseDouble(txtPrice.getText());
//            if (price <= 0) {
//                showAlert("Price must be a positive number.");
//                return false;
//            }
//        } catch (NumberFormatException e) {
//            showAlert("Price must be a valid number.");
//            return false;
//        }
//        if (txtProductId.getText().isEmpty()) {
//            showAlert("Product ID is required.");
//            return false;
//        }
//        if (txtQuantity.getText().isEmpty()) {
//            showAlert("Quantity is required.");
//            return false;
//        }
//        try {
//            int quantity = Integer.parseInt(txtQuantity.getText());
//            if (quantity <= 0) {
//                showAlert("Quantity must be a positive integer.");
//                return false;
//            }
//        } catch (NumberFormatException e) {
//            showAlert("Quantity must be a valid integer.");
//            return false;
//        }
//        return true;
//    }
//
//    private void showAlert(String message) {
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        alert.setTitle("Validation Error");
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//    public void initialize() throws SQLException {
//        initialize(null, null);
//        loadTableData();
//
//    }
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        ColOrderDetailsId.setCellValueFactory(new PropertyValueFactory<>("order_details_id"));
//        colOrderId.setCellValueFactory(new PropertyValueFactory<>("order_id"));
//        colProductId.setCellValueFactory(new PropertyValueFactory<>("product_id"));
//        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
///*
//        try {
//            refreshPage();
//        } catch (Exception e) {
//            e.printStackTrace();
//            new Alert(Alert.AlertType.ERROR, "Fail to load customer id").show();
//        }*/
//    }
//
//    private void refreshPage() throws SQLException {
//        loadNextOrderDetailsId();
//        loadTableData();
//
//
//        btnadd_order_details.setDisable(false);
//
//        btndelete_order_details.setDisable(false);
//        btnupdate_order_details.setDisable(false);
//
//        txtOrderDetailsId .setText("");
//        txtOrderId.setText("");
//        txtProductId.setText("");
//        txtQuantity.setText("");
//        txtPrice.setText("");
//    }
//
//    private void loadNextOrderDetailsId() {
//
//    }
//
//    private void loadTableData() throws SQLException {
//        ObservableList<OrderDetailsTM> addorderdetails = FXCollections.observableArrayList();
//
//        List<OrderDetailsTM> list = OrderDetailsModel.getAllOderdetails();
//
//        for (OrderDetailsTM orderDetailsTM : list) {
//            System.out.println("qwertyu");
//            addorderdetails.add(orderDetailsTM);
//        }
//        tblOrderDetails.setItems(addorderdetails);
//    }
//
//
//    OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
//
//
//    @FXML
//    void handleAddOrderDetail(ActionEvent event) {
//        if (validateOrderDetails()) {
//            try {
//                connection.setAutoCommit(false);
//
//                String orderDetailQuery = "INSERT INTO order_details (order_details_id, order_id, product_id, quantity, price) VALUES (?, ?, ?, ?, ?)";
//                try (PreparedStatement stmt = connection.prepareStatement(orderDetailQuery)) {
//                    stmt.setInt(1, Integer.parseInt(txtOrderDetailsId.getText()));
//                    stmt.setInt(2, Integer.parseInt(txtOrderId.getText()));
//                    stmt.setInt(3, Integer.parseInt(txtProductId.getText()));
//                    stmt.setInt(4, Integer.parseInt(txtQuantity.getText()));
//                    stmt.setDouble(5, Double.parseDouble(txtPrice.getText()));
//
//                    int affectedRows = stmt.executeUpdate();
//                    if (affectedRows > 0) {
//                        connection.commit();
//                        showAlert("Order detail added successfully!");
//                    } else {
//                        showAlert("Failed to add order detail.");
//                        connection.rollback();
//                    }
//                }
//            } catch (SQLException e) {
//                showAlert("Error adding order detail: " + e.getMessage());
//                try {
//                    connection.rollback();
//                } catch (SQLException rollbackEx) {
//                    showAlert("Error rolling back transaction: " + rollbackEx.getMessage());
//                }
//            } finally {
//                try {
//                    connection.setAutoCommit(true);
//                } catch (SQLException e) {
//                    showAlert("Error resetting auto commit: " + e.getMessage());
//                }
//            }
//        }
//    }
//
//    @FXML
//    void handleUpdateOrderDetail(ActionEvent event) {
//        if (validateOrderDetails()) {
//            try {
//
//                connection.setAutoCommit(false);
//
//                String updateOrderDetailQuery = "UPDATE order_details SET order_id = ?, product_id = ?, quantity = ?, price = ? WHERE order_details_id = ?";
//                try (PreparedStatement stmt = connection.prepareStatement(updateOrderDetailQuery)) {
//                    stmt.setInt(1, Integer.parseInt(txtOrderId.getText()));
//                    stmt.setInt(2, Integer.parseInt(txtProductId.getText()));
//                    stmt.setInt(3, Integer.parseInt(txtQuantity.getText()));
//                    stmt.setDouble(4, Double.parseDouble(txtPrice.getText()));
//                    stmt.setInt(5, Integer.parseInt(txtOrderDetailsId.getText()));
//
//                    int affectedRows = stmt.executeUpdate();
//                    if (affectedRows > 0) {
//                        connection.commit();
//                        showAlert("Order detail updated successfully!");
//                    } else {
//                        showAlert("Failed to update order detail.");
//                        connection.rollback();
//                    }
//                }
//            } catch (SQLException e) {
//                showAlert("Error updating order detail: " + e.getMessage());
//                try {
//                    connection.rollback();
//                } catch (SQLException rollbackEx) {
//                    showAlert("Error rolling back transaction: " + rollbackEx.getMessage());
//                }
//            } finally {
//                try {
//                    connection.setAutoCommit(true);
//                } catch (SQLException e) {
//                    showAlert("Error resetting auto commit: " + e.getMessage());
//                }
//            }
//        }
//    }
//
//    @FXML
//    void handleDeleteOrderDetail(ActionEvent event) {
//        if (validateOrderDetails()) {
//            try {
//                connection.setAutoCommit(false);
//
//                String deleteOrderDetailQuery = "DELETE FROM order_details WHERE order_details_id = ?";
//                try (PreparedStatement stmt = connection.prepareStatement(deleteOrderDetailQuery)) {
//                    stmt.setInt(1, Integer.parseInt(txtOrderDetailsId.getText()));
//
//                    int affectedRows = stmt.executeUpdate();
//                    if (affectedRows > 0) {
//                        connection.commit();
//                        showAlert("Order detail deleted successfully!");
//                    } else {
//                        showAlert("Failed to delete order detail.");
//                        connection.rollback();
//                    }
//                }
//            } catch (SQLException e) {
//                showAlert("Error deleting order detail: " + e.getMessage());
//                try {
//                    connection.rollback();
//                } catch (SQLException rollbackEx) {
//                    showAlert("Error rolling back transaction: " + rollbackEx.getMessage());
//                }
//            } finally {
//                try {
//                    connection.setAutoCommit(true);
//                } catch (SQLException e) {
//                    showAlert("Error resetting auto commit: " + e.getMessage());
//                }
//            }
//        }
//    }
//}
