package dao;

import lk.ijse.gdse.pizzahubsystem.dto.DepartmentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DepartmentDAO {
    boolean save(DepartmentDTO dto) throws SQLException;
    String getNextDepartmentId() throws SQLException;
    ArrayList<DepartmentDTO> getAllDepartments() throws SQLException;
    boolean updateDepartment(DepartmentDTO departmentDTO) throws SQLException;
    boolean deleteDepartment(String departmentId) throws SQLException;
    DepartmentDTO findById(String departmentId) throws SQLException;
    ArrayList<String> getAllDepartmentIds() throws SQLException;
}
