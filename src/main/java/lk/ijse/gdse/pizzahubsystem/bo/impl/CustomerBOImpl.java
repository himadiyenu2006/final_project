package lk.ijse.gdse.pizzahubsystem.bo.impl;

import lk.ijse.gdse.pizzahubsystem.dto.CustomerDTO;
import lk.ijse.gdse.pizzahubsystem.dto.tm.CustomerTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBOImpl {
    int getNextCustomerId() throws SQLException;
    boolean saveCustomer(CustomerDTO customerDTO) throws SQLException;
    ArrayList<CustomerTM> getAllCustomers() throws SQLException, ClassNotFoundException;
    boolean updateCustomer(CustomerDTO customerDTO) throws SQLException;
    boolean deleteCustomer(String customerId) throws SQLException;
    ArrayList<String> getAllCustomerIds() throws SQLException;
    CustomerDTO findById(String customerId) throws SQLException;
}
