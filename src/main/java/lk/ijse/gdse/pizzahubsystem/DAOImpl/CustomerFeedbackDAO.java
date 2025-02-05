package lk.ijse.gdse.pizzahubsystem.DAOImpl;

import lk.ijse.gdse.pizzahubsystem.dto.CustomerFeedbackDTO;

import java.sql.SQLException;
import java.util.List;

public abstract class CustomerFeedbackDAO {
    public abstract int getNextFeedbackId() throws SQLException;

    public abstract boolean saveFeedback(CustomerFeedbackDTO feedbackDTO) throws SQLException;

    public abstract List<CustomerFeedbackDTO> getAllFeedbacks() throws SQLException;

    public abstract boolean deleteFeedback(int feedbackId) throws SQLException;

    public abstract boolean updateFeedback(CustomerFeedbackDTO feedbackDTO) throws SQLException;

    public abstract CustomerFeedbackDTO findById(int feedbackId) throws SQLException;
}
