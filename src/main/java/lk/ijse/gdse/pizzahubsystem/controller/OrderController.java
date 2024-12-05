package lk.ijse.gdse.pizzahubsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.pizzahubsystem.dto.OrderDTO;
import model.OrderModel;
import javafx.scene.control.TableView;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
public class OrderController {

    public Label txtorderdate;
    public Label orderDate;
    public TextField txtcustomerId;
    public TextField txtcustomerId1;
    public Label lblItemPrice;
    public TableView tblorder;
    public TableColumn colorderId;
    public TableColumn colorderDate;
    public TableColumn colcustomerId;
    public Button btnrest;
    public Button btnplaceorder;
    @FXML
    private Button btnPlaceOrder;

    @FXML
    private Button btnReset;

    @FXML
    private TableColumn<OrderDTO, String> colStatus;

    @FXML
    private TableColumn<OrderDTO, Double> colTotal;

    @FXML
    private TableColumn<OrderDTO, String> colCustomerId;

    @FXML
    private TableColumn<OrderDTO, LocalDate> colOrderDate;

    @FXML
    private TableColumn<OrderDTO, String> colOrderId;

    @FXML
    private Label lblOrderId;

    @FXML
    private DatePicker dpOrderDate;

    @FXML
    private TableView<OrderDTO> tblOrder;

    @FXML
    private TextField txtStatus;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtTotalPrice;

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        try {
            String orderId = lblOrderId.getText();
            String customerId = txtCustomerId.getText();
            LocalDate orderDate = dpOrderDate.getValue();
            double totalPrice = Double.parseDouble(txtTotalPrice.getText());
            String status = txtStatus.getText();
            
            if (orderId.isEmpty() || customerId.isEmpty() || orderDate == null || status.isEmpty()) {
                showAlert("Validation Error", "Please fill in all fields correctly.", Alert.AlertType.WARNING);
                return;
            }
            
            OrderDTO order = new OrderDTO(orderId, orderDate, status, totalPrice, customerId);
            
            boolean isSaved = new OrderModel().saveOrder(order);

            if (isSaved) {
                showAlert("Success", "Order placed successfully!", Alert.AlertType.INFORMATION);
                loadOrders(); 
                resetForm();  
            } else {
                showAlert("Error", "Failed to place the order.", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Please enter a valid total price.", Alert.AlertType.WARNING);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while saving the order.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        resetForm();
    }

    @FXML
    public void initialize() {
        colorderId.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        colcustomerId.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        colorderDate.setCellValueFactory(new PropertyValueFactory<>("order_date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        
        loadOrders();
        
        generateOrderId();
    }

    private void resetForm() {
        lblOrderId.setText("");
        txtcustomerId.clear();
        dpOrderDate.setValue(null);
        txtTotalPrice.clear();
        txtStatus.clear();
    }

    private void loadOrders() {
        try {
            ArrayList<OrderDTO> orderDTOS = new OrderModel().getAllOrders();

            tblorder.getItems().clear();

           // tblOrder.getItems().addAll(orderDTOS);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load orders.", Alert.AlertType.ERROR);
        }
    }


    private void generateOrderId() {
        try {
            String nextOrderId = new OrderModel().getNextOrderId();
            lblOrderId.setText(nextOrderId);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to generate Order ID.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
