package model;

import lk.ijse.gdse.pizzahubsystem.dto.InventoryDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryModel {


    //private DBConnection DatabaseConnection;

    public String getNextInventoryId() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("SELECT inventory_id FROM inventory ORDER BY inventory_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            int newId = Integer.parseInt(lastId) + 1;
            return String.format("%04d", newId);
        }
        return "0001";
    }

    public boolean saveInventory(InventoryDTO inventoryDTO) throws SQLException {
        System.out.println(inventoryDTO+"model");
        return Util.CrudUtil.execute(
                "INSERT INTO inventory(inventory_id, product_id, supplier_id, quantity, last_updated) VALUES (?, ?, ?, ?,?)",
                inventoryDTO.getInventory_id(),
                inventoryDTO.getProduct_id(),
                inventoryDTO.getSupplier_id(),
                inventoryDTO.getQuantity(),
                inventoryDTO.getLast_updated()

        );
    }

    public ArrayList<InventoryDTO> getAllInventoryItems() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("SELECT * FROM inventory");

        ArrayList<InventoryDTO> inventoryItems = new ArrayList<>();

        while (rst.next()) {
            InventoryDTO inventoryDTO = new InventoryDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getString(5)

                    );
            System.out.println(inventoryDTO);
            inventoryItems.add(inventoryDTO);
        }
        return inventoryItems;
    }

    public boolean updateInventory(InventoryDTO inventoryDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "UPDATE inventory SET product_id=?, supplier_id=? , quantity=?, last_updated=? WHERE inventory_id=?",
                inventoryDTO.getInventory_id(),
                inventoryDTO.getProduct_id(),
                inventoryDTO.getSupplier_id(),
                inventoryDTO.getQuantity(),
                inventoryDTO.getLast_updated()

        );
    }


    public boolean deleteInventory(int inventoryId) throws SQLException {
        return Util.CrudUtil.execute("DELETE FROM inventory WHERE inventory_id=?", inventoryId);
    }

    public InventoryDTO findById(int inventoryId) throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("SELECT * FROM inventory WHERE inventory_id=?", inventoryId);

        if (rst.next()) {
            return new InventoryDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getString(5)
            );
        }
        return null;
    }

    public InventoryDTO getInventoryById(String inventoryId) throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("SELECT * FROM inventory WHERE inventory_id=?", inventoryId);

        if (rst.next()) {
            return new InventoryDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getString(5)
            );
        }
        return null;
    }
//        String query = "SELECT * FROM inventory WHERE inventory_id = ?";
//
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//            pstmt.setString(1, inventoryId);
//
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                return new InventoryDTO(
//                        rs.getString("inventory_id"),
//                        rs.getString("product_id"),
//                        rs.getString("supplier_id"),
//                        rs.getInt("quantity"),
//                        rs.getString("last_updated")
//                );
//            } else {
//                return null;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

}
