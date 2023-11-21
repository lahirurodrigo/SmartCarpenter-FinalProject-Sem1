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

            if (isAdded){
                boolean isSaved = FurnitureModel.updateItem(list);

                if (isSaved){
                    boolean isUpdated = OrderDetailModel.updateOrderDetail(dto);

                    if (isUpdated){
                        connection.commit();
                    }else{
                        connection.rollback();
                    }
                }else{
                    connection.rollback();
                }
            }else{
                connection.rollback();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            connection.setAutoCommit(true);
        }
        return true;
    }
}
