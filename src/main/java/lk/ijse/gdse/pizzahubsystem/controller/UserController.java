package lk.ijse.gdse.pizzahubsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.pizzahubsystem.dto.UserDTO;
import lk.ijse.gdse.pizzahubsystem.dto.tm.UserTM;
import model.UserModel;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.sql.DriverManager.getConnection;

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
    private TableColumn<UserTM, String> colUserId;

    @FXML
    private TableColumn<UserTM, String> colEmail;

    @FXML
    private TableColumn<UserTM, String> colName;

    @FXML
    private TableColumn<UserTM, String> colPassword;

    @FXML
    private TableColumn<UserTM, String> colRole;

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

    private UserModel userModel = new UserModel();

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("user_Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        try {
            loadTableData();
        } catch (SQLException e) {
            showAlert("Error", "Error loading users: " + e.getMessage());
        }
    }

    private void loadTableData() throws SQLException {
        ObservableList<UserTM> usersList = FXCollections.observableArrayList();
        ArrayList<UserDTO> userDTOList = userModel.getAllUsers();

        for (UserDTO user : userDTOList) {
            usersList.add(new UserTM(user.getUser_id(), user.getUsername(), user.getEmail(), user.getPassword(), user.getRole()));
        }

        tblUser.setItems(usersList);
    }

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

    private void executeTransaction(String sql, Object[] params) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection("jdbc:mysql://localhost:3306/pizzahubsystemdb");
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
            try {
                loadTableData();
            } catch (SQLException e) {
                showAlert("Error", "Error loading users: " + e.getMessage());
            }
        }
    }

    @FXML
    void btnUpdateOnAction() {
        if (validateInputs()) {
            String sql = "UPDATE users SET name = ?, email = ?, password = ?, role = ? WHERE user_id = ?";
            Object[] params = {txtName.getText(), txtEmail.getText(), txtPassword.getText(), txtRole.getText(), lblUserId.getText()};
            executeTransaction(sql, params);
            try {
                loadTableData();
            } catch (SQLException e) {
                showAlert("Error", "Error loading users: " + e.getMessage());
            }
        }
    }

    @FXML
    void btnDeleteOnAction() {
        try {
            String sql = "DELETE FROM users WHERE user_id = ?";
            Object[] params = {lblUserId.getText()};
            executeTransaction(sql, params);
            loadTableData();
        } catch (SQLException e) {
            showAlert("Error", "Error deleting user: " + e.getMessage());
        }
    }

    private void clearFields() {
        lblUserId.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        txtRole.setText("");
        txtName.setText("");
    }
}

//package lk.ijse.gdse.pizzahubsystem.controller;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.control.TableView;
//import javafx.scene.control.*;
//import javafx.scene.layout.AnchorPane;
//import lk.ijse.gdse.pizzahubsystem.dto.UserDTO;
//import lk.ijse.gdse.pizzahubsystem.dto.tm.CustomerTM;
//import lk.ijse.gdse.pizzahubsystem.dto.tm.UserTM;
//import model.CustomerModel;
//import model.UserModel;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static java.sql.DriverManager.getConnection;
//
//public class UserController {
//
//    @FXML
//    private AnchorPane userpane;
//
//    @FXML
//    private Button btnDelete;
//
//    @FXML
//    private Button btnSave;
//
//    @FXML
//    private Button btnUpdate;
//
//    @FXML
//    private TableColumn<UserTM, String>  colCustomerId;
//
//    @FXML
//    private TableColumn<UserTM, String> colEmail;
//
//    @FXML
//    private TableColumn<UserTM, String> colName;
//
//    @FXML
//    private  TableColumn<UserTM, String>  colPassword;
//
//    @FXML
//    private  TableColumn<UserTM, String> colRole;
//
//    @FXML
//    private Label lblUserId;
//
//    @FXML
//    private TableView<UserDTO> tblUser;
//
//    @FXML
//    private TextField txtEmail;
//
//    @FXML
//    private TextField txtName;
//
//    @FXML
//    private TextField txtPassword;
//
//    @FXML
//    private TextField txtRole;
//
//
//    private boolean validateInputs() {
//        if (txtName.getText().isEmpty()) {
//            showAlert("Validation Error", "Name field cannot be empty.");
//            return false;
//        }
//
//        if (txtEmail.getText().isEmpty()) {
//            showAlert("Validation Error", "Email field cannot be empty.");
//            return false;
//        } else if (!txtEmail.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
//            showAlert("Validation Error", "Please enter a valid email address.");
//            return false;
//        }
//
//        if (txtPassword.getText().isEmpty()) {
//            showAlert("Validation Error", "Password field cannot be empty.");
//            return false;
//        } else if (txtPassword.getText().length() < 6) {
//            showAlert("Validation Error", "Password must be at least 6 characters long.");
//            return false;
//        }
//
//        if (txtRole.getText().isEmpty()) {
//            showAlert("Validation Error", "Role field cannot be empty.");
//            return false;
//        }
//
//        return true;
//    }
//
//    private void showAlert(String title, String message) {
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//
//    private void executeTransaction(String sql, Object[] params) {
//        Connection connection = null;
//        PreparedStatement statement = null;
//
//        try {
//            connection = getConnection();
//            connection.setAutoCommit(false);
//
//            statement = connection.prepareStatement(sql);
//            for (int i = 0; i < params.length; i++) {
//                statement.setObject(i + 1, params[i]);
//            }
//
//            int affectedRows = statement.executeUpdate();
//            if (affectedRows > 0) {
//                connection.commit();
//                showAlert("Success", "Operation completed successfully.");
//            } else {
//                connection.rollback();
//                showAlert("Error", "Operation failed. No changes were made.");
//            }
//        } catch (SQLException e) {
//            try {
//                if (connection != null) {
//                    connection.rollback();
//                }
//            } catch (SQLException rollbackException) {
//                rollbackException.printStackTrace();
//            }
//            e.printStackTrace();
//            showAlert("Error", "An error occurred while processing the transaction.");
//        } finally {
//            try {
//                if (statement != null) statement.close();
//                if (connection != null) connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void loadAllUsers() {
//        try {
//            ArrayList<UserDTO> userDTOS = userModel.getAllUsers();
//            ObservableList<UserDTO> users = FXCollections.observableArrayList(userDTOS);
//            tblUser.setItems(users);
//        } catch (SQLException e) {
//            showAlert(Alert.AlertType.ERROR, "SQL Error", "Error while loading users: " + e.getMessage());
//        }
//    }
//
//    private void getNextUserId(){
//
//    }
//
//    private void deleteUser(){
//
//    }
//    @FXML
//    void btnSaveOnAction() {
//        if (validateInputs()) {
//            String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
//            Object[] params = {txtName.getText(), txtEmail.getText(), txtPassword.getText(), txtRole.getText()};
//            executeTransaction(sql, params);
//        }
//    }
//
//    @FXML
//    void btnUpdateOnAction() {
//        if (validateInputs()) {
//            String sql = "UPDATE users SET name = ?, email = ?, password = ?, role = ? WHERE user_id = ?";
//            Object[] params = {txtName.getText(), txtEmail.getText(), txtPassword.getText(), txtRole.getText(), lblUserId.getText()};
//            executeTransaction(sql, params);
//        }
//    }
//
//    @FXML
//    void btnDeleteOnAction() {
//        String sql = "DELETE FROM users WHERE user_id = ?";
//        Object[] params = {lblUserId.getText()};
//        executeTransaction(sql, params);
//    }
//
//    private void refreshPage() throws SQLException {
//        loadNextUserId();
//        loadTableData();
//
//
//        btnSave.setDisable(false);
//
//        btnDelete.setDisable(false);
//        btnUpdate.setDisable(false);
//
//        lblUserId.setText("");
//        txtEmail.setText("");
//        txtPassword.setText("");
//        txtRole.setText("");
//        txtName.setText("");
//        private void loadTableData() throws SQLException {
//            ObservableList<UserTM> addUser = FXCollections.observableArrayList();
//
//            ArrayList<UserDTO> list = UserModel.getAllUsers();
//
//            for (UserDTO userTM : list) {
//                System.out.println("qwertyu");
//                addUser.add(userTM);
//            }
//            tblUser.setItems(addUser);
//        }
//
//        UserModel userModel = new UserModel();
//
//    }
//}
