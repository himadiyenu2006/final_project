package model;

import Util.CrudUtil;
import lk.ijse.gdse.pizzahubsystem.dto.EmployeeDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {

    public static boolean addEmployee(EmployeeDTO newEmployee) {
        return false;
    }

    public String getNextEmployeeId() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select employee_id from employee order by employee_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("E%03d", newIdIndex);
        }
        return "E001";
    }

    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "insert into employee(employee_id, position , hire_date , department_id , name) values (?,?,?,?,?)",
                employeeDTO.getEmployee_id(),
                employeeDTO.getPosition(),
                employeeDTO.getHire_date(),
                employeeDTO.getDepartment_id(),
                employeeDTO.getName()

        );
    }


    public ArrayList<EmployeeDTO> getAllEmployees() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select * from employee");

        ArrayList<EmployeeDTO> employeeDTOS = new ArrayList<>();

        while (rst.next()) {
            EmployeeDTO employeeDTO = new EmployeeDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getString(4),
                    rst.getString(5)

            );
            employeeDTOS.add(employeeDTO);
        }
        return employeeDTOS;
    }
    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return CrudUtil.execute(
                "update employee set name=?, department_id=?, position=? , hire_date=? where employee_id=?",
                employeeDTO.getName(),
                employeeDTO.getEmployee_id(),
                employeeDTO.getPosition(),
                employeeDTO.getHire_date(),
                employeeDTO.getDepartment_id()



        );
    }

    public boolean deleteEmployee(String employeeId) throws SQLException {
        return Util.CrudUtil.execute("delete from employee where employee_id=?", employeeId);
    }

    public EmployeeDTO findById(String selectedEmpId) throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select * from employee where employee_id=?", selectedEmpId);

        if (rst.next()) {
            return new EmployeeDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }

    public boolean deleteEmployee(Integer employeeId) {
        return false;
    }
}
