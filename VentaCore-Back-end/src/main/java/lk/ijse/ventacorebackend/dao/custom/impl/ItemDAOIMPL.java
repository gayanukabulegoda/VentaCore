package lk.ijse.ventacorebackend.dao.custom.impl;

import lk.ijse.ventacorebackend.dao.custom.ItemDAO;
import lk.ijse.ventacorebackend.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOIMPL implements ItemDAO {
    @Override
    public boolean save(Item entity) throws SQLException {
        return false;
    }

    @Override
    public Item getData(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean update(Item entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException {
        return null;
    }
}
