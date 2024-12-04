package lk.ijse.gdse.pizzahubsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.ijse.gdse.pizzahubsystem.dto.tm.OrderTM;

public class OrderController {

    public TableColumn collStatus;

    public TableColumn colTotalPrice;

    public javafx.scene.control.TableView tblorder;

    public TextField txtStatus;
    @FXML
    private ComboBox<OrderTM> cmbCustomerId;

    @FXML
    private TableColumn<OrderTM, String> colAction;

    @FXML
    private TableColumn<OrderTM, String> colName;

    @FXML
    private TableColumn<OrderTM, Double> colPrice;

    @FXML
    private TableColumn<OrderTM, String> colQuantity;

    @FXML
    private TableColumn<OrderTM, String> colTotal;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblItemPrice;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label orderDate;

    @FXML
    private TableView<OrderTM> tblOrder;

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        if (validateOrderDetails()) {
            showAlert("Order Placed", "Your order has been successfully placed!", Alert.AlertType.INFORMATION);

            btnResetOnAction(event);
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        cmbCustomerId.getSelectionModel().clearSelection();
        lblCustomerName.setText("");
        lblItemPrice.setText("");
        lblOrderId.setText("");
        orderDate.setText("");

        tblOrder.getItems().clear();
    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) {
        OrderTM selectedCustomer = cmbCustomerId.getValue();
        if (selectedCustomer != null) {
            lblCustomerName.setText(String.valueOf(selectedCustomer.getCustomer_id()));
        }
    }

    private boolean validateOrderDetails() {
        if (cmbCustomerId.getValue() == null) {
            showAlert("Validation Error", "Please select a customer.", Alert.AlertType.WARNING);
            return false;
        }

        if (tblOrder.getItems().isEmpty()) {
            showAlert("Validation Error", "Please add items to the order before placing it.", Alert.AlertType.WARNING);
            return false;
        }

        return true;
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
