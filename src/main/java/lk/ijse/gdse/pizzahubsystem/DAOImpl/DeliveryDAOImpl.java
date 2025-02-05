package lk.ijse.gdse.pizzahubsystem.DAOImpl;

import dao.DeliveryDAO;
import lk.ijse.gdse.pizzahubsystem.dto.DeliveryDTO;
import Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveryDAOImpl implements DeliveryDAO {

    @Override
    public ArrayList<DeliveryDTO> getAllDeliveries() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM delivery");
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

    @Override
    public String getNextDeliveryId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT delivery_id FROM delivery ORDER BY delivery_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("D%03d", newIdIndex);
        }
        return "D001";
    }

    @Override
    public boolean saveDelivery(DeliveryDTO deliveryDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO delivery (delivery_id, order_id, delivery_address, delivery_date, delivery_status, employee_id) VALUES (?, ?, ?, ?, ?, ?)",
                deliveryDTO.getDelivery_id(),
                deliveryDTO.getOrder_id(),
                deliveryDTO.getDelivery_address(),
                deliveryDTO.getDelivery_date(),
                deliveryDTO.getDelivery_status(),
                deliveryDTO.getEmployee_id()
        );
    }

    @Override
    public boolean updateDelivery(DeliveryDTO deliveryDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE delivery SET order_id=?, delivery_address=?, delivery_date=?, delivery_status=?, employee_id=? WHERE delivery_id=?",
                deliveryDTO.getOrder_id(),
                deliveryDTO.getDelivery_address(),
                deliveryDTO.getDelivery_date(),
                deliveryDTO.getDelivery_status(),
                deliveryDTO.getEmployee_id(),
                deliveryDTO.getDelivery_id()
        );
    }

    @Override
    public boolean deleteDelivery(String deliveryId) throws SQLException {
        return CrudUtil.execute("DELETE FROM delivery WHERE delivery_id=?", deliveryId);
    }

    @Override
    public DeliveryDTO findById(String deliveryId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM delivery WHERE delivery_id=?", deliveryId);

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
