package lk.ijse.gdse.pizzahubsystem.DAOImpl;


import Util.CrudUtil;
import lk.ijse.gdse.pizzahubsystem.dto.CustomerDTO;
import lk.ijse.gdse.pizzahubsystem.dto.tm.CustomerTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl extends CustomerDAO {
    @Override
    public int getNextCustomerId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT customer_id FROM customer ORDER BY customer_id DESC LIMIT 1");
        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            return i + 1;
        }
        return 1;
    }

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        return CrudUtil.execute("INSERT INTO customer VALUES (?,?,?,?,?)",
                customerDTO.getCustomerId(),
                customerDTO.getName(),
                customerDTO.getContact(),
                customerDTO.getEmail(),
                customerDTO.getAddress());
    }

    @Override
    public ArrayList<CustomerTM> getAllCustomers() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM customer");
        ArrayList<CustomerTM> customers = new ArrayList<>();
        while (rst.next()) {
            customers.add(new CustomerTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)));
        }
        return customers;
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
        return CrudUtil.execute("UPDATE customer SET name=?, contact=?, email=?, address=? WHERE customer_id=?",
                customerDTO.getName(),
                customerDTO.getContact(),
                customerDTO.getEmail(),
                customerDTO.getAddress(),
                customerDTO.getCustomerId());
    }

    @Override
    public boolean deleteCustomer(String customerId) throws SQLException {
        return CrudUtil.execute("DELETE FROM customer WHERE customer_id=?", customerId);
    }

    @Override
    public ArrayList<String> getAllCustomerIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT customer_id FROM customer");
        ArrayList<String> customerIds = new ArrayList<>();
        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }
        return customerIds;
    }

    @Override
    public CustomerDTO findById(String selectedCusId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM customer WHERE customer_id=?", selectedCusId);
        if (rst.next()) {
            return new CustomerDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5));
        }
        return null;
    }
}
