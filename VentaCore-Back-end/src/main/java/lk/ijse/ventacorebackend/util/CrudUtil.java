package lk.ijse.ventacorebackend.util;

import lk.ijse.ventacorebackend.dao.SuperDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudUtil<T> extends SuperDAO {
    boolean save(T entity) throws SQLException;
    T getData(String id) throws SQLException;
    boolean update(T entity) throws SQLException;
    boolean delete(String id) throws SQLException;
    ArrayList<String> getAllId() throws SQLException;
}
