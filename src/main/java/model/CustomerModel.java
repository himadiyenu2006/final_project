package model;

import lk.ijse.gdse.pizzahubsystem.dto.CustomerDTO;
import lk.ijse.gdse.pizzahubsystem.dto.tm.CustomerTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {

    private Util.CrudUtil CrudUtil;


    public int getNextCustomerId() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select customer_id from customer order by customer_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;

        }
        return 1;
    }

    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "insert into customer values (?,?,?,?,?)",
                customerDTO.getCustomerId(),
                customerDTO.getName(),
                customerDTO.getContact(),
                customerDTO.getEmail(),
                customerDTO.getAddress()

        );
    }

    public ArrayList<CustomerTM> getAllCustomers() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select * from customer");

        ArrayList<CustomerTM> customerDTOS = new ArrayList<>();

        while (rst.next()) {
            CustomerTM customerDTO = new CustomerTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "update customer set name=?, contact=?, email=?,address=? where customer_id=?",
                customerDTO.getName(),
                customerDTO.getContact(),
                customerDTO.getEmail(),
                customerDTO.getAddress(),
                customerDTO.getCustomerId()
        );
    }

    public boolean deleteCustomer(String customerId) throws SQLException {
        return Util.CrudUtil.execute("delete from customer where customer_id=?", customerId);
    }

    public ArrayList<String> getAllCustomerIds() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select customer_id from customer");

        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }

        return customerIds;
    }
    public CustomerDTO findById(String selectedCusId) throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select * from customer where customer_id=?", selectedCusId);

        if (rst.next()) {
            return new CustomerDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }
}
