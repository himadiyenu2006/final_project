package lk.ijse.gdse.pizzahubsystem.DAOImpl;

import lk.ijse.gdse.pizzahubsystem.dto.ProductDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public abstract class ProductDAO {
    public abstract boolean save(ProductDTO productDTO) throws SQLException;

    public abstract boolean updateProduct(ProductDTO productDTO) throws SQLException;

    public abstract boolean deleteProduct(String productId) throws SQLException;

    public abstract ArrayList<ProductDTO> getAllProducts() throws SQLException;

    public abstract ProductDTO findById(String selectedProdId) throws SQLException;

    public abstract String getNextProductId() throws SQLException;
}
