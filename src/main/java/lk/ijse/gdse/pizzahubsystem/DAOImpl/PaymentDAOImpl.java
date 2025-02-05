package lk.ijse.gdse.pizzahubsystem.DAOImpl;

import Util.CrudUtil;
import lk.ijse.gdse.pizzahubsystem.dto.PaymentDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public String getNextPaymentId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT payment_id FROM payment ORDER BY payment_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }
        return "P001";
    }

    @Override
    public boolean savePayment(PaymentDTO paymentDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO payment(payment_id, order_id, payment_method, payment_date, amount, customer_id, order_name, quantity, payment_method1, payment_method2, payment_method3) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                paymentDTO.getPayment_id(),
                paymentDTO.getOrder_id(),
                paymentDTO.getPayment_method(),
                paymentDTO.getPayment_date(),
                paymentDTO.getAmount(),
                paymentDTO.getCustomer_id(),
                paymentDTO.getOrder_name(),
                paymentDTO.getQuantity(),
                paymentDTO.getPayment_method1(),
                paymentDTO.getPayment_method2(),
                paymentDTO.getPayment_method3()
        );
    }

    @Override
    public ArrayList<PaymentDTO> getAllPayments() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM payment");
        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();

        while (rst.next()) {
            PaymentDTO paymentDTO = new PaymentDTO(
                    Integer.parseInt(rst.getString(1)),
                    rst.getInt(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getDouble(5),
                    rst.getInt(6),
                    rst.getString(7),
                    rst.getInt(8),
                    rst.getString(9),
                    rst.getString(10),
                    rst.getString(11)
            );
            paymentDTOS.add(paymentDTO);
        }
        return paymentDTOS;
    }

    @Override
    public boolean updatePayment(PaymentDTO paymentDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE payment SET order_id=?, payment_method=?, payment_date=?, amount=?, customer_id=?, quantity=?, payment_method1=?, payment_method2=?, payment_method3=? WHERE payment_id=?",
                paymentDTO.getOrder_id(),
                paymentDTO.getPayment_method(),
                paymentDTO.getPayment_date(),
                paymentDTO.getAmount(),
                paymentDTO.getCustomer_id(),
                paymentDTO.getQuantity(),
                paymentDTO.getPayment_method1(),
                paymentDTO.getPayment_method2(),
                paymentDTO.getPayment_method3(),
                paymentDTO.getPayment_id()
        );
    }

    @Override
    public boolean deletePayment(String paymentId) throws SQLException {
        return CrudUtil.execute("DELETE FROM payment WHERE payment_id=?", paymentId);
    }

    @Override
    public PaymentDTO findById(String selectedPaymentId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM payment WHERE payment_id=?", selectedPaymentId);

        if (rst.next()) {
            return new PaymentDTO(
                    Integer.parseInt(rst.getString(1)),
                    rst.getInt(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getDouble(5),
                    rst.getInt(6),
                    rst.getString(7),
                    rst.getInt(8),
                    rst.getString(9),
                    rst.getString(10),
                    rst.getString(11)
            );
        }
        return null;
    }

    @Override
    public ArrayList<PaymentDTO> getPaymentsByOrderId(int orderId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM payment WHERE order_id=?", orderId);
        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();

        while (rst.next()) {
            PaymentDTO paymentDTO = new PaymentDTO(
                    Integer.parseInt(rst.getString(1)),
                    rst.getInt(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getDouble(5),
                    rst.getInt(6),
                    rst.getString(7),
                    rst.getInt(8),
                    rst.getString(9),
                    rst.getString(10),
                    rst.getString(11)
            );
            paymentDTOS.add(paymentDTO);
        }
        return paymentDTOS;
    }
}
