package lk.ijse.gdse.pizzahubsystem.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemDTO {
    private String item_id;
    private String name;
    private int quantity;
    private double price;

}
