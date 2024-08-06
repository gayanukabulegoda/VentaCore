package lk.ijse.ventacorebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO implements Serializable {
    private String id;
    private String date;
    private String customerId;
    private List<ItemDTO> items = new ArrayList<>();
    private String total;
    private String discount;
    private String subTotal;
    private String cash;
    private String balance;
}
