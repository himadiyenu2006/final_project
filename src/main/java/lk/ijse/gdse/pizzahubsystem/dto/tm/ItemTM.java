package lk.ijse.gdse.pizzahubsystem.dto.tm;

import javafx.beans.binding.BooleanExpression;
import javafx.beans.value.ObservableValue;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ItemTM {
    private String item_id;
    private String name;
    private int quantity;
    private double price;


    public ItemTM(int id, String name, double price, String quantity) {
    }

    public BooleanExpression itemIdProperty() {
        return null;
    }

    public ObservableValue<String> itemNameProperty() {
        return null;
    }

    public BooleanExpression itemPriceProperty() {
        return null;
    }

    public ObservableValue<String> itemQuantityProperty() {
        return null;
    }

    public void setItemName(String text) {
        return;
    }

    public void setItemPrice(double v) {
        return;
    }

    public void setItemQuantity(String text) {
        return;
    }

    public char[] getItemId() {
        return null;
    }

    public String getItemName() {
        return null;
    }

    public char[] getItemPrice() {
        return null;
    }

    public String getItemQuantity() {
        return null;
    }
}
