package lk.ijse.SmartCarpenter.model;

import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.OrderDto;
import lk.ijse.SmartCarpenter.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    public static boolean addOrder(OrderDto dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("INSERT INTO orders VALUES (?,?,?,DATEDIFF(?,?),?,?)");

        pstm.setString(1,dto.getId());
        pstm.setString(2, String.valueOf(dto.getPlaceDate()));
        pstm.setString(3, String.valueOf(dto.getDueDate()));
        pstm.setString(4, String.valueOf(dto.getDueDate()));
        pstm.setString(5, String.valueOf(dto.getPlaceDate()));
        pstm.setString(6, dto.getCusId());

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
}
