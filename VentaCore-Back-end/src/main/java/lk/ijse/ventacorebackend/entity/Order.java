package lk.ijse.ventacorebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    private String id;
    private String date;
    private String customerId;
    private double total;
    private String discount;
    private double subTotal;
    private double cash;
    private double balance;
}
