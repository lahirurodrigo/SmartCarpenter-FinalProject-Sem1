package lk.ijse.SmartCarpenter.model;

import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.OrderDto;
import lk.ijse.SmartCarpenter.dto.PlaceOrderDto;
import lk.ijse.SmartCarpenter.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class OrderDetailModel {

    public static boolean updateOrderDetail(PlaceOrderDto dto) throws SQLException {

        OrderDto oDto = dto.getDto();
        String orderId = oDto.getId();

        List<CartTm> cartTmList = dto.getList();

        for (CartTm tm : cartTmList){
            boolean isSaved = saveOrderDetails(orderId,tm);
            if (isSaved == false){
                return  false;
            }
        }
        return true;
    }

    private static boolean saveOrderDetails(String orderId, CartTm tm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("INSERT INTO orderDetails VALUES (?,?,?,?) ");

        pstm.setString(1,orderId);
        pstm.setString(2,tm.getCode());
        pstm.setInt(3, tm.getQty());
        pstm.setDouble(4, tm.getUnitPrice());

        return pstm.executeUpdate()>0;

    }

    public static double getTotal(String id) throws SQLException {

        double total = 0;

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM orderDetails WHERE o_id = ?");
        pstm.setString(1,id);
        ResultSet rs = pstm.executeQuery();

        while (rs.next()){
            total+= rs.getDouble(4)*rs.getInt(3);
        }
        return total;
    }
}