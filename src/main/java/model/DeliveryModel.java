package model;

import lk.ijse.gdse.pizzahubsystem.dto.DeliveryDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveryModel {


    public static ArrayList<DeliveryDTO> getAllDelivery() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select * from delivery");

        ArrayList<DeliveryDTO> deliveryList = new ArrayList<>();

        while (rst.next()) {
            DeliveryDTO deliveryDTO = new DeliveryDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getString(5),
                    rst.getString(6)

            );
            deliveryList.add(deliveryDTO);
        }
        return deliveryList;
    }

    public String getNextDeliveryId() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select delivery_id from delivery order by delivery_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("D%03d", newIdIndex);
        }
        return "D001";
    }

    public boolean saveDelivery(DeliveryDTO deliveryDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "insert into delivery(delivery_id, order_id, delivery_address, delivery_date, delivery_status, employee_id) values (?,?,?,?,?,?)",
                deliveryDTO.getDelivery_id(),
                deliveryDTO.getOrder_id(),
                deliveryDTO.getDelivery_address(),
                deliveryDTO.getDelivery_date(),
                deliveryDTO.getDelivery_status(),
                deliveryDTO.getEmployee_id()
        );
    }

    public ArrayList<DeliveryDTO> getAllDeliveries() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select * from delivery");

        ArrayList<DeliveryDTO> deliveries = new ArrayList<>();

        while (rst.next()) {
            DeliveryDTO deliveryDTO = new DeliveryDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getString(5),
                    rst.getString(6)
                    );
            deliveries.add(deliveryDTO);
        }
        return deliveries;
    }

    public boolean updateDelivery(DeliveryDTO deliveryDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "update delivery set order_id=?, delivery_address=?, delivery_date=?, delivery_status=?, employee_id=? where delivery_id=?",
                deliveryDTO.getOrder_id(),
                deliveryDTO.getDelivery_address(),
                deliveryDTO.getDelivery_date(),
                deliveryDTO.getDelivery_status(),
                deliveryDTO.getEmployee_id(),
                deliveryDTO.getDelivery_id()
        );
    }

    public boolean deleteDelivery(String deliveryId) throws SQLException {
        return Util.CrudUtil.execute("delete from delivery where delivery_id=?", deliveryId);
    }

    public DeliveryDTO findById(String selectedDeliveryId) throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select * from delivery where delivery_id=?", selectedDeliveryId);

        if (rst.next()) {
            return new DeliveryDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getString(5),
                    rst.getString(6)
            );
        }
        return null;
    }

}
