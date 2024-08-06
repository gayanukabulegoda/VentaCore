package lk.ijse.ventacorebackend.dao.custom.impl;

import lk.ijse.ventacorebackend.dao.custom.OrderItemDetailDAO;
import lk.ijse.ventacorebackend.entity.OrderItemDetail;
import lk.ijse.ventacorebackend.util.SQLUtil;

import java.sql.SQLException;

public class OrderItemDetailDAOIMPL implements OrderItemDetailDAO {
    public static String SAVE_ORDER_ITEM_DETAIL = "INSERT INTO order_item_detail (order_id, item_id, qty) VALUES(?,?,?)";

    @Override
    public boolean save(OrderItemDetail entity) throws SQLException {
        return SQLUtil.execute(SAVE_ORDER_ITEM_DETAIL,
                entity.getOrderId(),
                entity.getItemId(),
                entity.getQty()
        );
    }
}
