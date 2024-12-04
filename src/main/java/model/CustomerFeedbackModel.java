package model;

import lk.ijse.gdse.pizzahubsystem.dto.CustomerFeedbackDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerFeedbackModel {

    public int getNextFeedbackId() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select feedback_id from customer_feedback order by feedback_id desc limit 1");

        if (rst.next()) {
            int lastId = rst.getInt(1);
            return lastId + 1;
        }
        return 1;
    }
    public boolean saveFeedback(CustomerFeedbackDTO feedbackDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "insert into customer_feedback(feedback_id, customer_id, feedback_text, rating, feedback_date) values (?,?,?,?,?)",
                feedbackDTO.getFeedback_id(),
                feedbackDTO.getCustomer_id(),
                feedbackDTO.getFeedbackText(),
                feedbackDTO.getRating(),
                feedbackDTO.getFeedbackDate()
        );
    }

    public ArrayList<CustomerFeedbackDTO> getAllFeedbacks() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select * from customer_feedback");

        ArrayList<CustomerFeedbackDTO> feedbacks = new ArrayList<>();

        while (rst.next()) {
            CustomerFeedbackDTO feedbackDTO = new CustomerFeedbackDTO(
                    rst.getInt("feedback_id"),
                    rst.getInt("customer_id"),
                    rst.getString("feedback_text"),
                    rst.getInt("rating"),
                    rst.getDate("feedback_date")
            );
            feedbacks.add(feedbackDTO);
        }
        return feedbacks;
    }

    public boolean deleteFeedback(int feedbackId) throws SQLException {
        return Util.CrudUtil.execute("delete from customer_feedback where feedback_id=?", feedbackId);
    }


    public boolean updateFeedback(CustomerFeedbackDTO feedbackDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "update customer_feedback set feedback_text=?, rating=?, feedback_date=? where feedback_id=?",
                feedbackDTO.getFeedbackText(),
                feedbackDTO.getRating(),
                feedbackDTO.getFeedbackDate(),
                feedbackDTO.getFeedback_id()
        );
    }

    public CustomerFeedbackDTO findById(int feedbackId) throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select * from customer_feedback where feedback_id=?", feedbackId);

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
