package lk.ijse.gdse.pizzahubsystem.DAOImpl;

import lk.ijse.gdse.pizzahubsystem.dto.CustomerFeedbackDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerFeedbackDAOImpl extends CustomerFeedbackDAO {

    private Util.CrudUtil CrudUtil;

    @Override
    public int getNextFeedbackId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT feedback_id FROM customer_feedback ORDER BY feedback_id DESC LIMIT 1");
        if (rst.next()) {
            return rst.getInt(1) + 1;
        }
        return 1;
    }

    @Override
    public boolean saveFeedback(CustomerFeedbackDTO feedbackDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO customer_feedback(feedback_id, customer_id, feedback_text, rating, feedback_date) VALUES (?,?,?,?,?)",
                feedbackDTO.getFeedback_id(),
                feedbackDTO.getCustomer_id(),
                feedbackDTO.getFeedbackText(),
                feedbackDTO.getRating(),
                feedbackDTO.getFeedbackDate()
        );
    }

    @Override
    public List<CustomerFeedbackDTO> getAllFeedbacks() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM customer_feedback");
        List<CustomerFeedbackDTO> feedbacks = new ArrayList<>();
        while (rst.next()) {
            feedbacks.add(new CustomerFeedbackDTO(
                    rst.getInt("feedback_id"),
                    rst.getInt("customer_id"),
                    rst.getString("feedback_text"),
                    rst.getInt("rating"),
                    rst.getDate("feedback_date")
            ));
        }
        return feedbacks;
    }

    @Override
    public boolean deleteFeedback(int feedbackId) throws SQLException {
        return CrudUtil.execute("DELETE FROM customer_feedback WHERE feedback_id=?", feedbackId);
    }

    @Override
    public boolean updateFeedback(CustomerFeedbackDTO feedbackDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE customer_feedback SET feedback_text=?, rating=?, feedback_date=? WHERE feedback_id=?",
                feedbackDTO.getFeedbackText(),
                feedbackDTO.getRating(),
                feedbackDTO.getFeedbackDate(),
                feedbackDTO.getFeedback_id()
        );
    }

    @Override
    public CustomerFeedbackDTO findById(int feedbackId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM customer_feedback WHERE feedback_id=?", feedbackId);
        if (rst.next()) {
            return new CustomerFeedbackDTO(
                    rst.getInt("feedback_id"),
                    rst.getInt("customer_id"),
                    rst.getString("feedback_text"),
                    rst.getInt("rating"),
                    rst.getDate("feedback_date")
            );
        }
        return null;
    }
}
