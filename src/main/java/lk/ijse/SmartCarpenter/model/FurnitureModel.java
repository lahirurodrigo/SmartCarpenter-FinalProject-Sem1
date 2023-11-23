package lk.ijse.SmartCarpenter.model;

import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.FurnitureDto;
import lk.ijse.SmartCarpenter.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FurnitureModel {

    public static boolean addItem(FurnitureDto dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO furniture VALUES (?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getCode());
        pstm.setString(2, dto.getDescription());
        pstm.setString(3, String.valueOf(dto.getUnitPrice()));
        pstm.setString(4, String.valueOf(dto.getQtyOnHand()));

        return pstm.executeUpdate()>0 ;
    }

    public static boolean deleteItem(String code) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("DELETE FROM furniture WHERE f_code=?");

        pstm.setString(1,code);
        return  pstm.executeUpdate()>0;
    }

    public static boolean updateItem(FurnitureDto dto) throws SQLException {

        Connection connection =DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("SELECT qty_on_hand FROM furniture WHERE f_code=?");

        pstm.setString(1, dto.getCode());
        ResultSet resultSet = pstm.executeQuery();

        int qty_on_hand;

        if (resultSet.next()){
            qty_on_hand = resultSet.getInt(1);
        }
        else {
            return false;
        }

        int quantity = qty_on_hand + dto.getQtyOnHand();

        PreparedStatement pstmNew = connection.prepareStatement("UPDATE furniture SET description = ?, unit_price = ?, qty_on_hand = ? WHERE f_code = ?");

        pstmNew.setString(1, dto.getDescription());
        pstmNew.setString(2, String.valueOf(dto.getUnitPrice()));
        pstmNew.setString(3, String.valueOf(quantity));
        pstmNew.setString(4, dto.getCode());

        return pstmNew.executeUpdate()>0;

    }

    public static List<FurnitureDto> loadAllItems() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM furniture";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<FurnitureDto> itemList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            itemList.add(new FurnitureDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            ));
        }

        return itemList;
    }

    public static FurnitureDto getByCode(String code) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM furniture WHERE f_code = ?");

        pstm.setString(1,code);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            return new FurnitureDto(resultSet.getString(1),
            resultSet.getString(2),
            resultSet.getDouble(3),
            resultSet.getInt(4));
        }
        return null;
    }

    public static boolean updateItem(List<CartTm> list) throws SQLException {

        for (CartTm tm : list){
            boolean isUpdated = updateQty(tm.getCode(), tm.getQty());
            if(isUpdated == false){
                return false;
            }
        }
        return true;
    }

    public static boolean updateQty(String code, int qty) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE furniture SET qty_on_hand = qty_on_hand - ? WHERE f_code = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setString(2, code);

        return pstm.executeUpdate() > 0;
    }

    public static FurnitureDto searchItem(String code) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM furniture WHERE f_code = ?");

        pstm.setString(1,code);
        ResultSet rs = pstm.executeQuery();

        FurnitureDto dto = null;

        if(rs.next()){
            dto = new FurnitureDto(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getInt(4)

            );
        }
        return dto;
    }
}
