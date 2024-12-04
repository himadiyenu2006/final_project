package lk.ijse.gdse.pizzahubsystem.dto.tm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class InventoryTM {
    private String inventory_id;
    private String product_id;
    private String supplier_id;
    private Integer quantity;
    private Date last_updated;
}
