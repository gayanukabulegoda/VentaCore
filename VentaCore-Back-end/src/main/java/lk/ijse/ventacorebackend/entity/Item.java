package lk.ijse.ventacorebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {
    private String id;
    private String name;
    private int qty;
    private double price;
}
