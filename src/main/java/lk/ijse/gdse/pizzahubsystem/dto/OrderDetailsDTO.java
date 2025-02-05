package lk.ijse.gdse.pizzahubsystem.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailsDTO {
    private String orderDetail_id;
    private String order_id;
    private String product_id;
    private int quantity;
    private double price;


    public Object getOrder_date() {
        return orderDetail_id;

    }

    public Object getCustomer_id() {
        return order_id;
    }

    public Object getTotal_price() {
        return price;
    }
}
