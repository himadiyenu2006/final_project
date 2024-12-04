package lk.ijse.gdse.pizzahubsystem.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InventoryDTO {
    private String inventory_id;
    private String product_id;
    private String supplier_id;
    private Integer quantity;
    private java.sql.Date last_updated;

    public InventoryDTO(int anInt, int anInt1, int anInt2, String string) {
    }
}
