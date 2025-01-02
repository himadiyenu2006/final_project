package lk.ijse.gdse.pizzahubsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.pizzahubsystem.dto.OrderDTO;
import lk.ijse.gdse.pizzahubsystem.dto.tm.OrderTM;
import model.OrderModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

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
    public TextField txttotalprice;
    public TextField txtcustomerId2;
    public DatePicker datetxt;
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
    private TextField txtTotalPrice;

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        try {
            String orderId = lblOrderId.getText();
            String customerId = txtcustomerId.getText();
            String orderDate = String.valueOf(datetxt.getValue());
            String totalPrice = String.valueOf(Double.parseDouble(txttotalprice.getText()));
            String status = txtcustomerId2.getText();
            
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
    public void initialize() throws SQLException {
        colorderId.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        colorderDate.setCellValueFactory(new PropertyValueFactory<>("order_date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        colcustomerId.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        
        loadOrders();
        
        generateOrderId();
    }

    private void resetForm() {
        lblOrderId.setText("");
        txtcustomerId.clear();
        dpOrderDate.setValue(null);
        txttotalprice.clear();
        txtcustomerId2.clear();
    }

    private void loadOrders() throws SQLException {

            ObservableList<OrderTM> orderTMS = FXCollections.observableArrayList();
            List<OrderDTO> sList = OrderModel.getAllOrders();
            for (OrderDTO orderDTO : sList) {
                OrderTM orderTM = new OrderTM(
                        orderDTO.getOrder_id(),
                        orderDTO.getOrder_date(),
                        orderDTO.getStatus(),
                        orderDTO.getTotal_price(),
                        orderDTO.getCustomer_id()
                );
                orderTMS.add(orderTM);
            }

            tblorder.setItems(orderTMS);
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
