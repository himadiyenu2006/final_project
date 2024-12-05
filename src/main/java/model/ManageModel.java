package model;

import lk.ijse.gdse.pizzahubsystem.db.DBConnection;
import lk.ijse.gdse.pizzahubsystem.dto.ManageDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageModel {

    private final Connection connection;

    public ManageModel() throws SQLException {
        connection = DBConnection.getInstance().getConnection();
    }


    public boolean saveManageDetails(ManageDTO manageDTO) {
        String sql = "INSERT INTO manage (manage_id, inventory_id, supplier_id , order_id, quantity, supplier_name ,supplier_contact_name ,  " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(2, manageDTO.getManageId());
            preparedStatement.setString(1, manageDTO.getInventoryId());
            preparedStatement.setString(6, manageDTO.getSupplierId());
            preparedStatement.setString(3, manageDTO.getOrderId());
            preparedStatement.setInt(4, manageDTO.getQuantity());
            preparedStatement.setString(7, manageDTO.getSupplierName());
            preparedStatement.setString(5, manageDTO.getSupplierContactName());


            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveManage(ManageDTO manageDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "insert into manage (manage_id, inventory_id, supplier_id, order_id, quantity , supplier_name , supplier_contact_name) values (?,?,?,?,?,?,?)",
                manageDTO.getManageId(),
                manageDTO.getInventoryId(),
                manageDTO.getSupplierId(),
                manageDTO.getOrderId(),
                manageDTO.getQuantity(),
                manageDTO.getSupplierName(),
                manageDTO.getSupplierContactName()
        );
    }


    public ArrayList<ManageDTO> getAllManageRecords() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("SELECT * FROM manage");

        ArrayList<ManageDTO> manageDTOs = new ArrayList<>();

        while (rst.next()) {
            ManageDTO manageDTO = new ManageDTO(
                    rst.getString("manage_id"),
                    rst.getString("inventory_id"),
                    rst.getString("supplier_id"),
                    rst.getString("order_id"),
                    rst.getInt("quantity"),
                    rst.getString("supplier_name"),
                    rst.getString("supplier_contact_name")

            );
            manageDTOs.add(manageDTO);
        }
        return manageDTOs;
    }

}
