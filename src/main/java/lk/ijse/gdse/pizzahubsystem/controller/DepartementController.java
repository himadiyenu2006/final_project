package lk.ijse.gdse.pizzahubsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import lk.ijse.gdse.pizzahubsystem.dto.DepartmentDTO;
import lk.ijse.gdse.pizzahubsystem.dto.tm.DepartmentTM;
import model.DepartmentModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class DepartementController implements Initializable {

    public TableView departmentTableView;

    public TableColumn deptdescriptionColumn;

    @FXML
    private ImageView imageview;

    @FXML
    private TableView<DepartmentTM> tblDepartment;

    @FXML
    private TableColumn<DepartmentTM, String> deptNameColumn, managerNameColumn;
    @FXML
    private TableColumn<DepartmentTM, String> deptDescriptionColumn;

    @FXML
    private TableColumn<DepartmentTM, Integer> deptIdColumn, employeeCountColumn;

    @FXML
    private TextField departmentIdField, departmentNameField, managerNameField, numEmployeesField, descriptionArea;

    @FXML
    private Button save_btn, delete_btn, cancel_btn;

    private static final Pattern ID_PATTERN = Pattern.compile("^[A-Za-z0-9]{3,10}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z ]{3,30}$");
    private static final Pattern EMPLOYEE_COUNT_PATTERN = Pattern.compile("^\\d+$");
    private static final Pattern DESCRIPTION_PATTERN = Pattern.compile("^[\\w\\s,.-]{5,100}$");

    private final DepartmentModel departmentModel = new DepartmentModel();

    public DepartementController() throws IOException {
    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//       /* assert deptDescriptionColumn != null : "fx:id=\"deptDescriptionColumn\" was not injected: check your FXML file.";
//        deptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
//        initializeTable();
//        try {
//            refreshPage();
//        } catch (SQLException e) {
//            showAlert("Error", "Failed to load department data: " + e.getMessage());
//        }*/
//    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        deptIdColumn.setCellValueFactory(new PropertyValueFactory<>("department_id"));
        deptNameColumn.setCellValueFactory(new PropertyValueFactory<>("department_name"));
        managerNameColumn.setCellValueFactory(new PropertyValueFactory<>("manager_name"));
        employeeCountColumn.setCellValueFactory(new PropertyValueFactory<>("number_of_employees"));
        deptdescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }


//    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DepartmentView.fxml"));
//    Parent root = loader.load();


    public void initialize() throws SQLException {
        initialize(null, null);
        loadTableData();

    }

    private void refreshPage() throws SQLException {
        loadNextDepartmentId();
        loadTableData();
        resetButtonsAndFields();
    }

    private void loadTableData() throws SQLException {
        ArrayList<DepartmentDTO> departmentDTOS = departmentModel.getAllDepartments();
        ObservableList<DepartmentTM> departmentTMS = FXCollections.observableArrayList();

        for (DepartmentDTO dto : departmentDTOS) {
            departmentTMS.add(new DepartmentTM(
                    dto.getDepartment_id(),
                    dto.getDepartment_name(),
                    dto.getManager_name(),
                    dto.getNumber_of_employees(),
                    dto.getDescription()
            ));
        }
        tblDepartment.setItems(departmentTMS);
    }

    private void loadNextDepartmentId() throws SQLException {
        String nextDepartmentId = departmentModel.getNextDepartmentId();
        departmentIdField.setText(nextDepartmentId);
    }

    private void resetButtonsAndFields() {
        save_btn.setDisable(false);
        delete_btn.setDisable(true);
        cancel_btn.setDisable(true);
        clearFields();
    }

    private void clearFields() {
        departmentIdField.clear();
        departmentNameField.clear();
        managerNameField.clear();
        numEmployeesField.clear();
        descriptionArea.clear();
    }


    private boolean validateAllFields() {
        if (validateDepartmentId()) {
            showAlert("Validation Error", "Invalid Department ID format.");
            return false;
        }
        if (validateDepartmentName()) {
            showAlert("Validation Error", "Invalid Department Name format.");
            return false;
        }
        if (validateManagerName()) {
            showAlert("Validation Error", "Invalid Manager Name format.");
            return false;
        }
        if (validateEmployeeCount()) {
            showAlert("Validation Error", "Invalid Employee Count format.");
            return false;
        }
        if (validateDescription()) {
            showAlert("Validation Error", "Invalid Description format.");
            return false;
        }
        return true;
    }


    private boolean validateDepartmentId() {
        return !ID_PATTERN.matcher(departmentIdField.getText()).matches();
    }

    private boolean validateDepartmentName() {

        return !NAME_PATTERN.matcher(departmentNameField.getText()).matches();
    }

    private boolean validateManagerName() {

        return !NAME_PATTERN.matcher(managerNameField.getText()).matches();
    }

    private boolean validateEmployeeCount() {
        return !EMPLOYEE_COUNT_PATTERN.matcher(numEmployeesField.getText()).matches();
    }

    private boolean validateDescription() {

        return !DESCRIPTION_PATTERN.matcher(descriptionArea.getText()).matches();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void cancleOnAction(ActionEvent actionEvent) {
        clearFields();
        resetButtonsAndFields();
    }

    public void saveOnAction(ActionEvent actionEvent) {
        if (validateAllFields()) {
            try {
                DepartmentDTO departmentDTO = new DepartmentDTO(
                        departmentIdField.getText(),
                        departmentNameField.getText(),
                        managerNameField.getText(),
                        Integer.parseInt(numEmployeesField.getText()),
                        descriptionArea.getText()
                );

                if (departmentModel.save(departmentDTO)) {
                    showAlert("Success", "Department saved successfully.");
                    refreshPage(); // Refreshes the table and fields
                } else {
                    showAlert("Error", "Failed to save department.");
                }
            } catch (SQLException e) {
                showAlert("Error", "Database error: " + e.getMessage());
            }
        }
    }

//    public void deleteOnAction(ActionEvent actionEvent) {
//        if (departmentIdField.getText().isEmpty()) {
//            showAlert("Validation Error", "Please enter a Department ID to delete.");
//            return;
//        }
//
//        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
//                "Are you sure you want to delete this department?", ButtonType.YES, ButtonType.NO);
//        confirmationAlert.setTitle("Delete Confirmation");
//        confirmationAlert.showAndWait();
//
//        if (confirmationAlert.getResult() == ButtonType.YES) {
//            try {
//                if (departmentModel.delete(departmentIdField.getText())) {
//                    showAlert("Success", "Department deleted successfully.");
//                    refreshPage();
//                } else {
//                    showAlert("Error", "No department found with the given ID.");
//                }
//            } catch (SQLException e) {
//                showAlert("Error", "Database error: " + e.getMessage());
//            }
//        }
//    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException {
        String departmentIdFieldText = departmentIdField.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = departmentModel.deleteDepartment(departmentIdFieldText);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete customer...!").show();
            }
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String departmentIdFieldText = departmentIdField.getText();
        String nameFieldText = departmentNameField.getText();
        String managerNameFieldText = managerNameField.getText();
        Integer numEmployeesFieldText = Integer.valueOf(numEmployeesField.getText());
        String descriptionAreaText = descriptionArea.getText();

        DepartmentDTO departmentDTO = new DepartmentDTO(departmentIdFieldText, nameFieldText, managerNameFieldText, numEmployeesFieldText, descriptionAreaText);

        boolean isUpdated = departmentModel.updateDepartment(departmentDTO);
        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Customer updated...!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to update customer...!").show();
        }

    }

    public void handledepartmentId(ActionEvent actionEvent) {
        if (validateDepartmentId()) {
            showAlert("Validation Error", "Invalid Department ID format.");
            departmentIdField.requestFocus();
        } else {
            departmentNameField.requestFocus();
        }
    }

    public void handleDepartmentName(ActionEvent actionEvent) {
        if (validateDepartmentName()) {
            showAlert("Validation Error", "Invalid Department Name format.");
            departmentNameField.requestFocus();
        } else {
            managerNameField.requestFocus();
        }
    }

    public void handlemanagername(ActionEvent actionEvent) {
        if (validateManagerName()) {
            showAlert("Validation Error", "Invalid Manager Name format.");
            managerNameField.requestFocus();
        } else {
            numEmployeesField.requestFocus();
        }
    }

    public void handleNumberOfEmployees(ActionEvent actionEvent) {
        if (validateEmployeeCount()) {
            showAlert("Validation Error", "Invalid Employee Count format.");
            numEmployeesField.requestFocus();
        } else {
            descriptionArea.requestFocus();
        }
    }

    public void handledescription(ActionEvent actionEvent) {
        if (validateDescription()) {
            showAlert("Validation Error", "Invalid Description format.");
            descriptionArea.requestFocus();
        } else {
            save_btn.requestFocus();
        }
    }
}