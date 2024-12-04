package lk.ijse.gdse.pizzahubsystem.controller;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceDialog;
import lk.ijse.gdse.pizzahubsystem.dto.tm.ItemTM;

import java.util.Collection;

public class TableView<T> {
    public ChoiceDialog<Object> getSelectionModel() {

        return null;
    }

    public Collection<ItemTM> getItems() {
        return null;
    }

    public void refresh() {
    }

    public void setItems(ObservableList<T> itemData) {
    }
}
