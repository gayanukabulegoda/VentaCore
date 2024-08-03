package lk.ijse.ventacorebackend.bo;

import lk.ijse.ventacorebackend.bo.custom.impl.CustomerBOIMPL;
import lk.ijse.ventacorebackend.bo.custom.impl.ItemBOIMPL;
import lk.ijse.ventacorebackend.bo.custom.impl.OrderBOIMPL;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {}

    public static BOFactory getBoFactory() {
        return (boFactory == null) ?
                boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        CUSTOMER, ITEM, ORDER
    }

    public SuperBO getBO(BOTypes boTypes) {
        return switch (boTypes) {
            case CUSTOMER -> new CustomerBOIMPL();
            case ITEM -> new ItemBOIMPL();
            case ORDER -> new OrderBOIMPL();
        };
    }
}
