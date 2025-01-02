package lk.ijse.gdse.pizzahubsystem.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderTM {
    private String order_id;
    private String order_date;
    private String status;
    private String total_price;
    private String customer_id;


}