package model;

import Util.CrudUtil;
import lk.ijse.gdse.pizzahubsystem.dto.DepartmentDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DepartmentModel {

    public static boolean save(DepartmentDTO dto) throws SQLException {
/*        String query = "INSERT INTO department (department_id, department_name, description, manager_name, num_employees) VALUES (?, ?, ?, ?, ?)";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, dto.getDepartment_id());
            preparedStatement.setString(2, dto.getDepartment_name());
            preparedStatement.setString(3,dto.getManager_name() );
            preparedStatement.setString(4, dto.getNumber_of_employees());
            preparedStatement.setString(5, dto.getDescription());

            return preparedStatement.executeUpdate() > 0;*/

        return CrudUtil.execute(
                "INSERT INTO department VALUES (?, ?, ?, ?, ?)",
                dto.getDepartment_id(),
                dto.getDepartment_name(),
                dto.getManager_name(),
                dto.getNumber_of_employees(),dto.getDescription()
        );



    }


    public static String getNextDepartmentId() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select department_id from department order by department_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            int newIdIndex = Integer.parseInt(lastId) + 1;
            return String.valueOf(newIdIndex);
        }
        return "1";
    }

    public boolean saveDepartment(DepartmentDTO departmentDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "insert into department(Department_id, Department_name,Manager_name,Number_of_employees,Description) values (?, ? , ? , ? , ?)",
                departmentDTO.getDepartment_id(),
                departmentDTO.getDepartment_name(),
                departmentDTO.getManager_name(),
                departmentDTO.getNumber_of_employees(),
                departmentDTO.getDescription()
        );
    }

    public ArrayList<DepartmentDTO> getAllDepartments() throws SQLException {
       /* ResultSet rst = Util.CrudUtil.execute("select * from department");

        ArrayList<DepartmentDTO> departmentDTOS = new ArrayList<>();

        while (rst.next()) {
            DepartmentDTO departmentDTO = new DepartmentDTO(
                    rst.getInt(1),
                    rst.getString(2)
            );
            departmentDTOS.add(departmentDTO);
        }
        return departmentDTOS;*/
        return null;
    }

    public boolean updateDepartment(DepartmentDTO departmentDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "update department set name=? where department_id=?",
                departmentDTO.getDepartment_name(),
                departmentDTO.getDepartment_id()
        );
    }


    public boolean deleteDepartment(int departmentId) throws SQLException {
        return Util.CrudUtil.execute("delete from department where department_id=?", departmentId);
    }

    public DepartmentDTO findById(int departmentId) throws SQLException {
       /* ResultSet rst = Util.CrudUtil.execute("select * from department where department_id=?", departmentId);

        if (rst.next()) {
            return new DepartmentDTO(
                    rst.getInt(1),
                    rst.getString(2)
            );
        }
        return null;*/
        return null;
    }

    public boolean delete(String text) {
        return Util.CrudUtil.execute();
    }
}


