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

        String sql = "SELECT s_id FROM salary ORDER BY s_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitSalaryId(resultSet.getString(1));
        }
        return splitSalaryId(null);
    }

    private static String splitSalaryId(String currentOrderId) {
        if(currentOrderId != null) {
            String[] split = currentOrderId.split("S0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "S00" + id;
        } else {
            return "S001";
        }
    }

    public static boolean saveSalary(SalaryDto dto) throws SQLException {

        Connection connection =DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("INSERT  INTO salary VALUES (?,?,?,?)");

        pstm.setString(1, dto.getEId());
        pstm.setString(2,dto.getEId());
        pstm.setInt(3,dto.getMonth());
        pstm.setDouble(4,dto.getAmount());

        return pstm.executeUpdate() > 0 ;

    }
}
