package lk.ijse.SmartCarpenter.model;

import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.RawMaterialDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RawMaterialModel {
    public static boolean saveItem(RawMaterialDto dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO rawMaterial VALUES (?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getCode());
        pstm.setString(2, dto.getCategory());
        pstm.setString(3, String.valueOf(dto.getUnitPrice()));
        pstm.setString(4, String.valueOf(dto.getQty()));

        return pstm.executeUpdate()>0 ;
    }

    public static List<String> getAllCodes() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT r_code FROM rawMaterial";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<String> list = new ArrayList<>();

        ResultSet rs = pstm.executeQuery();

        while (rs.next()){
            list.add(rs.getString(1));
        }
        return  list;
    }

    public static RawMaterialDto getItemByCode(String code) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM rawMaterial WHERE r_code = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        RawMaterialDto dto = null;

        pstm.setString(1,code);

        ResultSet rs = pstm.executeQuery();

        while (rs.next()){
            dto = new RawMaterialDto(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getInt(4)
            );
        }

        return dto;

    }

    public static boolean updateItem(RawMaterialDto dto) throws SQLException {

        Connection connection =DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("SELECT qty_on_hand FROM rawMaterial WHERE r_code=?");

        pstm.setString(1, dto.getCode());
        ResultSet resultSet = pstm.executeQuery();

        int qty_on_hand;

        if (resultSet.next()){
            qty_on_hand = resultSet.getInt(1);
        }
        else {
            return false;
        }

        int quantity = qty_on_hand + dto.getQty();

        PreparedStatement pstmNew = connection.prepareStatement("UPDATE rawMaterial SET category = ?, unit_price = ?, qty_on_hand = ? WHERE r_code = ?");

        pstmNew.setString(1, dto.getCategory());
        pstmNew.setString(2, String.valueOf(dto.getUnitPrice()));
        pstmNew.setString(3, String.valueOf(quantity));
        pstmNew.setString(4, dto.getCode());

        return pstmNew.executeUpdate()>0;
    }


    public static double getTotal() {
        double total = 0;

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            PreparedStatement pstm = connection.prepareStatement("SELECT unit_price,qty_on_hand FROM rawMaterial");
            ResultSet rs = pstm.executeQuery();

            while(rs.next()){
                total += rs.getInt(1)*rs.getDouble(2);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  total;
    }
}
