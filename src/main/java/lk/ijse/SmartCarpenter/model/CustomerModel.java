package lk.ijse.SmartCarpenter.model;

import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.CustomerDto;
import org.w3c.dom.ls.LSOutput;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {

    public static boolean saveCustomer(CustomerDto dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO customer VALUES (?,?,?,?) ";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getId());
        pstm.setString(2,dto.getName());
        pstm.setString(3,dto.getAddress());
        pstm.setString(4,dto.getTel());

        return pstm.executeUpdate()> 0;
    }

    public static boolean updateCustomer(CustomerDto dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE customer SET name= ?,address=?,email=? WHERE cus_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAddress());
        pstm.setString(3, dto.getTel());
        pstm.setString(4,dto.getId());

        return pstm.executeUpdate()> 0;
    }

    public static List<CustomerDto> getAllCustomers() throws SQLException {
        ArrayList<CustomerDto> list = new ArrayList<>();

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            list.add(new CustomerDto(
                    resultSet.getString("cus_id"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("email")
            ));
        }
        return list;
    }

    public static CustomerDto searchCustomerId(String id) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM customer WHERE cus_id = ?");

        pstm.setString(1,id);
        ResultSet rs = pstm.executeQuery();

        CustomerDto dto = null;

        if (rs.next()){
             dto = new CustomerDto(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)
            );
        }
        return dto;
    }

    public static boolean deleteCustomer(String id) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE cus_id = ?");
        pstm.setString(1,id);

        return pstm.executeUpdate()>0;
    }
}
