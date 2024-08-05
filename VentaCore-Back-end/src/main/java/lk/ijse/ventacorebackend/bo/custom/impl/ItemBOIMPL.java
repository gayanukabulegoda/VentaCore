package lk.ijse.ventacorebackend.bo.custom.impl;

import lk.ijse.ventacorebackend.bo.custom.ItemBO;
import lk.ijse.ventacorebackend.dao.DAOFactory;
import lk.ijse.ventacorebackend.dao.custom.ItemDAO;
import lk.ijse.ventacorebackend.dto.ItemDTO;
import lk.ijse.ventacorebackend.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class ItemBOIMPL implements ItemBO {
    ItemDAO itemDAO =
            (ItemDAO) DAOFactory.getDaoFactory()
                    .getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public boolean saveItem(ItemDTO dto) throws SQLException {
        return itemDAO.save(
                new Item(
                        dto.getId(),
                        dto.getName(),
                        parseInt(dto.getQty()),
                        parseDouble(dto.getPrice())
                )
        );
    }

    @Override
    public ItemDTO getItemData(String id) throws SQLException {
        Item entity = itemDAO.getData(id);
        return new ItemDTO(
                entity.getId(),
                entity.getName(),
                String.valueOf(entity.getQty()),
                String.valueOf(entity.getPrice())
        );
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException {
        return itemDAO.update(
                new Item(
                        dto.getId(),
                        dto.getName(),
                        parseInt(dto.getQty()),
                        parseDouble(dto.getPrice())
                )
        );
    }

    @Override
    public boolean deleteItem(String id) throws SQLException {
        return itemDAO.delete(id);
    }

    @Override
    public List<ItemDTO> getAllItems() throws SQLException {
        List<Item> itemEntities = itemDAO.getAll();
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : itemEntities) {
            itemDTOS.add(
                    new ItemDTO(
                            item.getId(),
                            item.getName(),
                            String.valueOf((item.getQty())),
                            String.valueOf(item.getPrice())
                    ));
        }
        return itemDTOS;
    }
}
