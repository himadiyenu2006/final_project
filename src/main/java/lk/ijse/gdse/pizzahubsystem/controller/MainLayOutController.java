
package lk.ijse.gdse.pizzahubsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

class MainLayoutController implements Initializable {

    @FXML
    private AnchorPane content;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/CustomerView.fxml");
    }

    @FXML
    void navigateToCustomerPage(ActionEvent event) {
        navigateTo("/view/CustomerView.fxml");
    }

    @FXML
    void navigateToOrderPage(ActionEvent event) {
        navigateTo("/view/OrderView.fxml");
    }

    @FXML
    void navigateToUserPage(ActionEvent event) {
        navigateTo("/view/UserView.fxml");
    }

    @FXML
    void navigateToDepartmentPage(ActionEvent event) {
        navigateTo("/view/DepartmentView.fxml");
    }

    public void navigateTo(String fxmlPath) {
        try {
            content.getChildren().clear();

            AnchorPane load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));

            load.prefWidthProperty().bind(content.widthProperty());
            load.prefHeightProperty().bind(content.heightProperty());

            content.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load page!").show();
        }
    }
}
