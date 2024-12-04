package lk.ijse.gdse.pizzahubsystem.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    public class DBConnection {
        private static DBConnection dbConnection;
        private Connection connection;
        public DBConnection() throws SQLException {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/pizzahubsystemdb",
                    "root",
                    "1234"
            );
        }
        public static DBConnection getInstance() throws SQLException {
            if (dbConnection==null){
                dbConnection=new DBConnection();
             }
            return dbConnection;
        }


        public Connection getConnection() {
            return connection;
        }

        public void setConnection(Connection connection) {
            this.connection = connection;
        }

    }