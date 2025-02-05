package lk.ijse.gdse.pizzahubsystem.DAOImpl;

import lk.ijse.gdse.pizzahubsystem.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeDAO {
    boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException;
    String getNextEmployeeId() throws SQLException;
    ArrayList<EmployeeDTO> getAllEmployees() throws SQLException;
    boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException;
    boolean deleteEmployee(String employeeId) throws SQLException;
    EmployeeDTO findById(String employeeId) throws SQLException;
}


