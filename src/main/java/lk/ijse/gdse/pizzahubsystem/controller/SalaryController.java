package lk.ijse.gdse.pizzahubsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import lk.ijse.gdse.pizzahubsystem.dto.SalaryDTO;
import lk.ijse.gdse.pizzahubsystem.dto.tm.SalaryTM;
import model.SalaryModel;

import java.sql.SQLException;

public class SalaryController {



    public Label baseSalary;
    public TextField baseSalaryField;

    @FXML
    private TextField deductionsField;

    @FXML
    private TextField employeeIdField;

    @FXML
    private TextField netSalaryField;

    @FXML
    private TextField salaryIdField;

    @FXML
    private TextField salary_date;

    @FXML
    private TextField bonusField;

    @FXML
    private ImageView salarymanagementimage;

    @FXML
    private TableView<?> tblsalaryid;

    @FXML
    private TableColumn<?, ?> colEmp_id;

    @FXML
    private TableColumn<SalaryTM, String> colSalaryDate;

    @FXML
    private TableColumn<SalaryTM, String> colSalaryId;

    @FXML
    private TableColumn<SalaryTM, String> colempbasesalary;

    @FXML
    private TableColumn<SalaryTM, String> colempbonus;

    @FXML
    private TableColumn<SalaryTM, String> colempdeductions;

    @FXML
    private TableColumn<SalaryTM, String> colempname;

    @FXML
    private TableColumn<SalaryTM, String> colempnetsalary;

/*
    public SalaryController(DBConnection databaseConnection) throws IOException {
        this.connection = databaseConnection.getConnection();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/SalaryView.fxml"));
        SalaryController controller = null; // Pass in your DBConnection instance here
        try {
            controller = new SalaryController(new DBConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loader.setController(controller);
        Parent root = loader.load();
    }
*/

    @FXML
    private void calculateNetSalary() {
            try {
                double baseSalary = Double.parseDouble(baseSalaryField.getText());
                double bonus = Double.parseDouble(bonusField.getText());
                double deductions = Double.parseDouble(deductionsField.getText());
                double netSalary = baseSalary + bonus - deductions;
                netSalaryField.setText(String.valueOf(netSalary));
            } catch (NumberFormatException e) {
                new Alert(AlertType.ERROR,"Invalid input Please enter valid numbers for base salary, bonus, and deductions.").show();
            }
        }


    @FXML
    private void saveSalary() throws SQLException {
        String colSalaryId = salaryIdField.getText();
        String colEmp_id = employeeIdField.getText();
        double baseSalaryFieldText = Double.parseDouble(baseSalary.getText());
        double bonusField1 = Double.parseDouble(bonusField.getText());
        double deductionsField1 = Double.parseDouble(deductionsField.getText());
        double netSalaryFieldText = Double.parseDouble(netSalaryField.getText());
        String date = salary_date.getText();


        SalaryDTO salaryDTO = new SalaryDTO(colSalaryId, colEmp_id, baseSalaryFieldText, bonusField1, deductionsField1, netSalaryFieldText, date);
        boolean issaved = SalaryModel.save(salaryDTO);
        if (issaved) {
            new Alert(AlertType.INFORMATION, "Salary saved successfully!").show();
        } else {
            new Alert(AlertType.ERROR, "Failed to save Salary.").show();
        }
    }
//    private boolean isValidInput() {
//        if (employeeIdField.getText().isEmpty() || employeeNameField.getText().isEmpty()) {
//            showAlert("Missing Information", "Employee ID and Name are required.");
//            return false;
//        }
//
//        if (baseSalaryField.getText().isEmpty() || bonusField.getText().isEmpty() || deductionsField.getText().isEmpty()) {
//            showAlert("Missing Information", "Base Salary, Bonus, and Deductions must be provided.");
//            return false;
//        }
//
//        try {
//            Double.parseDouble(baseSalaryField.getText());
//            Double.parseDouble(bonusField.getText());
//            Double.parseDouble(deductionsField.getText());
//        } catch (NumberFormatException e) {
//            showAlert("Invalid input", "Base Salary, Bonus, and Deductions must be valid numbers.");
//            return false;
//        }
//
//        return true;
//    }
//
//    private void showAlert(String title, String message) {
//        Alert alert = new Alert(AlertType.WARNING);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }

//    private void startTransaction() {
//        try {
//            connection.setAutoCommit(false);
//        } catch (SQLException e) {
//            showAlert("Error", "Failed to start transaction: " + e.getMessage());
//        }
//    }
//
//    private void commitTransaction() {
//        try {
//            connection.commit();
//            connection.setAutoCommit(true);
//        } catch (SQLException e) {
//            showAlert("Error", "Failed to commit transaction: " + e.getMessage());
//        }
//    }
//
//    private void rollbackTransaction() {
//        try {
//            connection.rollback();
//            connection.setAutoCommit(true);
//        } catch (SQLException e) {
//            showAlert("Error", "Failed to rollback transaction: " + e.getMessage());
//        }
//    }

    @FXML
    void calculateNetSalary(ActionEvent event) {

    }
}