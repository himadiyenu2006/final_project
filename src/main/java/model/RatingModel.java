package model;

import lk.ijse.gdse.pizzahubsystem.dto.tm.RatingTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RatingModel {

    private Util.CrudUtil CrudUtil;

    public String getNextRatingId() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("SELECT ratingId FROM rating ORDER BY ratingId DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("R%03d", newIdIndex);
        }
        return "R001";
    }

    // Method to save a rating
    public boolean saveRating(RatingTM ratingTM) throws SQLException {
        return Util.CrudUtil.execute(
                "INSERT INTO rating(ratingId, customerId, orderId, ratingValue, comments) VALUES (?,?,?,?,?)",
                ratingTM.getRatingId(),
                ratingTM.getCustomerId(),
                ratingTM.getOrderId(),
                ratingTM.getRatingValue(),
                ratingTM.getComments()
        );
    }


    public ArrayList<RatingTM> getAllRatings() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("SELECT * FROM rating");

        ArrayList<RatingTM> ratingTMs = new ArrayList<>();

        while (rst.next()) {
            RatingTM ratingTM = new RatingTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getString(5)
            );
            ratingTMs.add(ratingTM);
        }
        return ratingTMs;
    }


    public boolean updateRating(RatingTM ratingTM) throws SQLException {
        return Util.CrudUtil.execute(
                "UPDATE rating SET customerId=?, orderId=?, ratingValue=?, comments=? WHERE ratingId=?",
                ratingTM.getCustomerId(),
                ratingTM.getOrderId(),
                ratingTM.getRatingValue(),
                ratingTM.getComments(),
                ratingTM.getRatingId()
        );
    }

    public boolean deleteRating(String ratingId) throws SQLException {
        return Util.CrudUtil.execute("DELETE FROM rating WHERE ratingId=?", ratingId);
    }

    public RatingTM findById(String selectedRatingId) throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("SELECT * FROM rating WHERE ratingId=?", selectedRatingId);

        if (rst.next()) {
            return new RatingTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getString(5)
            );
        }
        return null;
    }
}
