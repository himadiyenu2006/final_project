package lk.ijse.gdse.pizzahubsystem.DAOImpl;

import lk.ijse.gdse.pizzahubsystem.dto.ItemDTO;
import lk.ijse.gdse.pizzahubsystem.dto.OrderDetailsDTO;
import Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public List<String> getAllItemIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT item_id FROM item");

        List<String> itemIds = new ArrayList<>();
        while (rst.next()) {
            itemIds.add(rst.getString(1));
        }
        return itemIds;
    }

    @Override
    public ItemDTO findById(String selectedItemId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM item WHERE item_id=?", selectedItemId);

        if (rst.next()) {
            return new ItemDTO(
                    rst.getString("item_id"),
                    rst.getString("name"),
                    rst.getInt("quantity"),
                    rst.getDouble("price")
            );
        }
        return null;
    }

    @Override
    public boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE item SET quantity = quantity - ? WHERE item_id = ?",
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getOrder_id()
        );
    }

    @Override
    public boolean addItem(ItemDTO newItem) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO item (item_id, name, quantity, price) VALUES (?, ?, ?, ?)",
                newItem.getItem_id(),
                newItem.getName(),
                newItem.getQuantity(),
                newItem.getPrice()
        );
    }

    @Override
    public boolean updateItem(ItemDTO updatedItem) throws SQLException {
        return CrudUtil.execute(
                "UPDATE item SET name = ?, quantity = ?, price = ? WHERE item_id = ?",
                updatedItem.getName(),
                updatedItem.getQuantity(),
                updatedItem.getPrice(),
                updatedItem.getItem_id()
        );
    }

    @Override
    public boolean deleteItem(String itemId) throws SQLException {
        return CrudUtil.execute("DELETE FROM item WHERE item_id = ?", itemId);
    }

    @Override
    public List<ItemDTO> getAllItems() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM item");

        List<ItemDTO> itemList = new ArrayList<>();
        while (rst.next()) {
            itemList.add(new ItemDTO(
                    rst.getString("item_id"),
                    rst.getString("name"),
                    rst.getInt("quantity"),
                    rst.getDouble("price")
            ));
        }
        return itemList;
    }

    @Override
    public boolean saveItem(ItemDTO itemDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO item (item_id, name, quantity, price) VALUES (?, ?, ?, ?)",
                itemDTO.getItem_id(),
                itemDTO.getName(),
                itemDTO.getQuantity(),
                itemDTO.getPrice()
        );
    }
}
