package lk.ijse.gdse.pizzahubsystem.dto;

import lombok.*;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDTO {
    private String order_id;
    private Date order_date;
    private String status;
    private double total_price;
    private String customer_id;


}
