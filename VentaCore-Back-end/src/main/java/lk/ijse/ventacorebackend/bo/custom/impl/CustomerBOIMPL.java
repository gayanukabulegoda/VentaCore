package lk.ijse.ventacorebackend.bo.custom.impl;

import lk.ijse.ventacorebackend.bo.custom.CustomerBO;
import lk.ijse.ventacorebackend.dao.DAOFactory;
import lk.ijse.ventacorebackend.dao.custom.CustomerDAO;
import lk.ijse.ventacorebackend.dto.CustomerDTO;
import lk.ijse.ventacorebackend.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOIMPL implements CustomerBO {
    CustomerDAO customerDAO =
            (CustomerDAO) DAOFactory.getDaoFactory()
                    .getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean saveCustomer(CustomerDTO dto) throws SQLException {
        return customerDAO.save(
                new Customer(
                        dto.getId(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getCity()
                )
        );
    }

    @Override
    public CustomerDTO getCustomerData(String id) throws SQLException {
        Customer entity = customerDAO.getData(id);
        return new CustomerDTO(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getCity()
        );
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException {
        return customerDAO.update(
                new Customer(
                        dto.getId(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getCity()
                )
        );
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException {
        return customerDAO.delete(id);
    }

    @Override
    public ArrayList<String> getAllCustomerId() throws SQLException {
        return null;
    }
}
