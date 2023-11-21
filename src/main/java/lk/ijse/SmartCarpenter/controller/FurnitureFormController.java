package lk.ijse.SmartCarpenter.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.SmartCarpenter.dto.FurnitureDto;
import lk.ijse.SmartCarpenter.dto.ManufacturingDetailDto;
import lk.ijse.SmartCarpenter.dto.tm.FurnitureTm;
import lk.ijse.SmartCarpenter.model.AddFurnitureModel;
import lk.ijse.SmartCarpenter.model.FurnitureModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class FurnitureFormController implements Initializable {
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
    private TableColumn<?, ?> ColQua;


    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableView<FurnitureTm> tblDetails;

    @FXML
    private JFXComboBox<String> cmbCodeUpdate;

    @FXML
    private JFXComboBox<String> cmbCodeView;

    @FXML
    private JFXComboBox<String> cmbEmpId;

    @FXML
    private JFXComboBox<String> cmbEmpIdUpdate;


    @FXML
    private TextField txtLabourCost;

    @FXML
    private JFXTextField txtLabourCostUpdate;

    @FXML
    private DatePicker dtpDate;

    @FXML
    private DatePicker dtpDateUpdate;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtUniPriceUpdate;

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
        String empId = cmbEmpId.getValue();
        double labourCost = Double.parseDouble(txtLabourCost.getText());
        LocalDate date = dtpDate.getValue();

        if (code.isEmpty() || description.isEmpty() || unitPrice == 0  || qauntity == 0){
            new Alert(Alert.AlertType.ERROR,"Fields empty").showAndWait();
            return;
        }

        FurnitureDto dto = new FurnitureDto(code,description,unitPrice,qauntity);
        ManufacturingDetailDto dtoMan = new ManufacturingDetailDto(code,empId,labourCost,date);

        try {
            boolean isAdded =AddFurnitureModel.addFurnitureItem(dto,dtoMan);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtDescription.clear();
        txtHeight.clear();;
        txtWidth.clear();;
        txtCode.clear();
        txtQuantity.clear();
        txtUnitPrice.clear();
        txtDescriptionUpdate.clear();
        txtDescriptionView.clear();
        txtQuantityUpdate.clear();
        txtQuantityView.clear();
        txtUniPriceUpdate.clear();
        txtUnitPriceView.clear();
        txtLabourCost.clear();
        txtLabourCostUpdate.clear();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String code = cmbCodeView.getValue();

        try {
            boolean isDeleted = FurnitureModel.deleteItem(code);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item deleted!").show();
                loadAllFurnitures();
                loadFurnitureCodes();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Item not deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String code = (String) cmbCodeUpdate.getValue();
        String desc = txtDescriptionUpdate.getText();
        double unitPrice = Double.parseDouble(txtUniPriceUpdate.getText());
        int qty = Integer.parseInt(txtQuantityUpdate.getText());

        FurnitureDto dto = new FurnitureDto(code,desc,unitPrice,qty);

        try {
            boolean isUpdated = FurnitureModel.updateItem(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "item updated").show();
                loadAllFurnitures();
                loadFurnitureCodes();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnViewOnAction(ActionEvent event) {
        String code = (String) cmbCodeView.getValue();

        try {
            FurnitureDto dto = FurnitureModel.getByCode(code);

            if(dto==null){
                new Alert(Alert.AlertType.ERROR,"No records found").showAndWait();
            }

            txtUnitPriceView.setText(String.valueOf(dto.getUnitPrice()));
            txtQuantityView.setText(String.valueOf(dto.getQtyOnHand()));
            txtDescriptionView.setText(dto.getDescription());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearViewOnAction(ActionEvent event) {
        clearFields();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadFurnitureCodes();
        setCellValueFactory();
        loadAllFurnitures();
    }



    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        ColQua.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
    }

    private void loadAllFurnitures() {

        ObservableList<FurnitureTm> obList = FXCollections.observableArrayList();

        try {
            List<FurnitureDto> list = FurnitureModel.loadAllItems();

            for (FurnitureDto dto : list){
                obList.add(new FurnitureTm(
                        dto.getCode(),
                        dto.getQtyOnHand()
                ));
            }
            tblDetails.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    private void loadFurnitureCodes() {
        ObservableList<String> list  = FXCollections.observableArrayList();
        List<FurnitureDto> itemDtos = null;
        try {
            itemDtos = FurnitureModel.loadAllItems();
            for (FurnitureDto dto : itemDtos) {
                list.add(dto.getCode());
            }
            cmbCodeUpdate.setItems(list);
            cmbCodeView.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
}
