package model;

import lk.ijse.gdse.pizzahubsystem.dto.ProductDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductModel {

    private Util.CrudUtil CrudUtil;

    public static boolean updateproduct(ProductDTO ProductDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "UPDATE product SET product_name = ?, price = ?, description = ?, category = ?, inventory_count = ? WHERE product_id = ?",
                ProductDTO.getProduct_name(),
                ProductDTO.getPrice(),
                ProductDTO.getDescription(),
                ProductDTO.getCategory(),
                ProductDTO.getInventory_count(),
                ProductDTO.getProduct_id()
        );
    }

    public static boolean deleteproduct(String productId) throws SQLException {
        return Util.CrudUtil.execute(
                "delete from product where product_id=?",
                productId
        );
    }

    public static boolean save(ProductDTO productDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "insert into product(product_id, Product_name, price ,description , category, inventory_count) values (?,?,?,?,?,?)",
                productDTO.getProduct_id(),
                productDTO.getProduct_name(),
                productDTO.getPrice(),
                productDTO.getDescription(),
                productDTO.getCategory(),
                productDTO.getInventory_count()
        );
    }


    public String getNextProductId() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select product_id from product order by product_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }
        return "P001";
    }


    public static ArrayList<ProductDTO> getAllProducts() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select * from product");

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

    public ProductDTO findById(String selectedProdId) throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select * from product where product_id=?", selectedProdId);

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
}
