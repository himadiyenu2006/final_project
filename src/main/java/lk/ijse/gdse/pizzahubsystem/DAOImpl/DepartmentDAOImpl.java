package lk.ijse.gdse.pizzahubsystem.DAOImpl;

import dao.DepartmentDAO;
import lk.ijse.gdse.pizzahubsystem.dto.DepartmentDTO;
import Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DepartmentDAOImpl implements DepartmentDAO {

    @Override
    public boolean save(DepartmentDTO dto) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO department (department_id, department_name, manager_name, number_of_employees, description) VALUES (?, ?, ?, ?, ?)",
                dto.getDepartment_id(),
                dto.getDepartment_name(),
                dto.getManager_name(),
                dto.getNumber_of_employees(),
                dto.getDescription()
        );
    }

    @Override
    public String getNextDepartmentId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT department_id FROM department ORDER BY department_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            int newIdIndex = Integer.parseInt(lastId) + 1;
            return String.valueOf(newIdIndex);
        }
        return "1";
    }

    @Override
    public ArrayList<DepartmentDTO> getAllDepartments() throws SQLException {
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
        }
        return departmentList;
    }

    @Override
    public boolean updateDepartment(DepartmentDTO departmentDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE department SET department_name=?, manager_name=?, number_of_employees=?, description=? WHERE department_id=?",
                departmentDTO.getDepartment_name(),
                departmentDTO.getManager_name(),
                departmentDTO.getNumber_of_employees(),
                departmentDTO.getDescription(),
                departmentDTO.getDepartment_id()
        );
    }

    @Override
    public boolean deleteDepartment(String departmentId) throws SQLException {
        return CrudUtil.execute("DELETE FROM department WHERE department_id=?", departmentId);
    }

    @Override
    public DepartmentDTO findById(String departmentId) throws SQLException {
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

    @Override
    public ArrayList<String> getAllDepartmentIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT department_id FROM department");

        ArrayList<String> departmentIds = new ArrayList<>();
        while (rst.next()) {
            departmentIds.add(rst.getString(1));
        }
        return departmentIds;
    }
}
