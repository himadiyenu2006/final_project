package model;

import lk.ijse.gdse.pizzahubsystem.dto.SalaryDTO;
import lk.ijse.gdse.pizzahubsystem.dto.tm.SalaryTM;
import Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalaryModel {

    public static boolean save(SalaryDTO salaryDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO salary (salary_id, employee_id, basic_salary, bonus, deductions, net_salary, salary_date) VALUES (?, ?, ?, ?, ?, ?, ?)",
                salaryDTO.getSalary_id(),
                salaryDTO.getEmployee_id(),
                salaryDTO.getBasic_salary(),
                salaryDTO.getBonus(),
                salaryDTO.getDeductions(),
                salaryDTO.getNet_salary(),
                salaryDTO.getSalary_date()
        );
    }

    public static boolean calculateNetSalary(SalaryDTO salaryDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE salary SET net_salary = ? WHERE salary_id = ?",
                salaryDTO.getNet_salary(),
                salaryDTO.getSalary_id()
        );
    }

    public static ArrayList<SalaryTM> getAllSalary() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM salary");
        ArrayList<SalaryTM> salaryList = new ArrayList<>();

        while (rst.next()) {
            SalaryTM salary = new SalaryTM(
                    rst.getString("salary_id"),
                    rst.getString("employee_id"),
                    rst.getDouble("basic_salary"),
                    rst.getDouble("bonus"),
                    rst.getDouble("deductions"),
                    rst.getDouble("net_salary"),
                    rst.getString("salary_date")
            );
            salaryList.add(salary);
        }
        return salaryList;
    }

    public static SalaryDTO findByEmployeeId(String employeeId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM salary WHERE employee_id = ?", employeeId);

        if (rst.next()) {
            return new SalaryDTO(
                    rst.getString("salary_id"),
                    rst.getString("employee_id"),
                    rst.getDouble("basic_salary"),
                    rst.getDouble("bonus"),
                    rst.getDouble("deductions"),
                    rst.getDouble("net_salary"),
                    rst.getString("salary_date")
            );
        }
        return null;
    }
}
