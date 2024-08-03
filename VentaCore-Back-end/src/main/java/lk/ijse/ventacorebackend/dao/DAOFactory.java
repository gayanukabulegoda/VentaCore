package lk.ijse.ventacorebackend.dao;

import lk.ijse.ventacorebackend.dao.custom.impl.CustomerDAOIMPL;
import lk.ijse.ventacorebackend.dao.custom.impl.ItemDAOIMPL;
import lk.ijse.ventacorebackend.dao.custom.impl.OrderDAOIMPL;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {}

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ?
                daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMER, ITEM, ORDER
    }

    public SuperDAO getDAO(DAOTypes daoTypes) {
        return switch (daoTypes) {
            case CUSTOMER -> new CustomerDAOIMPL();
            case ITEM -> new ItemDAOIMPL();
            case ORDER -> new OrderDAOIMPL();
        };
    }
}
