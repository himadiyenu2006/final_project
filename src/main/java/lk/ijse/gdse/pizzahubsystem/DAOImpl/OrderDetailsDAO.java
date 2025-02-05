package lk.ijse.gdse.pizzahubsystem.DAOImpl;

import lk.ijse.gdse.pizzahubsystem.dto.OrderDetailsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsDAO {
    String getNextOrderId() throws SQLException;
    boolean saveOrderDetails(OrderDetailsDTO orderDetailsDTO) throws SQLException;
    ArrayList<OrderDetailsDTO> getAllOrderDetails() throws SQLException;
    boolean updateOrderDetails(OrderDetailsDTO orderDetailsDTO) throws SQLException;
    boolean deleteOrderDetails(String orderId) throws SQLException;
    OrderDetailsDTO findById(String selectedOrderId) throws SQLException;
}
