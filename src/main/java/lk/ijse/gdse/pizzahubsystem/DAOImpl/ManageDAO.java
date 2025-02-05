package lk.ijse.gdse.pizzahubsystem.DAOImpl;

import lk.ijse.gdse.pizzahubsystem.dto.ManageDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ManageDAO {
    boolean saveManageDetails(ManageDTO manageDTO) throws SQLException;
    boolean saveManage(ManageDTO manageDTO) throws SQLException;
    ArrayList<ManageDTO> getAllManageRecords() throws SQLException;
}
