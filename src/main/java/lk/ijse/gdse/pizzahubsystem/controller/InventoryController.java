package lk.ijse.gdse.pizzahubsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.ijse.gdse.pizzahubsystem.dto.tm.InventoryTM;
import javafx.scene.control.TableView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class InventoryController {

    public Label priceFeild;
    public TableColumn itemIdColumn;

    public TableColumn itemNameColumn;

    public TableColumn quantityColumn;

    public TableColumn priceColumn;
    
    public TableColumn colinventoryId;
    
    public TableColumn colProductId;
    
    public TableColumn colSupplierId;
    
    public TableColumn colQuantity;
    
    public TableColumn colLast_Updated;
    
    public TableView inventorytable;
    
    public TextField txtInventoryId;
    
    public TextField txtproductId;
    
    public TextField txtSupplierId;
    
    public Button btnAdd;
    
    public Button btnUpdated;
    
    public Button btnDelete;
    
    public TextField txtlast_updated;
    
    public TextField quantityId;

    @FXML
    private TableView<InventoryTM> inventoryTable;
    @FXML
    private TableColumn<InventoryTM, Integer> columnId;
    @FXML
    private TableColumn<InventoryTM, String> columnName;
    @FXML
    private TableColumn<InventoryTM, Integer> columnQuantity;
    @FXML
    private TableColumn<InventoryTM, Double> columnPrice;
    @FXML
    private TextField quantityField;

    @FXML
    private TextField priceField;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button searchButton;

    private static final Pattern PRICE_PATTERN = Pattern.compile("^(\\d+)(\\.\\d{1,2})?$");
    private static final Pattern QUANTITY_PATTERN = Pattern.compile("^[1-9][0-9]*$");

    private Connection connection;

    public InventoryController() {
       /* try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pizzahubsystemdb", "root", "1234");
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    @FXML
    public void handleAddItem(ActionEvent actionEvent) {
        if (validateInputs()) {
            try {
                String sql = "INSERT INTO inventory (item_name, quantity, price) VALUES (?, ?, ?)";
                PreparedStatement pst = connection.prepareStatement(sql);
                pst.setString(1, "New Item");
                pst.setInt(2, Integer.parseInt(quantityField.getText()));
                pst.setDouble(3, Double.parseDouble(priceField.getText()));

                int affectedRows = pst.executeUpdate();
                if (affectedRows > 0) {
                    connection.commit();
                    showAlert("Success", "Item added successfully!");
                }
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                showAlert("Database Error", "Failed to add item: " + e.getMessage());
            }
        } else {
            showAlert("Validation Error", "Please enter valid quantity and price values.");
        }
    }

    @FXML
    public void handleUpdateItem(ActionEvent actionEvent) {
        if (validateInputs()) {
            try {
                String sql = "UPDATE inventory SET quantity = ?, price = ? WHERE item_id = ?";
                PreparedStatement pst = connection.prepareStatement(sql);
                pst.setInt(1, Integer.parseInt(quantityField.getText()));
                pst.setDouble(2, Double.parseDouble(priceField.getText()));

                int affectedRows = pst.executeUpdate();
                if (affectedRows > 0) {
                    connection.commit();
                    showAlert("Success", "Item updated successfully!");
                }
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                showAlert("Database Error", "Failed to update item: " + e.getMessage());
            }
        } else {
            showAlert("Validation Error", "Please enter valid quantity and price values.");
        }
    }

    @FXML
    public void handleDeleteItem(ActionEvent actionEvent) {
        try {
            String sql = "DELETE FROM inventory WHERE item_id = ?";
            PreparedStatement pst = connection.prepareStatement(sql);

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                connection.commit();
                showAlert("Success", "Item deleted successfully!");
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            showAlert("Database Error", "Failed to delete item: " + e.getMessage());
        }
    }

    private boolean validateInputs() {
        boolean valid = true;

        if (!validateQuantity(quantityField.getText())) {
            showAlert("Invalid Quantity", "Please enter a valid quantity (positive integer).");
            valid = false;
        }

        if (!validatePrice(priceField.getText())) {
            showAlert("Invalid Price", "Please enter a valid price (positive decimal number).");
            valid = false;
        }

        return valid;
    }

    private boolean validateQuantity(String quantity) {
        return QUANTITY_PATTERN.matcher(quantity).matches();
    }

    private boolean validatePrice(String price) {
        return PRICE_PATTERN.matcher(price).matches();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /*public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
*/
    public void handleSearchItem(ActionEvent actionEvent) {

    }

    public void AddOnAction(ActionEvent actionEvent) {
    }

    public void UpdateOnAction(ActionEvent actionEvent) {
    }

    public void deleteOnAction(ActionEvent actionEvent) {
    }

    public void tblInventory(SortEvent<TableView> tableViewSortEvent) {

    }
}
