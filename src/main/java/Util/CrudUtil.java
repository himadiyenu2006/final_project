package Util;

import lk.ijse.gdse.pizzahubsystem.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CrudUtil {

    public static <T> T execute(String sql, Object... obj) throws SQLException {
        System.out.println("rffff");
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);

        for (int i = 0; i < obj.length; i++) {
            pst.setObject(i + 1, obj[i]);
        }

        if (sql.trim().toUpperCase().startsWith("SELECT")) {
            ResultSet resultSet = pst.executeQuery();
            return (T) resultSet;
        } else {
            int rowsAffected = pst.executeUpdate();
            boolean isSuccessful = rowsAffected > 0;
            return (T) Boolean.valueOf(isSuccessful);
        }
    }

    public static boolean execute() {
        return true;
    }
}
