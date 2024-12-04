package model;

import lk.ijse.gdse.pizzahubsystem.dto.OrderDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderModel {

    private Util.CrudUtil CrudUtil;

    public String getNextOrderId() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select order_id from orders order by order_id desc limit 1");

        if (rst.next()) {
            int lastId = rst.getInt(1);
            int newIdIndex = lastId + 1;
            return String.format("%03d", newIdIndex);
        }
        return "001";
    }


    public boolean saveOrder(OrderDTO orderDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "insert into orders(order_id, customer_id, order_date, total_amount, status) values (?,?,?,?,?)",
                orderDTO.getOrder_id(),
                orderDTO.getOrder_date(),
                orderDTO.getStatus(),
                orderDTO.getTotal_price(),
                orderDTO.getCustomer_id()
        );
    }


    public ArrayList<OrderDTO> getAllOrders() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select * from orders");

        ArrayList<OrderDTO> orderDTOS = new ArrayList<>();

        while (rst.next()) {
            OrderDTO orderDTO = new OrderDTO(
                    rst.getString(1),
                    rst.getDate(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getString(5)
            );
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }


    public boolean updateOrder(OrderDTO orderDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "update orders set customer_id=?, order_date=?, status=? , total_price=?, where order_id=?",
                orderDTO.getCustomer_id(),
                orderDTO.getOrder_date(),
                orderDTO.getStatus(),
                orderDTO.getTotal_price(),
                orderDTO.getOrder_id()

        );
    }

    public boolean deleteOrder(String orderId) throws SQLException {
        return Util.CrudUtil.execute("delete from orders where order_id=?", orderId);
    }

    public OrderDTO findById(String orderId) throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select * from orders where order_id=?", orderId);

        if (rst.next()) {
            return new OrderDTO(
                    rst.getString(1),
                    rst.getDate(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getString(5)
            );
        }
        return null;
    }
}
