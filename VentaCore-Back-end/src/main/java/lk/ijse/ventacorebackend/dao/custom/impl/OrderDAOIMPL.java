package lk.ijse.ventacorebackend.dao.custom.impl;

import lk.ijse.ventacorebackend.dao.custom.OrderDAO;
import lk.ijse.ventacorebackend.entity.Order;
import lk.ijse.ventacorebackend.entity.OrderItemDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOIMPL implements OrderDAO {
    @Override
    public boolean save(OrderItemDetail entity) throws SQLException {
        return false;
    }

    @Override
    public OrderItemDetail getData(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean update(OrderItemDetail entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<OrderItemDetail> getAll() throws SQLException {
        return null;
    }
}
