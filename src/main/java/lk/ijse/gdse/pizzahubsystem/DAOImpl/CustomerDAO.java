package lk.ijse.gdse.pizzahubsystem.DAOImpl;

import lk.ijse.gdse.pizzahubsystem.dto.CustomerDTO;
import lk.ijse.gdse.pizzahubsystem.dto.tm.CustomerTM;

import java.sql.SQLException;
import java.util.ArrayList;

public abstract class CustomerDAO {
    public abstract int getNextCustomerId() throws SQLException;

    public abstract boolean saveCustomer(CustomerDTO customerDTO) throws SQLException;

    public abstract ArrayList<CustomerTM> getAllCustomers() throws SQLException, ClassNotFoundException;

    public abstract boolean updateCustomer(CustomerDTO customerDTO) throws SQLException;

    public abstract boolean deleteCustomer(String customerId) throws SQLException;

    public abstract ArrayList<String> getAllCustomerIds() throws SQLException;

    public abstract CustomerDTO findById(String selectedCusId) throws SQLException;
}
