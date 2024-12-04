package lk.ijse.gdse.pizzahubsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lk.ijse.gdse.pizzahubsystem.dto.ProductDTO;
import lk.ijse.gdse.pizzahubsystem.dto.tm.ProductTM;
import model.ProductModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductController {

    public TableView tblProducts;

    public TableColumn colProductId;

    public TableColumn colName;

    public TableColumn colCategory;

    public TableColumn colInventoryCount;

    public TableColumn colDescription;

    public TableColumn colPrice;
    @FXML
    private TextField txtProductId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtInventoryCount;

    @FXML
    private TableView<ProductTM> productTable;

    @FXML
    private TableColumn<ProductTM, String> productId;

    @FXML
    private TableColumn<ProductTM, String> name;

    @FXML
    private TableColumn<ProductTM, String> description;

    @FXML
    private TableColumn<ProductTM, String> inventorycount;

    @FXML
    private TableColumn<ProductTM, String> category;

    @FXML
    private TableColumn<ProductTM, Double> price;



    public boolean validateProductId(String productId) {
        return productId != null && !productId.trim().isEmpty();
    }

    public boolean validateProductName(String name) {
        return name != null && !name.trim().isEmpty() && name.length() <= 50;
    }

    public boolean validateProductDescription(String description) {
        return description == null || description.length() <= 200;
    }

    public boolean validateInventoryCount(int inventoryCount) {
        return inventoryCount >= 0;
    }

    public boolean validateCategory(String category) {
        return category != null && !category.trim().isEmpty();
    }

    public boolean validatePrice(double price) {
        return price > 0;
    }

    public boolean validateProduct(String productId, String name, String description, int inventoryCount, String category, double price) {
        return validateProductId(productId) &&
                validateProductName(name) &&
                validateProductDescription(description) &&
                validateInventoryCount(inventoryCount) &&
                validateCategory(category) &&
                validatePrice(price);
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Product Operation Failed");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Product Operation Successful");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadTableData() throws SQLException {
        ArrayList<ProductDTO> producta = ProductModel.getAllProducts();
        ObservableList<ProductTM> productTMS = FXCollections.observableArrayList();
        for (ProductDTO product : producta) {
            productTMS.add(new ProductTM(
                    product.getProduct_id(),
                    product.getProduct_name(),
                    product.getPrice(),
                    product.getDescription(),
                    product.getCategory(),
                    product.getInventory_count()

            ));
        }
        tblProducts.setItems(productTMS);
    }
    @FXML
    public void saveOnClick(ActionEvent event) throws SQLException {
        String productId = txtProductId.getText();
        String name = txtName.getText();
        String description = txtDescription.getText();
        int inventoryCount = Integer.parseInt(txtInventoryCount.getText());
        String category = txtCategory.getText();
        double price = Double.parseDouble(txtPrice.getText());

        ProductDTO productDTO = new ProductDTO(productId, name, price, description, category, inventoryCount);
        boolean issaved = ProductModel.save(productDTO);
        if (issaved){
            new Alert(AlertType.INFORMATION, "Product saved successfully!").show();
        }else{
            new Alert(AlertType.ERROR, "Failed to save product.").show();
        }
    }

    @FXML
    public void updateOnClick(ActionEvent event) throws SQLException {
        String productId = txtProductId.getText();
        String name = txtName.getText();
        String description = txtDescription.getText();
        int inventoryCount = Integer.parseInt(txtInventoryCount.getText());
        String category = txtCategory.getText();
        double price = Double.parseDouble(txtPrice.getText());

        ProductDTO productDTO = new ProductDTO(productId, name, price, description, category, inventoryCount);
        boolean isUpdated = ProductModel.updateproduct(productDTO);
        if (isUpdated) {

            new Alert(Alert.AlertType.INFORMATION, "Customer updated...!").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Fail to update customer...!").show();
        }

    }

    @FXML
    public void deleteOnClick(ActionEvent event) throws SQLException {
        String productId = txtProductId.getText();
        boolean isdelete = ProductModel.deleteproduct(productId);

        if (isdelete){
            new Alert(AlertType.CONFIRMATION,"product deleted successfully!").show();
        }else {
            new Alert(AlertType.ERROR,"Fail to delete product...!").show();
        }
    }

    @FXML
    public void handleSubmitAction(ActionEvent event) {

        System.out.println("Submit button clicked!");
    }

    @FXML
    public void addToCartOnClick(ActionEvent event) {

        System.out.println("Add to Cart button clicked!");
    }
}
