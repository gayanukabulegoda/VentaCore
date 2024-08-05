package lk.ijse.ventacorebackend.bo.custom;

import lk.ijse.ventacorebackend.bo.SuperBO;
import lk.ijse.ventacorebackend.dto.ItemDTO;

import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO {
    boolean saveItem(ItemDTO dto) throws SQLException;
    ItemDTO getItemData(String id) throws SQLException;
    boolean updateItem(ItemDTO dto) throws SQLException;
    boolean deleteItem(String id) throws SQLException;
    List<ItemDTO> getAllItems() throws SQLException;
}
