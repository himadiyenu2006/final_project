package model;

import lk.ijse.gdse.pizzahubsystem.dto.ItemDTO;
import lk.ijse.gdse.pizzahubsystem.dto.OrderDetailsDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
}
