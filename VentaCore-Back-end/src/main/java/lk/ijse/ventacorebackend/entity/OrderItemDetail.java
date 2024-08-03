package lk.ijse.ventacorebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemDetail {
    private String orderId;
    private String itemId;
    private int qty;
}
