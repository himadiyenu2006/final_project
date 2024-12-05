package lk.ijse.gdse.pizzahubsystem.dto.tm;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderTM {
    private String order_id;
    private LocalDate order_date;
    private String status;
    private double total_price;
    private String customer_id;


}