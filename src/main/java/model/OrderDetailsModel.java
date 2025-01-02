package model;

import lk.ijse.gdse.pizzahubsystem.dto.OrderDetailsDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsModel {

    private Util.CrudUtil CrudUtil;

    public String getNextOrderId() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select order_id from order_details order by order_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("O%03d", newIdIndex);
        }
        return "O001";
    }


    public boolean saveOrderDetails(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "insert into order_details(orderdetail_id , order_id, order_date, customer_id, product_id, quantity, total_price) values (?,?,?,?,?,?)",
                orderDetailsDTO.getOrderDetail_id(),
                orderDetailsDTO.getOrder_id(),
                orderDetailsDTO.getProduct_id(),
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getPrice()
        );
    }

    public ArrayList<OrderDetailsDTO> getAllOrderDetails() throws SQLException {

        ResultSet rst = Util.CrudUtil.execute("select * from orderdetails");

        ArrayList<OrderDetailsDTO> orderDetailsList = new ArrayList<>();

        while (rst.next()) {
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getDouble(5)

            );
            System.out.println(orderDetailsDTO);
            orderDetailsList.add(orderDetailsDTO);
        }
        return orderDetailsList;
    }


    public boolean updateOrderDetails(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "update order_details set order_id=?, product_id=?, quantity=?, price=? where order_id=?",
                orderDetailsDTO.getOrder_id(),
                orderDetailsDTO.getProduct_id(),
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getPrice(),
                orderDetailsDTO.getOrderDetail_id()
        );
    }


    public boolean deleteOrderDetails(String orderId) throws SQLException {
        return Util.CrudUtil.execute("delete from order_details where order_id=?", orderId);
    }


    public OrderDetailsDTO findById(String selectedOrderId) throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select * from order_details where order_id=?", selectedOrderId);

        if (rst.next()) {
            return new OrderDetailsDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getDouble(5)
            );
        }
        return null;
    }
}
