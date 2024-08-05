package lk.ijse.ventacorebackend.dao.custom.impl;

import lk.ijse.ventacorebackend.dao.custom.CustomerDAO;
import lk.ijse.ventacorebackend.entity.Customer;
import lk.ijse.ventacorebackend.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOIMPL implements CustomerDAO {
    public static String SAVE_CUSTOMER = "INSERT INTO customer (id, name, email, city) VALUES(?,?,?,?)";
    public static String GET_CUSTOMER = "SELECT * FROM customer WHERE id=?";
    public static String UPDATE_CUSTOMER = "UPDATE customer SET name=?, email=?, city=? WHERE id=?";
    public static String DELETE_CUSTOMER = "DELETE FROM customer WHERE id=?";
    public static String GET_ALL_CUSTOMERS = "SELECT * FROM customer";

    @Override
    public boolean save(Customer entity) throws SQLException {
        return SQLUtil.execute(SAVE_CUSTOMER,
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getCity()
        );
    }

    @Override
    public Customer getData(String id) throws SQLException {
        ResultSet set = SQLUtil.execute(GET_CUSTOMER, id);
        Customer entity = new Customer();
        if (set.next()) {
            entity.setId(set.getString(1));
            entity.setName(set.getString(2));
            entity.setEmail(set.getString(3));
            entity.setCity(set.getString(4));
        }
        return entity;
    }

    @Override
    public boolean update(Customer entity) throws SQLException {
        return SQLUtil.execute(UPDATE_CUSTOMER,
                entity.getName(),
                entity.getEmail(),
                entity.getCity(),
                entity.getId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute(DELETE_CUSTOMER, id);
    }

    @Override
    public List<Customer> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute(GET_ALL_CUSTOMERS);
        List<Customer> customerList = new ArrayList<>();
        while (rst.next()) {
            customerList.add(new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            ));
        }
        return customerList;
    }
}
