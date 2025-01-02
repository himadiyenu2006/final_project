package lk.ijse.gdse.pizzahubsystem.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDTO {
    private String order_id;
    private String order_date;
    private String status;
    private String total_price;
    private String customer_id;



}
