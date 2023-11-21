package lk.ijse.SmartCarpenter.model;

import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.FurnitureDto;
import lk.ijse.SmartCarpenter.dto.ManufacturingDetailDto;

import java.sql.Connection;
import java.sql.SQLException;

public class AddFurnitureModel {
    public static boolean addFurnitureItem(FurnitureDto dto, ManufacturingDetailDto dtoManu) throws SQLException {
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();

            connection.setAutoCommit(false);

            boolean isSaved = FurnitureModel.addItem(dto);

            if (isSaved){
                boolean isAdded = ManufacturingDetailModel.addDetail(dtoManu);
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
            return  false;
        }finally {
            connection.setAutoCommit(true);
        }

        return true;

    }
}
