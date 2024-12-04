package lk.ijse.gdse.pizzahubsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import lk.ijse.gdse.pizzahubsystem.dto.tm.OrderDetailsTM;
import javafx.scene.control.TableView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDetailsController {

    public TableView tblOrderdetails;
    @FXML
    private TableColumn<OrderDetailsTM,String> ColOrderDetailsId;

    @FXML
    private TableColumn<OrderDetailsTM , String> colOrderId;

    @FXML
    private TableColumn<OrderDetailsTM , String> colPrice;

    @FXML
    private TableColumn<OrderDetailsTM , String > colProductId;

    @FXML
    private TableColumn<OrderDetailsTM , String > colQuantity;

    @FXML
    private ImageView imageid;

    @FXML
    private TextField txtOrderDetailsId;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtProductId;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TableView<OrderDetailsTM> tblOrderDetails;

    private Connection connection;

    public OrderDetailsController() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pizzahubsystemdb", "root", "1234");
        } catch (SQLException e) {
            showAlert("Error establishing database connection: " + e.getMessage());
        }
    }

    private boolean validateOrderDetails() {
        if (txtOrderDetailsId.getText().isEmpty()) {
            showAlert("Order Details ID is required.");
            return false;
        }
        if (txtOrderId.getText().isEmpty()) {
            showAlert("Order ID is required.");
            return false;
        }
        if (txtPrice.getText().isEmpty()) {
            showAlert("Price is required.");
            return false;
        }
        try {
            double price = Double.parseDouble(txtPrice.getText());
            if (price <= 0) {
                showAlert("Price must be a positive number.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Price must be a valid number.");
            return false;
        }
        if (txtProductId.getText().isEmpty()) {
            showAlert("Product ID is required.");
            return false;
        }
        if (txtQuantity.getText().isEmpty()) {
            showAlert("Quantity is required.");
            return false;
        }
        try {
            int quantity = Integer.parseInt(txtQuantity.getText());
            if (quantity <= 0) {
                showAlert("Quantity must be a positive integer.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Quantity must be a valid integer.");
            return false;
        }
        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void handleAddOrderDetail(ActionEvent event) {
        if (validateOrderDetails()) {
            try {
                connection.setAutoCommit(false);

                String orderDetailQuery = "INSERT INTO order_details (order_details_id, order_id, product_id, quantity, price) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = connection.prepareStatement(orderDetailQuery)) {
                    stmt.setInt(1, Integer.parseInt(txtOrderDetailsId.getText()));
                    stmt.setInt(2, Integer.parseInt(txtOrderId.getText()));
                    stmt.setInt(3, Integer.parseInt(txtProductId.getText()));
                    stmt.setInt(4, Integer.parseInt(txtQuantity.getText()));
                    stmt.setDouble(5, Double.parseDouble(txtPrice.getText()));

                    int affectedRows = stmt.executeUpdate();
                    if (affectedRows > 0) {
                        connection.commit();
                        showAlert("Order detail added successfully!");
                    } else {
                        showAlert("Failed to add order detail.");
                        connection.rollback();
                    }
                }
            } catch (SQLException e) {
                showAlert("Error adding order detail: " + e.getMessage());
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    showAlert("Error rolling back transaction: " + rollbackEx.getMessage());
                }
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    showAlert("Error resetting auto commit: " + e.getMessage());
                }
            }
        }
    }

    @FXML
    void handleUpdateOrderDetail(ActionEvent event) {
        if (validateOrderDetails()) {
            try {

                connection.setAutoCommit(false);

                String updateOrderDetailQuery = "UPDATE order_details SET order_id = ?, product_id = ?, quantity = ?, price = ? WHERE order_details_id = ?";
                try (PreparedStatement stmt = connection.prepareStatement(updateOrderDetailQuery)) {
                    stmt.setInt(1, Integer.parseInt(txtOrderId.getText()));
                    stmt.setInt(2, Integer.parseInt(txtProductId.getText()));
                    stmt.setInt(3, Integer.parseInt(txtQuantity.getText()));
                    stmt.setDouble(4, Double.parseDouble(txtPrice.getText()));
                    stmt.setInt(5, Integer.parseInt(txtOrderDetailsId.getText()));

                    int affectedRows = stmt.executeUpdate();
                    if (affectedRows > 0) {
                        connection.commit();
                        showAlert("Order detail updated successfully!");
                    } else {
                        showAlert("Failed to update order detail.");
                        connection.rollback();
                    }
                }
            } catch (SQLException e) {
                showAlert("Error updating order detail: " + e.getMessage());
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    showAlert("Error rolling back transaction: " + rollbackEx.getMessage());
                }
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    showAlert("Error resetting auto commit: " + e.getMessage());
                }
            }
        }
    }

    @FXML
    void handleDeleteOrderDetail(ActionEvent event) {
        if (validateOrderDetails()) {
            try {
                connection.setAutoCommit(false);

                String deleteOrderDetailQuery = "DELETE FROM order_details WHERE order_details_id = ?";
                try (PreparedStatement stmt = connection.prepareStatement(deleteOrderDetailQuery)) {
                    stmt.setInt(1, Integer.parseInt(txtOrderDetailsId.getText()));

                    int affectedRows = stmt.executeUpdate();
                    if (affectedRows > 0) {
                        connection.commit();
                        showAlert("Order detail deleted successfully!");
                    } else {
                        showAlert("Failed to delete order detail.");
                        connection.rollback();
                    }
                }
            } catch (SQLException e) {
                showAlert("Error deleting order detail: " + e.getMessage());
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    showAlert("Error rolling back transaction: " + rollbackEx.getMessage());
                }
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    showAlert("Error resetting auto commit: " + e.getMessage());
                }
            }
        }
    }
}
