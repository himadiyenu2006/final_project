module lk.ijse.gdse.pizzahubsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires static lombok;
    requires com.google.protobuf;
    requires net.sf.jasperreports.core;


    opens lk.ijse.gdse.pizzahubsystem.controller to javafx.fxml;
    exports lk.ijse.gdse.pizzahubsystem;
    exports lk.ijse.gdse.pizzahubsystem.dto.tm;
}