package model;

import lk.ijse.gdse.pizzahubsystem.dto.SupplierDTO;
import lk.ijse.gdse.pizzahubsystem.dto.tm.SupplierTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {

    private Util.CrudUtil CrudUtil;

    public String getNextSupplierId() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select supplier_id from supplier order by supplier_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("S%03d", newIdIndex);
        }
        return "S001";
    }

    public boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "insert into supplier(supplier_id, Supplier_name,contact_name, contact_number, address) values (?,?,?,?,?)",
                supplierDTO.getSupplier_id(),
                supplierDTO.getSupplier_name(),
                supplierDTO.getContact_name(),
                supplierDTO.getContact_number(),
                supplierDTO.getAddress()
        );
    }


    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select * from supplier");

        ArrayList<SupplierDTO> supplierDTOS = new ArrayList<>();

        while (rst.next()) {
            SupplierDTO supplierDTO = new SupplierDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            supplierDTOS.add(supplierDTO);
        }
        return supplierDTOS;
    }

    public boolean updateSupplier(SupplierTM supplierDTO) throws SQLException {
        return Util.CrudUtil.execute(
                "update supplier set Supplier_name=?,contact_name=?, contact_number=?, address=? where supplier_id=?",
                supplierDTO.getSupplier_name(),
                supplierDTO.getContact_name(),
                supplierDTO.getContact_number(),
                supplierDTO.getAddress(),
                supplierDTO.getSupplier_id()
        );
    }


    public boolean deleteSupplier(String supplierId) throws SQLException {
        return Util.CrudUtil.execute("delete from supplier where supplier_id=?", supplierId);
    }


    public ArrayList<String> getAllSupplierIds() throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select supplier_id from supplier");

        ArrayList<String> supplierIds = new ArrayList<>();

        while (rst.next()) {
            supplierIds.add(rst.getString(1));
        }

        return supplierIds;
    }


    public SupplierDTO findById(String selectedSupId) throws SQLException {
        ResultSet rst = Util.CrudUtil.execute("select * from supplier where supplier_id=?", selectedSupId);

        if (rst.next()) {
            return new SupplierDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }
}
