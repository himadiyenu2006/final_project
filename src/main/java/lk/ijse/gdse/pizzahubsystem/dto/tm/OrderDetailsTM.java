package lk.ijse.gdse.pizzahubsystem.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDetailsTM {
    private String order_details_id;
    private String order_id;
    private String product_id;
    private int quantity;
    private double price;
}
