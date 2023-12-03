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

        String sql = "SELECT e_id FROM employee WHERE e_id LIKE 'E00%' ORDER BY CAST(SUBSTRING(e_id, 4) AS UNSIGNED) DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitEmployeeId(resultSet.getString(1));
        }
        return splitEmployeeId(null);
    }

    private static String splitEmployeeId(String currentOrderId) {
        if (currentOrderId == null || currentOrderId.isEmpty() || !currentOrderId.matches("^E\\d+$")) {
            return "E001";
        } else {
            String numericPart = currentOrderId.substring(3);
            int numericValue = Integer.parseInt(numericPart);

            int nextNumericValue = numericValue + 1;
            String nextNumericPart = String.format("%0" + numericPart.length() + "d", nextNumericValue);

            return "E00" + nextNumericPart;

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

    public static String getEmployeeName(String id) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT name FROM employee WHERE e_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);
        ResultSet rs = pstm.executeQuery();

        String name = null;

        while (rs.next()){
            name = rs.getString(1);
        }
        return name;
    }

    public static String getTotalEmployees() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee";
        PreparedStatement pstm = connection.prepareStatement(sql);

        int count = 0;

        ResultSet rs = pstm.executeQuery();

        while (rs.next()){
            count++;
        }

        return String.valueOf(count);

    }
}
