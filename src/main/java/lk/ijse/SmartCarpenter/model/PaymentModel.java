package lk.ijse.SmartCarpenter.model;

import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.FurnitureDto;
import lk.ijse.SmartCarpenter.dto.PaymentDto;
import lk.ijse.SmartCarpenter.dto.tm.PaymentTm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentModel {
    public static boolean savePayment(PaymentDto dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("INSERT INTO payment VALUES (?,?,?,?,?)");

        pstm.setString(1,dto.getId());
        pstm.setDate(2, Date.valueOf(dto.getDate()));
        pstm.setString(3,dto.getType());
        pstm.setDouble(4, dto.getAmount());
        pstm.setString(5,dto.getOId());

        return pstm.executeUpdate()>0;
    }

    public static double getPaidAmount(String id) throws SQLException {

        double total = 0;

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM payment WHERE o_id = ? ");

        pstm.setString(1,id);
        ResultSet rs = pstm.executeQuery();

        while (rs.next()){
            total += rs.getDouble(4);
        }

        return total;

    }

    public static String generateNextId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT p_id FROM payment WHERE p_id LIKE 'P00%' ORDER BY CAST(SUBSTRING(p_id, 4) AS UNSIGNED) DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitPaymentId(resultSet.getString(1));
        }
        return splitPaymentId(null);
    }

    private static String splitPaymentId(String currentOrderId) {
        if (currentOrderId == null || currentOrderId.isEmpty() || !currentOrderId.matches("^P\\d+$")) {
            return "P001";
        } else {
            String numericPart = currentOrderId.substring(3);
            int numericValue = Integer.parseInt(numericPart);

            int nextNumericValue = numericValue + 1;
            String nextNumericPart = String.format("%0" + numericPart.length() + "d", nextNumericValue);

            return "P00" + nextNumericPart;

        }
    }

    public static List<PaymentDto> loadAllItems(String id) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM payment WHERE o_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<PaymentDto> itemList = new ArrayList<>();

        pstm.setString(1,id);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            itemList.add(new PaymentDto(
                    resultSet.getString(1),
                    resultSet.getDate(2).toLocalDate(),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5)
            ));
        }

        return itemList;
    }
}