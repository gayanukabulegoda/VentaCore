package lk.ijse.ventacorebackend.bo.custom;

import lk.ijse.ventacorebackend.bo.SuperBO;
import lk.ijse.ventacorebackend.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    boolean saveCustomer(CustomerDTO dto) throws SQLException;
    CustomerDTO getCustomerData(String id) throws SQLException;
    boolean updateCustomer(CustomerDTO dto) throws SQLException;
    boolean deleteCustomer(String id) throws SQLException;
    List<CustomerDTO> getAllCustomers() throws SQLException;
}
