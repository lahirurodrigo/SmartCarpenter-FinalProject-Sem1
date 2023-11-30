package lk.ijse.SmartCarpenter.model;

import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.PaymentDto;

import java.sql.*;

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
}