package lk.ijse.SmartCarpenter.model;

import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.ManufacturingDetailDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;

public class ManufacturingDetailModel {
    public static boolean addDetail(ManufacturingDetailDto dtoManu) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO manufacturingDetail VALUES (?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dtoManu.getCode());
        pstm.setString(2,dtoManu.getEmpId());
        pstm.setString(3, String.valueOf(dtoManu.getLabourCost()));
        pstm.setString(4, String.valueOf(dtoManu.getDate()));
        pstm.setInt(5,dtoManu.getQty());

        return pstm.executeUpdate() > 0;
    }

    public static double getSalaryAmount(String id, int month) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        Year currentYear = Year.now();
        int year = currentYear.getValue();
        double amount = 0;

        String sql = "SELECT labour_cost,qty FROM manufacturingDetail WHERE e_id = ? AND YEAR(date) = ? AND MONTH(date) = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);
        pstm.setInt(2,year);
        pstm.setInt(3,month);

        ResultSet rs = pstm.executeQuery();

        while (rs.next()){
            amount += rs.getDouble(1)*rs.getInt(2);
        }

        return amount;
    }

    public static String getLabourCost(String code) throws SQLException {

        String labourCost = null;

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT labour_cost FROM manufacturingDetail WHERE f_code = ? ORDER BY f_code DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,code);
        ResultSet rs = pstm.executeQuery();

        while (rs.next()){
            labourCost = rs.getString(1);
        }

        return labourCost;
    }
}
