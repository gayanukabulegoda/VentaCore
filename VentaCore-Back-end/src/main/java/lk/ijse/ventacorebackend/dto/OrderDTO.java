package lk.ijse.ventacorebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO implements Serializable {
    private String id;
    private String date;
    private String customerId;
    private double total;
}
