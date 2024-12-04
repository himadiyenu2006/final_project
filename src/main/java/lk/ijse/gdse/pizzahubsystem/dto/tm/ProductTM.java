package lk.ijse.gdse.pizzahubsystem.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProductTM {
    private String product_id;
    private String product_name;
    private double price;
    private String description;
    private String category;
    private int inventory_count;
}