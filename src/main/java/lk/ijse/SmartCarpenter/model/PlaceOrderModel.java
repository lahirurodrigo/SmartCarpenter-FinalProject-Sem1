package lk.ijse.SmartCarpenter.model;

import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.FurnitureDto;
import lk.ijse.SmartCarpenter.dto.OrderDto;
import lk.ijse.SmartCarpenter.dto.PlaceOrderDto;
import lk.ijse.SmartCarpenter.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PlaceOrderModel {

    public static boolean placeOrder(PlaceOrderDto dto) throws SQLException {

        OrderDto oDto = dto.getDto();
        List<CartTm> list = dto.getList();

        Connection connection = null ;

        try {
            connection = DbConnection.getInstance().getConnection();

            connection.setAutoCommit(false);

            boolean isAdded = OrderModel.addOrder(oDto);
            System.out.println(isAdded);

            if (isAdded){
                boolean isSaved = FurnitureModel.updateItem(list);
                System.out.println(isSaved);

                if (isSaved){
                    System.out.println("123456");
                    boolean isUpdated = OrderDetailModel.updateOrderDetail(dto);
                    System.out.println(isUpdated);


                    if (isUpdated){
                        connection.commit();
                        connection.setAutoCommit(true);
                        return true;
                    }else{
                        connection.rollback();
                        connection.setAutoCommit(true);
                        return false;
                    }
                }else{
                    connection.rollback();
                }
            }else{
                connection.rollback();
            }

        } catch (SQLException e) {
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
        }
        return false;
    }
}
