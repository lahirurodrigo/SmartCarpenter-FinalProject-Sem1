package lk.ijse.SmartCarpenter.model;

import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.FurnitureDto;
import lk.ijse.SmartCarpenter.dto.OrderDto;
import lk.ijse.SmartCarpenter.dto.tm.CartTm;
import lk.ijse.SmartCarpenter.dto.tm.DashBoardTm;

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

        String sql = "SELECT o_id FROM orders WHERE o_id LIKE 'O00%' ORDER BY CAST(SUBSTRING(o_id, 4) AS UNSIGNED) DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentOrderId) {
        if (currentOrderId == null || currentOrderId.isEmpty() || !currentOrderId.matches("^O\\d+$")) {
            return "O001";
        } else {
            String numericPart = currentOrderId.substring(3);
            int numericValue = Integer.parseInt(numericPart);

            int nextNumericValue = numericValue + 1;
            String nextNumericPart = String.format("%0" + numericPart.length() + "d", nextNumericValue);

            return "O00" + nextNumericPart;

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

    public static List<DashBoardTm> getAllOrders() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT o_id,due_date FROM orders WHERE duration > 0 ORDER BY due_date";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<DashBoardTm> list = new ArrayList<>();

        ResultSet rs = pstm.executeQuery();

        while (rs.next()){
            list.add(new DashBoardTm(
                    rs.getString(1),
                    rs.getString(2)
            ));
        }

        return list;

    }
}
