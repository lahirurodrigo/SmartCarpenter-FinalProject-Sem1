package lk.ijse.SmartCarpenter.model;

import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.ManufacturingDetailDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ManufacturingDetailModel {
    public static boolean addDetail(ManufacturingDetailDto dtoManu) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO manufacturingDetail VALUES (?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dtoManu.getCode());
        pstm.setString(2,dtoManu.getEmpId());
        pstm.setString(3, String.valueOf(dtoManu.getLabourCost()));
        pstm.setString(4, String.valueOf(dtoManu.getDate()));

        return pstm.executeUpdate() > 0;
    }
}
