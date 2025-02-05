package model;

import lk.ijse.gdse.pizzahubsystem.dto.PaymentDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentModel {

    public String getNextPaymentId() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("SELECT payment_id FROM payment ORDER BY payment_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }
        return "P001";
    }

    public boolean savePayment(PaymentDTO paymentDTO) throws SQLException {
        return Util.CrudUtil.execute(
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

    public ArrayList<PaymentDTO> getAllPayments() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("SELECT * FROM payment");

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

    public boolean updatePayment(PaymentDTO paymentDTO) throws SQLException {
        return Util.CrudUtil.execute(
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

    public boolean deletePayment(String paymentId) throws SQLException {
        return Util.CrudUtil.execute("DELETE FROM payment WHERE payment_id=?", paymentId);
    }

    public PaymentDTO findById(String selectedPaymentId) throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("SELECT * FROM payment WHERE payment_id=?", selectedPaymentId);

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

    public ArrayList<PaymentDTO> getPaymentsByOrderId(int orderId) throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("SELECT * FROM payment WHERE order_id=?", orderId);

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












//package model;
//
//import lk.ijse.gdse.pizzahubsystem.dto.PaymentDTO;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//public class PaymentModel {
//
//
//    private Util.CrudUtil CrudUtil;
//
//
//    public String getNextPaymentId() throws SQLException {
//        ResultSet rst = Util.CrudUtil.execute("select payment_id from payment order by payment_id desc limit 1");
//
//        if (rst.next()) {
//            String lastId = rst.getString(1);
//            String substring = lastId.substring(1);
//            int i = Integer.parseInt(substring);
//            int newIdIndex = i + 1;
//            return String.format("P%03d", newIdIndex);
//        }
//        return "P001";
//    }
//
//
//    public boolean savePayment(PaymentDTO paymentDTO) throws SQLException {
//        return Util.CrudUtil.execute(
//                "insert into payment(payment_id, order_id, payment_method,payment_date,amount,customer_id,order_name, quantity , payment_method1, payment_method2 , payment_method3 ) values (?,?,?,?,?,?,?,?,?,?,?)",
//                paymentDTO.getPayment_id(),
//                paymentDTO.getOrder_id(),
//                paymentDTO.getPayment_method(),
//                paymentDTO.getPayment_date(),
//                paymentDTO.getAmount(),
//                paymentDTO.getCustomer_id(),
//                paymentDTO.getQuantity(),
//                paymentDTO.getPayment_method1(),
//                paymentDTO.getPayment_method2(),
//                paymentDTO.getPayment_method3()
//
//        );
//    }
//
//
//    public ArrayList<PaymentDTO> getAllPayments() throws SQLException {
//        ResultSet rst = Util.CrudUtil.execute("select * from payment");
//
//        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();
//
//        while (rst.next()) {
//            PaymentDTO paymentDTO = new PaymentDTO(
//                    rst.getInt(1),
//                    rst.getInt(2),
//                    rst.getString(3),
//                    rst.getDate(4),
//                    rst.getDouble(5),
//                    rst.getInt(6),
//                    rst.getString(7),
//                    rst.getInt(8),
//                    rst.getString(9),
//                    rst.getString(10),
//                    rst.getString(11)
//
//
//            );
//            paymentDTOS.add(paymentDTO);
//        }
//        return paymentDTOS;
//    }
//
//
//    public boolean updatePayment(PaymentDTO paymentDTO) throws SQLException {
//        return Util.CrudUtil.execute(
//                "update payment set order_id=?, Payment_method=?, Payment_date=?, Amount=?,Customer_id=? Quantity=? Payment_method1=? Payment_method2=? Payment_method3=?, where payment_id=?",
//                paymentDTO.getPayment_id(),
//                paymentDTO.getOrder_id(),
//                paymentDTO.getPayment_method(),
//                paymentDTO.getPayment_date(),
//                paymentDTO.getAmount(),
//                paymentDTO.getCustomer_id(),
//                paymentDTO.getQuantity(),
//                paymentDTO.getPayment_method1(),
//                paymentDTO.getPayment_method2(),
//                paymentDTO.getPayment_method3()
//        );
//    }
//
//
//    public boolean deletePayment(String paymentId) throws SQLException {
//        return Util.CrudUtil.execute("delete from payment where payment_id=?", paymentId);
//    }
//
//
//    public PaymentDTO findById(String selectedPaymentId) throws SQLException {
//        ResultSet rst = Util.CrudUtil.execute("select * from payment where payment_id=?", selectedPaymentId);
//
//        if (rst.next()) {
//            return new PaymentDTO(
//                    rst.getInt(1),
//                    rst.getInt(2),
//                    rst.getString(3),
//                    rst.getDate(4),
//                    rst.getDouble(5),
//                    rst.getInt(6),
//                    rst.getString(7),
//                    rst.getInt(8),
//                    rst.getString(9),
//                    rst.getString(10),
//                    rst.getString(11));
//        return null;
//    }
//
//    public ArrayList<PaymentDTO> getPaymentsByOrderId(int orderId) throws SQLException {
//        ResultSet rst = Util.CrudUtil.execute("select * from payment where order_id=?", orderId);
//
//        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();
//
//        while (rst.next()) {
//            PaymentDTO paymentDTO = new PaymentDTO(
//                    rst.getInt(1),
//                    rst.getInt(2),
//                    rst.getString(3),
//                    rst.getDate(4),
//                    rst.getDouble(5),
//                    rst.getInt(6),
//                    rst.getString(7),
//                    rst.getInt(8),
//                    rst.getString(9),
//                    rst.getString(10),
//                    rst.getString(11));
//            paymentDTOS.add(paymentDTO);
//        }
//    }
//}
//}