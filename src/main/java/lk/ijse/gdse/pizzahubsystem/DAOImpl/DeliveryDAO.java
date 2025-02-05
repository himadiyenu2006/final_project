package dao;

import lk.ijse.gdse.pizzahubsystem.dto.DeliveryDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DeliveryDAO {
    ArrayList<DeliveryDTO> getAllDeliveries() throws SQLException;
    String getNextDeliveryId() throws SQLException;
    boolean saveDelivery(DeliveryDTO deliveryDTO) throws SQLException;
    boolean updateDelivery(DeliveryDTO deliveryDTO) throws SQLException;
    boolean deleteDelivery(String deliveryId) throws SQLException;
    DeliveryDTO findById(String deliveryId) throws SQLException;
}
