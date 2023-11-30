package lk.ijse.SmartCarpenter.model;

import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.FurnitureDto;
import lk.ijse.SmartCarpenter.dto.ManufacturingDetailDto;

import java.sql.Connection;
import java.sql.SQLException;

public class UpdateFurnitureModel {

    public static boolean updateItem(FurnitureDto dto, ManufacturingDetailDto dtoMan) throws SQLException {

        Connection connection = null;

        try {

            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            System.out.println("111111111111");
            boolean isUpdated = FurnitureModel.updateItem(dto);
            System.out.println("2222222222222");
            if (isUpdated){
                System.out.println("3333333333333");
                boolean isAdded = ManufacturingDetailModel.addDetail(dtoMan);
                System.out.println("4444444444444");
                if (isAdded){
                    connection.commit();
                }else{
                    connection.rollback();
                    return false;
                }

            }else{
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            return false;
        }finally {
            connection.setAutoCommit(true);
        }

        return true;
    }
}
