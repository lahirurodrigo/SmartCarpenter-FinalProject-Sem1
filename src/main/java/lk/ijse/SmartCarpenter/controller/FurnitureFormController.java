package lk.ijse.SmartCarpenter.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.SmartCarpenter.dto.FurnitureDto;
import lk.ijse.SmartCarpenter.model.CustomerModel;
import lk.ijse.SmartCarpenter.model.FurnitureModel;

import java.sql.SQLException;

public class FurnitureFormController {
    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnClearUpdate;

    @FXML
    private JFXButton btnClearView;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnView;

    @FXML
    private JFXButton btndelete;

    @FXML
    private JFXComboBox<?> cmbCodeUpdate;

    @FXML
    private JFXComboBox<?> cmbCodeView;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDescription;

    @FXML
    private JFXTextField txtDescriptionUpdate;

    @FXML
    private JFXTextField txtDescriptionView;

    @FXML
    private TextField txtHeight;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtQuantityUpdate;

    @FXML
    private TextField txtQuantityView;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private JFXTextField txtUnitPriceView;

    @FXML
    private TextField txtWidth;

    @FXML
    void btnAddOnAction(ActionEvent event) {

        String code = txtCode.getText();
        String description = txtWidth.getText()+"*"+txtHeight.getText()+" "+txtDescription.getText();
        double unitPrice = Double.valueOf(txtUnitPrice.getText());
        int qauntity = Integer.parseInt(txtQuantity.getText());

        if (code.isEmpty() || description.isEmpty() || unitPrice == 0  || qauntity == 0){
            new Alert(Alert.AlertType.ERROR,"Fields empty").showAndWait();
            return;
        }

        FurnitureDto dto = new FurnitureDto(code,description,unitPrice,qauntity);

        try {
            boolean isSaved = FurnitureModel.addItem(dto);

            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Added successfully").showAndWait();
            }
            else{
                new Alert(Alert.AlertType.ERROR,"Error").showAndWait();
                //new Alert(Alert.AlertType.ERROR,"Error").showAndWait();
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }


    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void btnViewOnAction(ActionEvent event) {

    }
}
