package lk.ijse.gdse.pizzahubsystem.DAOImpl;

import lk.ijse.gdse.pizzahubsystem.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentDAO {
    String getNextPaymentId() throws SQLException;

    boolean savePayment(PaymentDTO paymentDTO) throws SQLException;

    ArrayList<PaymentDTO> getAllPayments() throws SQLException;

    boolean updatePayment(PaymentDTO paymentDTO) throws SQLException;

    boolean deletePayment(String paymentId) throws SQLException;

    PaymentDTO findById(String selectedPaymentId) throws SQLException;

    ArrayList<PaymentDTO> getPaymentsByOrderId(int orderId) throws SQLException;
}
