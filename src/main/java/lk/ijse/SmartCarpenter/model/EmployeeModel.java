package lk.ijse.SmartCarpenter.model;

import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class EmployeeModel {
    public static String getNextEmployeeId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT e_id FROM employee ORDER BY e_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitEmployeeId(resultSet.getString(1));
        }
        return splitEmployeeId(null);
    }

    private static String splitEmployeeId(String currentOrderId) {
        if(currentOrderId != null) {
            String[] split = currentOrderId.split("E0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "E00" + id;
        } else {
            return "E001";
        }
    }

    public static List<EmployeeDto> getAllEmployeeIds() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        List<EmployeeDto> list = new ArrayList<>();

        while (rs.next()){
            list.add(new EmployeeDto(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getInt(5)
                    )
            );
        }
        return list;
    }

    public static boolean saveEmployee(EmployeeDto dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("INSERT INTO employee VALUES (?,?,?,?,?)");

        pstm.setString(1,dto.getId());
        pstm.setString(2, dto.getPosition());
        pstm.setString(3,dto.getName());
        pstm.setString(4,dto.getGender());
        pstm.setString(5, String.valueOf(dto.getAge()));

        return pstm.executeUpdate() > 0;

    }
}
