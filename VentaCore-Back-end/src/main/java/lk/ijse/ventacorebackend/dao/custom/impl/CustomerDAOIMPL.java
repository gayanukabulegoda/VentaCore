package lk.ijse.ventacorebackend.dao.custom.impl;

import lk.ijse.ventacorebackend.dao.custom.CustomerDAO;
import lk.ijse.ventacorebackend.entity.Customer;
import lk.ijse.ventacorebackend.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOIMPL implements CustomerDAO {
    public static String SAVE_CUSTOMER = "INSERT INTO customer (id, name, address, city) VALUES(?,?,?,?)";
    public static String GET_CUSTOMER = "SELECT * FROM customer WHERE id=?";
    public static String UPDATE_CUSTOMER = "UPDATE customer SET name=?, address=?, city=? WHERE id=?";
    public static String DELETE_CUSTOMER = "DELETE FROM customer WHERE id=?";

    @Override
    public boolean save(Customer entity) throws SQLException {
        return SQLUtil.execute(SAVE_CUSTOMER,
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
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
            entity.setAddress(set.getString(3));
            entity.setCity(set.getString(4));
        }
        return entity;
    }

    @Override
    public boolean update(Customer entity) throws SQLException {
        return SQLUtil.execute(UPDATE_CUSTOMER,
                entity.getName(),
                entity.getAddress(),
                entity.getCity(),
                entity.getId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute(DELETE_CUSTOMER, id);
    }

    @Override
    public ArrayList<String> getAllId() throws SQLException {
        return null;
    }
}
