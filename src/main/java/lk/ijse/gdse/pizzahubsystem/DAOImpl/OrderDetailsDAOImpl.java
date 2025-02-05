package lk.ijse.gdse.pizzahubsystem.DAOImpl;

import Util.CrudUtil;
import lk.ijse.gdse.pizzahubsystem.db.DBConnection;
import lk.ijse.gdse.pizzahubsystem.dto.OrderDetailsDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

    private final Connection connection;

    public OrderDetailsDAOImpl() throws SQLException {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public String getNextOrderId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT order_id FROM order_details ORDER BY order_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("O%03d", newIdIndex);
        }
        return "O001";
    }

    @Override
    public boolean saveOrderDetails(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO order_details (orderdetail_id, order_id, order_date, customer_id, product_id, quantity, total_price) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)",
                orderDetailsDTO.getOrderDetail_id(),
                orderDetailsDTO.getOrder_id(),
                orderDetailsDTO.getOrder_date(),
                orderDetailsDTO.getCustomer_id(),
                orderDetailsDTO.getProduct_id(),
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getTotal_price()
        );
    }

    @Override
    public ArrayList<OrderDetailsDTO> getAllOrderDetails() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM order_details");

        ArrayList<OrderDetailsDTO> orderDetailsList = new ArrayList<>();
        while (rst.next()) {
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                    rst.getString("orderdetail_id"),
                    rst.getString("order_id"),
                    rst.getString("order_date"),
                    rst.getInt("quantity"),
                    rst.getDouble("total_price")
            );
            orderDetailsList.add(orderDetailsDTO);
        }
        return orderDetailsList;
    }

    @Override
    public boolean updateOrderDetails(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE order_details SET order_id=?, product_id=?, quantity=?, total_price=? WHERE orderdetail_id=?",
                orderDetailsDTO.getOrder_id(),
                orderDetailsDTO.getProduct_id(),
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getTotal_price(),
                orderDetailsDTO.getOrderDetail_id()
        );
    }

    @Override
    public boolean deleteOrderDetails(String orderId) throws SQLException {
        return CrudUtil.execute("DELETE FROM order_details WHERE order_id=?", orderId);
    }

    @Override
    public OrderDetailsDTO findById(String selectedOrderId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM order_details WHERE order_id=?", selectedOrderId);

        if (rst.next()) {
            return new OrderDetailsDTO(
                    rst.getString("orderdetail_id"),
                    rst.getString("order_id"),
                    rst.getString("order_date"),
                    rst.getInt("quantity"),
                    rst.getDouble("total_price")
            );
        }
        return null;
    }
}
