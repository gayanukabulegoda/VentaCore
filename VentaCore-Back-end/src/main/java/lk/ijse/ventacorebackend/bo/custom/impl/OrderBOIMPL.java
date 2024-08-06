package lk.ijse.ventacorebackend.bo.custom.impl;

import lk.ijse.ventacorebackend.bo.custom.OrderBO;
import lk.ijse.ventacorebackend.dao.DAOFactory;
import lk.ijse.ventacorebackend.dao.custom.OrderDAO;
import lk.ijse.ventacorebackend.dao.custom.OrderItemDetailDAO;
import lk.ijse.ventacorebackend.db.DbConnection;
import lk.ijse.ventacorebackend.dto.ItemDTO;
import lk.ijse.ventacorebackend.dto.OrderDTO;
import lk.ijse.ventacorebackend.entity.Order;
import lk.ijse.ventacorebackend.entity.OrderItemDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBOIMPL implements OrderBO {
    OrderDAO orderDAO =
            (OrderDAO) DAOFactory.getDaoFactory()
                    .getDAO(DAOFactory.DAOTypes.ORDER);

    OrderItemDetailDAO orderItemDetailDAO =
            (OrderItemDetailDAO) DAOFactory.getDaoFactory()
                    .getDAO(DAOFactory.DAOTypes.ORDER_ITEM_DETAIL);

    @Override
    public boolean saveOrder(OrderDTO dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        boolean isOrderSaved;
        boolean isOrderItemSaved = true;
        connection.setAutoCommit(false);

        // Save order
        isOrderSaved = orderDAO.save(new Order(
                dto.getId(),
                dto.getDate(),
                dto.getCustomerId(),
                Double.parseDouble(dto.getTotal()),
                dto.getDiscount(),
                Double.parseDouble(dto.getSubTotal()),
                Double.parseDouble(dto.getCash()),
                Double.parseDouble(dto.getBalance())
        ));

        // Save order items
        if (isOrderSaved) {
            for (ItemDTO item : dto.getItems()) {
                boolean isOrderItemDetailSaved = orderItemDetailDAO.save(new OrderItemDetail(
                        dto.getId(),
                        item.getId(),
                        Integer.parseInt(item.getQty())
                ));
                if (!isOrderItemDetailSaved) {
                    connection.rollback(); // Rollback transaction on failure
                    isOrderItemSaved = false;
                    break;
                }
            }
        }

        if (isOrderSaved && isOrderItemSaved) {
            connection.commit();
        } else {
            connection.rollback();
        }
        connection.setAutoCommit(true);
        return isOrderSaved;
    }

    @Override
    public List<OrderDTO> getAllOrders() throws SQLException {
        List<Order> orderEntities = orderDAO.getAll();
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orderEntities) {
            orderDTOS.add(
                    new OrderDTO(
                            order.getId(),
                            order.getDate(),
                            order.getCustomerId(),
                            null,
                            String.valueOf(order.getTotal()),
                            order.getDiscount(),
                            String.valueOf(order.getSubTotal()),
                            String.valueOf(order.getCash()),
                            String.valueOf(order.getBalance())
                    ));
        }
        return orderDTOS;
    }
}
