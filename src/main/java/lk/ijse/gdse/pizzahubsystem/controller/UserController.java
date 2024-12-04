package lk.ijse.gdse.pizzahubsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.pizzahubsystem.dto.tm.UserTM;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserController {

    @FXML
    private AnchorPane userpane;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<UserTM, String>  colCustomerId;

    @FXML
    private TableColumn<UserTM, String> colEmail;

    @FXML
    private TableColumn<UserTM, String> colName;

    @FXML
    private  TableColumn<UserTM, String>  colPassword;

    @FXML
    private  TableColumn<UserTM, String> colRole;

    @FXML
    private Label lblUserId;

    @FXML
    private TableView<UserTM> tblUser;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtRole;


    private boolean validateInputs() {
        if (txtName.getText().isEmpty()) {
            showAlert("Validation Error", "Name field cannot be empty.");
            return false;
        }

        if (txtEmail.getText().isEmpty()) {
            showAlert("Validation Error", "Email field cannot be empty.");
            return false;
        } else if (!txtEmail.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showAlert("Validation Error", "Please enter a valid email address.");
            return false;
        }

        if (txtPassword.getText().isEmpty()) {
            showAlert("Validation Error", "Password field cannot be empty.");
            return false;
        } else if (txtPassword.getText().length() < 6) {
            showAlert("Validation Error", "Password must be at least 6 characters long.");
            return false;
        }

        if (txtRole.getText().isEmpty()) {
            showAlert("Validation Error", "Role field cannot be empty.");
            return false;
        }

        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/pizzahubsystemdb";
        String username = "root";
        String password = "1234";

        return DriverManager.getConnection(url, username, password);
    }

    private void executeTransaction(String sql, Object[] params) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                connection.commit();
                showAlert("Success", "Operation completed successfully.");
            } else {
                connection.rollback();
                showAlert("Error", "Operation failed. No changes were made.");
            }
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
            showAlert("Error", "An error occurred while processing the transaction.");
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btnSaveOnAction() {
        if (validateInputs()) {
            String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
            Object[] params = {txtName.getText(), txtEmail.getText(), txtPassword.getText(), txtRole.getText()};
            executeTransaction(sql, params);
        }
    }

    @FXML
    void btnUpdateOnAction() {
        if (validateInputs()) {
            String sql = "UPDATE users SET name = ?, email = ?, password = ?, role = ? WHERE user_id = ?";
            Object[] params = {txtName.getText(), txtEmail.getText(), txtPassword.getText(), txtRole.getText(), lblUserId.getText()};
            executeTransaction(sql, params);
        }
    }

    @FXML
    void btnDeleteOnAction() {
        String sql = "DELETE FROM users WHERE user_id = ?";
        Object[] params = {lblUserId.getText()};
        executeTransaction(sql, params);
    }
}
