package lk.ijse.gdse.pizzahubsystem.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;

    public DBConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/pizzahubsystemdb";
        String user = "root";
        String password = "1234";
        connection = DriverManager.getConnection(url, user, password);
    }

    public static DBConnection getInstance() throws SQLException {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    public static Connection getConnection() throws SQLException {
        return getInstance().connection;
    }
}
