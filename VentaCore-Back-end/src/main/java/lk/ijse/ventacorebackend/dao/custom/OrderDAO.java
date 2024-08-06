package lk.ijse.ventacorebackend.dao.custom;

import lk.ijse.ventacorebackend.dao.SuperDAO;
import lk.ijse.ventacorebackend.entity.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO extends SuperDAO {
    boolean save(Order entity) throws SQLException;
    List<Order> getAll() throws SQLException;
}