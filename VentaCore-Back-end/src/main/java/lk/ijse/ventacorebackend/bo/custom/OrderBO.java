package lk.ijse.ventacorebackend.bo.custom;

import lk.ijse.ventacorebackend.bo.SuperBO;
import lk.ijse.ventacorebackend.dto.OrderDTO;

import java.sql.SQLException;
import java.util.List;

public interface OrderBO extends SuperBO {
    boolean saveOrder(OrderDTO dto) throws SQLException;
    List<OrderDTO> getAllOrders() throws SQLException;
}
