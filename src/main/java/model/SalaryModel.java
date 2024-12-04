package model;

import lk.ijse.gdse.pizzahubsystem.dto.SalaryDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalaryModel {

    private Util.CrudUtil CrudUtil;

    public static boolean save(SalaryDTO salaryDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "insert into salary(salary_id ,employee_id,basic_salary , bonus , deductions , net_salary , salary_date) values (?,?,?.?.?.?.?)",
                salaryDTO.getSalary_id(),
                salaryDTO.getEmployee_id(),
                salaryDTO.getBasic_salary(),
                salaryDTO.getBonus(),
                salaryDTO.getDeductions(),
                salaryDTO.getNet_salary(),
                salaryDTO.getSalary_date()
        );
    }

    public ArrayList<SalaryDTO> getAllSalaries() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select * from salary");

        ArrayList<SalaryDTO> salaryDTOS = new ArrayList<>();

        while (rst.next()) {
            SalaryDTO salaryDTO = new SalaryDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getDouble(6),
                    rst.getString(7)

            );
            salaryDTOS.add(salaryDTO);
        }
        return salaryDTOS;
    }

    public boolean updateSalary(SalaryDTO salaryDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "update salary set Salary_amount=?,date=? where salary_id=?",
                salaryDTO.getEmployee_id(),
                salaryDTO.getBasic_salary(),
                salaryDTO.getBonus(),
                salaryDTO.getDeductions(),
                salaryDTO.getNet_salary(),
                salaryDTO.getSalary_date(),
                salaryDTO.getSalary_id()
        );
    }


    public boolean deleteSalary(int employeeId) throws SQLException {
        return Util.CrudUtil.execute("delete from salary where employee_id=?", employeeId);
    }


    public SalaryDTO findByEmployeeId(int employeeId) throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select * from salary where employee_id=?", employeeId);

        if (rst.next()) {
            return new SalaryDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getDouble(6),
                    rst.getString(7)
            );
        }
        return null;
    }
}
