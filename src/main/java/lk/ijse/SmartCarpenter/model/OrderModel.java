package lk.ijse.SmartCarpenter.model;

import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.FurnitureDto;
import lk.ijse.SmartCarpenter.dto.OrderDto;
import lk.ijse.SmartCarpenter.dto.tm.CartTm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    public static boolean addOrder(OrderDto dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("INSERT INTO orders VALUES (?,?,?,?,?)");

        pstm.setString(1,dto.getId());
        pstm.setDate(2, Date.valueOf(dto.getPlaceDate()));
        pstm.setDate(3, Date.valueOf(dto.getDueDate()));
        pstm.setInt(4, dto.getDuration());
        pstm.setString(5,dto.getCusId());

        return pstm.executeUpdate()>0;

    }

    public static String generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT o_id FROM orders ORDER BY o_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentOrderId) {
        if(currentOrderId != null) {
            String[] split = currentOrderId.split("O0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "O00" + id;
        } else {
            return "O001";
        }
    }

    public static List<String> loadAllIds() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM orders";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<String> orderList = new ArrayList<>();

        ResultSet rs = pstm.executeQuery();

        while (rs.next()){
            orderList.add(rs.getString(1));
        }
        return  orderList;
    }
}
