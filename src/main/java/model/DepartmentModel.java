package model;

import Util.CrudUtil;
import lk.ijse.gdse.pizzahubsystem.dto.DepartmentDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DepartmentModel {

    public static boolean save(DepartmentDTO dto) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO department (department_id, department_name, manager_name, number_of_employees, description) VALUES (?, ?, ?, ?, ?)",
                dto.getDepartment_id(),
                dto.getDepartment_name(),
                dto.getManager_name(),
                dto.getNumber_of_employees(),
                dto.getDescription()
        );
    }

    public static String getNextDepartmentId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT department_id FROM department ORDER BY department_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            int newIdIndex = Integer.parseInt(lastId) + 1;
            return String.valueOf(newIdIndex);
        }
        return "1";
    }

    public static ArrayList<DepartmentDTO> getAllDepartments() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM department");

        ArrayList<DepartmentDTO> departmentList = new ArrayList<>();
        while (rst.next()) {
            departmentList.add(new DepartmentDTO(
                    rst.getString("department_id"),
                    rst.getString("department_name"),
                    rst.getString("manager_name"),
                    rst.getInt("number_of_employees"),
                    rst.getString("description")
            ));
            System.out.println(departmentList.size());
        }
        return departmentList;
    }

    public static boolean updateDepartment(DepartmentDTO departmentDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE department SET department_name=?, manager_name=?, number_of_employees=?, description=? WHERE department_id=?",
                departmentDTO.getDepartment_name(),
                departmentDTO.getManager_name(),
                departmentDTO.getNumber_of_employees(),
                departmentDTO.getDescription(),
                departmentDTO.getDepartment_id()
        );
    }

    public static boolean deleteDepartment(String departmentId) throws SQLException {
        return CrudUtil.execute("DELETE FROM department WHERE department_id=?", departmentId);
    }

    public static DepartmentDTO findById(String departmentId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM department WHERE department_id=?", departmentId);

        if (rst.next()) {
            return new DepartmentDTO(
                    rst.getString("department_id"),
                    rst.getString("department_name"),
                    rst.getString("manager_name"),
                    rst.getInt("number_of_employees"),
                    rst.getString("description")
            );
        }
        return null;
    }

    public static ArrayList<String> getAllDepartmentIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT department_id FROM department");

        ArrayList<String> departmentIds = new ArrayList<>();
        while (rst.next()) {
            departmentIds.add(rst.getString(1));
        }
        return departmentIds;
    }

    public boolean delete(String text) {
        return (true);
    }
}
