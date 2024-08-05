package lk.ijse.ventacorebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDTO implements Serializable {
    private String id;
    private String name;
    private String qty;
    private String price;
}
