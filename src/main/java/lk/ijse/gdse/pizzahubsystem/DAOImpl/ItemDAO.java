package lk.ijse.gdse.pizzahubsystem.DAOImpl;

import lk.ijse.gdse.pizzahubsystem.dto.ItemDTO;
import lk.ijse.gdse.pizzahubsystem.dto.OrderDetailsDTO;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO {
    List<String> getAllItemIds() throws SQLException;
    ItemDTO findById(String selectedItemId) throws SQLException;
    boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException;
    boolean addItem(ItemDTO newItem) throws SQLException;
    boolean updateItem(ItemDTO updatedItem) throws SQLException;
    boolean deleteItem(String itemId) throws SQLException;
    List<ItemDTO> getAllItems() throws SQLException;
    boolean saveItem(ItemDTO itemDTO) throws SQLException;
}
