package lk.ijse.gdse.pizzahubsystem.DAOImpl;

import Util.CrudUtil;
import lk.ijse.gdse.pizzahubsystem.db.DBConnection;
import lk.ijse.gdse.pizzahubsystem.dto.ManageDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageDAOImpl implements ManageDAO {

    private final Connection connection;

    public ManageDAOImpl() throws SQLException {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean saveManageDetails(ManageDTO manageDTO) throws SQLException {
        String sql = "INSERT INTO manage (manage_id, inventory_id, supplier_id, order_id, quantity, supplier_name, supplier_contact_name) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, manageDTO.getManageId());
            preparedStatement.setString(2, manageDTO.getInventoryId());
            preparedStatement.setString(3, manageDTO.getSupplierId());
            preparedStatement.setString(4, manageDTO.getOrderId());
            preparedStatement.setInt(5, manageDTO.getQuantity());
            preparedStatement.setString(6, manageDTO.getSupplierName());
            preparedStatement.setString(7, manageDTO.getSupplierContactName());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveManage(ManageDTO manageDTO) throws SQLException {
        String sql = "INSERT INTO manage (manage_id, inventory_id, supplier_id, order_id, quantity, supplier_name, supplier_contact_name) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql,
                manageDTO.getManageId(),
                manageDTO.getInventoryId(),
                manageDTO.getSupplierId(),
                manageDTO.getOrderId(),
                manageDTO.getQuantity(),
                manageDTO.getSupplierName(),
                manageDTO.getSupplierContactName());
    }

    @Override
    public ArrayList<ManageDTO> getAllManageRecords() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM manage");

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
