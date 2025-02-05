package lk.ijse.gdse.pizzahubsystem.DAOImpl;

import Util.CrudUtil;
import lk.ijse.gdse.pizzahubsystem.dto.ProductDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAOImpl extends ProductDAO {

    @Override
    public boolean save(ProductDTO productDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO product (product_id, product_name, price, description, category, inventory_count) VALUES (?,?,?,?,?,?)",
                productDTO.getProduct_id(),
                productDTO.getProduct_name(),
                productDTO.getPrice(),
                productDTO.getDescription(),
                productDTO.getCategory(),
                productDTO.getInventory_count()
        );
    }

    @Override
    public boolean updateProduct(ProductDTO productDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE product SET product_name = ?, price = ?, description = ?, category = ?, inventory_count = ? WHERE product_id = ?",
                productDTO.getProduct_name(),
                productDTO.getPrice(),
                productDTO.getDescription(),
                productDTO.getCategory(),
                productDTO.getInventory_count(),
                productDTO.getProduct_id()
        );
    }

    @Override
    public boolean deleteProduct(String productId) throws SQLException {
        return CrudUtil.execute(
                "DELETE FROM product WHERE product_id = ?",
                productId
        );
    }

    @Override
    public ArrayList<ProductDTO> getAllProducts() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM product");

        ArrayList<ProductDTO> productDTOS = new ArrayList<>();

        while (rst.next()) {
            ProductDTO productDTO = new ProductDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getInt(6)
            );
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    @Override
    public ProductDTO findById(String selectedProdId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM product WHERE product_id = ?", selectedProdId);

        if (rst.next()) {
            return new ProductDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getInt(6)
            );
        }
        return null;
    }

    @Override
    public String getNextProductId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT product_id FROM product ORDER BY product_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }
        return "P001";
    }
}
