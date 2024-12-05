package lk.ijse.gdse.pizzahubsystem.controller;

import lk.ijse.gdse.pizzahubsystem.db.DBConnection;
import lk.ijse.gdse.pizzahubsystem.dto.DeliveryDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeliveryService {

    public boolean saveDelivery(DeliveryDTO deliveryDTO) throws SQLException {
        String query = "INSERT INTO delivery(delivery_id, order_id, delivery_address, delivery_date, delivery_status, employee_id) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(query)) {
            stmt.setString(1, deliveryDTO.getDelivery_id());
            stmt.setString(2, deliveryDTO.getOrder_id());
            stmt.setString(3, deliveryDTO.getDelivery_address());
            stmt.setDate(4, deliveryDTO.getDelivery_date());
            stmt.setString(5, deliveryDTO.getDelivery_status());
            stmt.setString(6, deliveryDTO.getEmployee_id());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateDelivery(DeliveryDTO deliveryDTO) throws SQLException, SQLException {
        String query = "UPDATE delivery SET order_id=?, delivery_address=?, delivery_date=?, delivery_status=?, employee_id=? WHERE delivery_id=?";
        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(query)) {
            stmt.setString(1, deliveryDTO.getOrder_id());
            stmt.setString(2, deliveryDTO.getDelivery_address());
            stmt.setDate(3, deliveryDTO.getDelivery_date());
            stmt.setString(4, deliveryDTO.getDelivery_status());
            stmt.setString(5, deliveryDTO.getEmployee_id());
            stmt.setString(6, deliveryDTO.getDelivery_id());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteDelivery(int deliveryId) throws SQLException {
        String query = "DELETE FROM delivery WHERE delivery_id=?";
        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(query)) {
            stmt.setInt(1, deliveryId);
            return stmt.executeUpdate() > 0;
        }
    }
}

