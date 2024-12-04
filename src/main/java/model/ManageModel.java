package model;

import lk.ijse.gdse.pizzahubsystem.db.DBConnection;
import lk.ijse.gdse.pizzahubsystem.dto.ManageDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ManageModel {

    private final Connection connection;

    public ManageModel() throws SQLException {
        connection = DBConnection.getInstance().getConnection();
    }


    public boolean saveManageDetails(ManageDTO manageDTO) {
        String sql = "INSERT INTO manage (inventory_id, manage_id, order_id, quantity, supplier_contact_name, supplier_id, supplier_name) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, manageDTO.getInventoryId());
            preparedStatement.setString(2, manageDTO.getManageId());
            preparedStatement.setString(3, manageDTO.getOrderId());
            preparedStatement.setInt(4, manageDTO.getQuantity());
            preparedStatement.setString(5, manageDTO.getSupplierContactName());
            preparedStatement.setString(6, manageDTO.getSupplierId());
            preparedStatement.setString(7, manageDTO.getSupplierName());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveManage(ManageDTO manageDTO) {
        return false;
    }

    public ManageDTO getAllManageRecords() {
        return null;
    }
}
