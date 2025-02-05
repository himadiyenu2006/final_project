package lk.ijse.gdse.pizzahubsystem.DAOImpl;

import lk.ijse.gdse.pizzahubsystem.dto.InventoryDTO;
import Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryDAOImpl implements InventoryDAO {

    @Override
    public boolean saveInventory(InventoryDTO inventoryDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO inventory (inventory_id, product_id, supplier_id, quantity, last_updated) VALUES (?, ?, ?, ?, ?)",
                inventoryDTO.getInventory_id(),
                inventoryDTO.getProduct_id(),
                inventoryDTO.getSupplier_id(),
                inventoryDTO.getQuantity(),
                inventoryDTO.getLast_updated()
        );
    }

    @Override
    public String getNextInventoryId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT inventory_id FROM inventory ORDER BY inventory_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            int newId = Integer.parseInt(lastId) + 1;
            return String.format("%04d", newId);
        }
        return "0001";
    }

    @Override
    public ArrayList<InventoryDTO> getAllInventoryItems() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM inventory");

        ArrayList<InventoryDTO> inventoryItems = new ArrayList<>();
        while (rst.next()) {
            inventoryItems.add(new InventoryDTO(
                    rst.getString("inventory_id"),
                    rst.getString("product_id"),
                    rst.getString("supplier_id"),
                    rst.getInt("quantity"),
                    rst.getString("last_updated")
            ));
        }
        return inventoryItems;
    }

    @Override
    public boolean updateInventory(InventoryDTO inventoryDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE inventory SET product_id=?, supplier_id=?, quantity=?, last_updated=? WHERE inventory_id=?",
                inventoryDTO.getProduct_id(),
                inventoryDTO.getSupplier_id(),
                inventoryDTO.getQuantity(),
                inventoryDTO.getLast_updated(),
                inventoryDTO.getInventory_id()
        );
    }

    @Override
    public boolean deleteInventory(String inventoryId) throws SQLException {
        return CrudUtil.execute("DELETE FROM inventory WHERE inventory_id=?", inventoryId);
    }

    @Override
    public InventoryDTO findById(String inventoryId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM inventory WHERE inventory_id=?", inventoryId);

        if (rst.next()) {
            return new InventoryDTO(
                    rst.getString("inventory_id"),
                    rst.getString("product_id"),
                    rst.getString("supplier_id"),
                    rst.getInt("quantity"),
                    rst.getString("last_updated")
            );
        }
        return null;
    }
}
