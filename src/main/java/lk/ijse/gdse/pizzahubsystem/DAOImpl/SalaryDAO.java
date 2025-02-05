package lk.ijse.gdse.pizzahubsystem.DAOImpl;

import lk.ijse.gdse.pizzahubsystem.dto.SalaryDTO;
import lk.ijse.gdse.pizzahubsystem.dto.tm.SalaryTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SalaryDAO {

    boolean save(SalaryDTO salaryDTO) throws SQLException;

    boolean calculateNetSalary(SalaryDTO salaryDTO) throws SQLException;

    ArrayList<SalaryTM> getAllSalary() throws SQLException;

    SalaryDTO findByEmployeeId(String employeeId) throws SQLException;
}
