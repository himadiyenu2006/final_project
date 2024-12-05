package lk.ijse.gdse.pizzahubsystem.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDTO {
    private String order_id;
    private LocalDate order_date;
    private String status;
    private double total_price;
    private String customer_id;



}
