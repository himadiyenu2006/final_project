package lk.ijse.gdse.pizzahubsystem.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ManageDTO {
    private String manageId;
    private String inventoryId;
    private String supplierId;
    private String orderId;
    private int quantity;
    private String supplierName;
    private String supplierContactName;


}


//    public int getInventoryId() {
//        return inventoryId;
//    }
//
//    public void setInventoryId(int inventoryId) {
//        this.inventoryId = inventoryId;
//    }
//
//    public int getManageId() {
//        return manageId;
//    }
//
//    public void setManageId(int manageId) {
//        this.manageId = manageId;
//    }
//
//    public int getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(int orderId) {
//        this.orderId = orderId;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//
//    public String getSupplierContactName() {
//        return supplierContactName;
//    }
//
//    public void setSupplierContactName(String supplierContactName) {
//        this.supplierContactName = supplierContactName;
//    }
//
//    public int getSupplierId() {
//        return supplierId;
//    }
//
//    public void setSupplierId(int supplierId) {
//        this.supplierId = supplierId;
//    }
//
//    public String getSupplierName() {
//        return supplierName;
//    }
//
//    public void setSupplierName(String supplierName) {
//        this.supplierName = supplierName;
//    }
//}
