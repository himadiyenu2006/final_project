package lk.ijse.gdse.pizzahubsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CustomerFeedbackController {

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtFeedBack;

    @FXML
    private TextField txtFeedBackText;

    @FXML
    private TextField txtFeedbackDate;

    @FXML
    private TextField txtRating;

    @FXML
    void ClearOnAction(ActionEvent event) {
        txtCustomerId.clear();
        txtFeedBack.clear();
        txtFeedBackText.clear();
        txtFeedbackDate.clear();
        txtRating.clear();
    }

    @FXML
    void SubmitOnAction(ActionEvent event) {
        if (validateInputs()) {
            try {
                boolean transactionResult = saveFeedbackTransaction();

                if (transactionResult) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Feedback submitted successfully.");
                    ClearOnAction(event);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to submit feedback. Please try again.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private boolean validateInputs() {
        if (txtCustomerId.getText().isEmpty() || !txtCustomerId.getText().matches("\\d+")) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter a valid Customer ID (numeric).");
            return false;
        }

        if (txtFeedBack.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter feedback.");
            return false;
        }

        if (txtFeedBackText.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter feedback text.");
            return false;
        }

        try {
            LocalDate.parse(txtFeedbackDate.getText());
        } catch (DateTimeParseException e) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter a valid date (YYYY-MM-DD).");
            return false;
        }

        try {
            int rating = Integer.parseInt(txtRating.getText());
            if (rating < 1 || rating > 5) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Rating must be between 1 and 5.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter a numeric rating between 1 and 5.");
            return false;
        }

        return true;
    }


    private boolean saveFeedbackTransaction() {
        return true;
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
