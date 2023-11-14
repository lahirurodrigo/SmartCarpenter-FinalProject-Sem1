package lk.ijse.SmartCarpenter.model;

import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.FurnitureDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FurnitureModel {

    public static boolean addItem(FurnitureDto dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO furniture VALUES (?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getCode());
        pstm.setString(2, dto.getDescription());
        pstm.setString(3, String.valueOf(dto.getUnitPrice()));
        pstm.setString(4, String.valueOf(dto.getQtyOnHand()));

        return pstm.executeUpdate()>0 ;
    }
}
