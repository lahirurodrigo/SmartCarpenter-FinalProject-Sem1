package lk.ijse.SmartCarpenter.model;

import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.OrderDetailDto;
import lk.ijse.SmartCarpenter.dto.OrderDto;
import lk.ijse.SmartCarpenter.dto.PlaceOrderDto;
import lk.ijse.SmartCarpenter.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public static double getTotalOrdedersAmount() {
        double total = 0;

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            PreparedStatement pstm = connection.prepareStatement("SELECT qty,unit_price FROM orderDetails");
            ResultSet rs = pstm.executeQuery();

            while(rs.next()){
                total += rs.getInt(1)*rs.getDouble(2);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  total;
    }

    public static List<OrderDetailDto> getOrderDetail(String id) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM orderDetails WHERE o_id = ?");
        pstm.setString(1,id);
        ResultSet rs = pstm.executeQuery();

        List<OrderDetailDto> list = new ArrayList<>();

        while(rs.next()){
            OrderDetailDto dto = new OrderDetailDto(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getDouble(4)
                    );

            list.add(dto);

        }

        return  list;

    }
}