package lk.ijse.SmartCarpenter.model;

import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.CredentialsDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CredentialModel {
    public static boolean checkCredentials(CredentialsDto dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM credentials");

        ResultSet rs = pstm.executeQuery();

        while(rs.next()){
            System.out.println(rs.getString(1)+"   "+rs.getString(2));
            if (rs.getString(1).equals(dto.getUserName()) && rs.getString(2).equals(dto.getPassword())){

                return true;
            }
        }
        return false;
    }

    public static CredentialsDto getCredentials() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM credentials");

        ResultSet rs = pstm.executeQuery();

        System.out.println("3333333333333");
        CredentialsDto dto =null;
        while(rs.next()) {
            dto = new CredentialsDto(rs.getString(1), rs.getString(2));
        }

        return dto;
    }
}
