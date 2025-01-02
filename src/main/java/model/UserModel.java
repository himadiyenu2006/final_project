package model;

import lk.ijse.gdse.pizzahubsystem.dto.UserDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserModel {

    private Util.CrudUtil CrudUtil;

    public int getNextUserId() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("SELECT user_id FROM user ORDER BY user_id DESC LIMIT 1");

        if (rst.next()) {
            int lastId = rst.getInt(1);
            return lastId + 1;
        }
        return 1;
    }

    public boolean saveUser(UserDTO userDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "INSERT INTO user(user_id, username, password,email, role) VALUES (?,?,?,?,?)",
                userDTO.getUser_id(),
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                userDTO.getRole()
        );
    }

    public static ArrayList<UserDTO> getAllUsers() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("SELECT * FROM user");

        ArrayList<UserDTO> userDTOS = new ArrayList<>();

        while (rst.next()) {
            UserDTO userDTO = new UserDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    public boolean updateUser(UserDTO userDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "UPDATE user SET username=?, password=?, email=? , role=? WHERE user_id=?",
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                userDTO.getRole(),
                userDTO.getUser_id()
        );
    }

    public boolean deleteUser(int userId) throws SQLException {
        return Util.CrudUtil.execute("DELETE FROM user WHERE user_id=?", userId);
    }

    public ArrayList<UserDTO> getAllUserIds() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("SELECT user_id FROM user");

        ArrayList<Integer> userIds = new ArrayList<>();

        while (rst.next()) {
            userIds.add(rst.getInt(1));
        }

        return getAllUserIds();
    }

    public UserDTO findById(int selectedUserId) throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("SELECT * FROM user WHERE user_id=?", selectedUserId);

        if (rst.next()) {
            return new UserDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }
}
