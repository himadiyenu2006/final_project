package lk.ijse.gdse.pizzahubsystem.DAOImpl;

import lk.ijse.gdse.pizzahubsystem.dto.InventoryDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InventoryDAO {
    boolean saveInventory(InventoryDTO inventoryDTO) throws SQLException;
    String getNextInventoryId() throws SQLException;
    ArrayList<InventoryDTO> getAllInventoryItems() throws SQLException;
    boolean updateInventory(InventoryDTO inventoryDTO) throws SQLException;
    boolean deleteInventory(String inventoryId) throws SQLException;
    InventoryDTO findById(String inventoryId) throws SQLException;
}
