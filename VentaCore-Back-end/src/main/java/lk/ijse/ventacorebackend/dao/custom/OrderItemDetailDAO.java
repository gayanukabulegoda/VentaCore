package lk.ijse.ventacorebackend.dao.custom;

import lk.ijse.ventacorebackend.dao.SuperDAO;
import lk.ijse.ventacorebackend.entity.OrderItemDetail;

import java.sql.SQLException;

public interface OrderItemDetailDAO extends SuperDAO {
    boolean save(OrderItemDetail entity) throws SQLException;
}
