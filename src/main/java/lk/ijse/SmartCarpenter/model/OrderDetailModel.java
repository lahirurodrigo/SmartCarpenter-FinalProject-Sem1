package lk.ijse.SmartCarpenter.model;

import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.OrderDto;
import lk.ijse.SmartCarpenter.dto.PlaceOrderDto;
import lk.ijse.SmartCarpenter.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class OrderDetailModel {

    public static boolean updateOrderDetail(PlaceOrderDto dto) throws SQLException {

        OrderDto oDto = dto.getDto();
        String orderId = oDto.getId();

        List<CartTm> cartTmList = dto.getList();

        for(CartTm tm : cartTmList) {
            if(!saveOrderDetails(orderId, tm)) {
                return false;
            }
        }
        return true;
    }

    private static boolean saveOrderDetails(String orderId, CartTm tm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("INSERT INTO orderDetail VALUES (?,?,?,?) ");

        pstm.setString(1,orderId);
        pstm.setString(2,tm.getCode());
        pstm.setString(3, String.valueOf(tm.getQty()));
        pstm.setString(1, String.valueOf(tm.getUnitPrice()));

        return pstm.executeUpdate()>0;

    }
}