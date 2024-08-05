package lk.ijse.ventacorebackend.dao.custom.impl;

import lk.ijse.ventacorebackend.dao.custom.ItemDAO;
import lk.ijse.ventacorebackend.entity.Item;
import lk.ijse.ventacorebackend.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOIMPL implements ItemDAO {
    public static String SAVE_ITEM = "INSERT INTO item (id, name, qty, price) VALUES(?,?,?,?)";
    public static String GET_ITEM = "SELECT * FROM item WHERE id=?";
    public static String UPDATE_ITEM = "UPDATE item SET name=?, qty=?, price=? WHERE id=?";
    public static String DELETE_ITEM = "DELETE FROM item WHERE id=?";
    public static String GET_ALL_ITEM = "SELECT * FROM item";

    @Override
    public boolean save(Item entity) throws SQLException {
        return SQLUtil.execute(SAVE_ITEM,
                entity.getId(),
                entity.getName(),
                entity.getQty(),
                entity.getPrice()
        );
    }

    @Override
    public Item getData(String id) throws SQLException {
        ResultSet set = SQLUtil.execute(GET_ITEM, id);
        Item entity = new Item();
        if (set.next()) {
            entity.setId(set.getString(1));
            entity.setName(set.getString(2));
            entity.setQty(set.getInt(3));
            entity.setPrice(set.getDouble(4));
        }
        return entity;
    }

    @Override
    public boolean update(Item entity) throws SQLException {
        return SQLUtil.execute(UPDATE_ITEM,
                entity.getName(),
                entity.getQty(),
                entity.getPrice(),
                entity.getId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute(DELETE_ITEM, id);
    }

    @Override
    public List<Item> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute(GET_ALL_ITEM);
        List<Item> itemList = new ArrayList<>();
        while (rst.next()) {
            itemList.add(new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            ));
        }
        return itemList;
    }
}
