package lk.ijse.SmartCarpenter.model;

import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.SalaryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalaryModel {
    public static String getNextSalaryId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT s_id FROM salary WHERE s_id LIKE 'S00%' ORDER BY CAST(SUBSTRING(s_id, 4) AS UNSIGNED) DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitSalaryId(resultSet.getString(1));
        }
        return splitSalaryId(null);
    }

    private static String splitSalaryId(String currentOrderId) {
        if (currentOrderId == null || currentOrderId.isEmpty() || !currentOrderId.matches("^S\\d+$")) {
            return "S001";
        } else {
            String numericPart = currentOrderId.substring(3);
            int numericValue = Integer.parseInt(numericPart);

            int nextNumericValue = numericValue + 1;
            String nextNumericPart = String.format("%0" + numericPart.length() + "d", nextNumericValue);

            return "S00" + nextNumericPart;

        }
    }

    public static boolean saveSalary(SalaryDto dto) throws SQLException {

        Connection connection =DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("INSERT  INTO salary VALUES (?,?,?,?)");

        pstm.setString(1, dto.getSId());
        pstm.setString(2,dto.getEId());
        pstm.setInt(3,dto.getMonth());
        pstm.setDouble(4,dto.getAmount());

        return pstm.executeUpdate() > 0 ;

    }


    public static double getTotal() {
        double total = 0;

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            PreparedStatement pstm = connection.prepareStatement("SELECT amount FROM salary");
            ResultSet rs = pstm.executeQuery();

            while(rs.next()){
                total += rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  total;
    }

    public static double getAmount(String id, int month) throws SQLException {

        double paidAmount = 0;

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT amount FROM salary WHERE e_id = ? AND month = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);
        pstm.setInt(2,month);
        ResultSet rs = pstm.executeQuery();

        while (rs.next()){
            paidAmount += rs.getDouble(1);
        }

        return paidAmount;
    }
}
