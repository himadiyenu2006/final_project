package lk.ijse.gdse.pizzahubsystem.DAOImpl;

import Util.CrudUtil;
import lk.ijse.gdse.pizzahubsystem.dto.EmployeeDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO employee (employee_id, position, hire_date, department_id, name) VALUES (?, ?, ?, ?, ?)",
                employeeDTO.getEmployee_id(),
                employeeDTO.getPosition(),
                employeeDTO.getHire_date(),
                employeeDTO.getDepartment_id(),
                employeeDTO.getName()
        );
    }

    @Override
    public String getNextEmployeeId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT employee_id FROM employee ORDER BY employee_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int newIdIndex = Integer.parseInt(substring) + 1;
            return String.format("E%03d", newIdIndex);
        }
        return "E001";
    }

    @Override
    public ArrayList<EmployeeDTO> getAllEmployees() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM employee");

        ArrayList<EmployeeDTO> employeeList = new ArrayList<>();
        while (rst.next()) {
            employeeList.add(new EmployeeDTO(
                    rst.getString("employee_id"),
                    rst.getString("position"),
                    rst.getDate("hire_date"),
                    rst.getString("department_id"),
                    rst.getString("name")
            ));
        }
        return employeeList;
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE employee SET name=?, department_id=?, position=?, hire_date=? WHERE employee_id=?",
                employeeDTO.getName(),
                employeeDTO.getDepartment_id(),
                employeeDTO.getPosition(),
                employeeDTO.getHire_date(),
                employeeDTO.getEmployee_id()
        );
    }

    @Override
    public boolean deleteEmployee(String employeeId) throws SQLException {
        return CrudUtil.execute("DELETE FROM employee WHERE employee_id=?", employeeId);
    }

    @Override
    public EmployeeDTO findById(String employeeId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM employee WHERE employee_id=?", employeeId);

        if (rst.next()) {
            return new EmployeeDTO(
                    rst.getString("employee_id"),
                    rst.getString("position"),
                    rst.getDate("hire_date"),
                    rst.getString("department_id"),
                    rst.getString("name")
            );
        }
        return null;
    }
}

