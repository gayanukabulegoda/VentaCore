package lk.ijse.ventacorebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemDetailDTO implements Serializable {
    private String orderId;
    private String itemId;
    private int qty;
}
