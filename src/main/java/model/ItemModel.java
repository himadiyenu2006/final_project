package model;

import lk.ijse.gdse.pizzahubsystem.dto.ItemDTO;
import lk.ijse.gdse.pizzahubsystem.dto.OrderDetailsDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {

    private Util.CrudUtil CrudUtil;

    public ArrayList<String> getAllItemIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select item_id from item");

        ArrayList<String> itemIds = new ArrayList<>();

        while (rst.next()) {
            itemIds.add(rst.getString(1));
        }

        return itemIds;
    }

    public ItemDTO findById(String selectedItemId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from item where item_id=?", selectedItemId);

        if (rst.next()) {
            return new ItemDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            );
        }

        return null;
    }

    public boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        return CrudUtil.execute(
                "update item set quantity = quantity - ? where item_id = ?",
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getOrder_id()
        );
    }

    public boolean addItem(ItemDTO newItem) {
        try {
            return CrudUtil.execute(
                    "INSERT INTO item (item_id, name, quantity, price) VALUES (?, ?, ?, ?)",
                    newItem.getItem_id(),
                    newItem.getName(),
                    newItem.getQuantity(),
                    newItem.getPrice()
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateItem(ItemDTO updatedItem) {
        try {
            return CrudUtil.execute(
                    "UPDATE item SET name = ?, quantity = ?, price = ? WHERE item_id = ?",
                    updatedItem.getName(),
                    updatedItem.getQuantity(),
                    updatedItem.getPrice(),
                    updatedItem.getItem_id()
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteItem(String itemId) {
        try {
            return CrudUtil.execute(
                    "DELETE FROM item WHERE item_id = ?",
                    itemId
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ItemDTO> getAllItems() {
        List<ItemDTO> itemList = new ArrayList<>();
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM item");

            while (rst.next()) {
                ItemDTO item = new ItemDTO(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getInt(3),
                        rst.getDouble(4)
                );
                itemList.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemList;
    }

    public boolean saveItem(ItemDTO itemDTO) {
        try {
            return CrudUtil.execute(
                    "INSERT INTO item (item_id, name, quantity, price) VALUES (?, ?, ?, ?)",
                    itemDTO.getItem_id(),
                    itemDTO.getName(),
                    itemDTO.getQuantity(),
                    itemDTO.getPrice()
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
