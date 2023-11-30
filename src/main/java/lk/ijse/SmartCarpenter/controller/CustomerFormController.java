package lk.ijse.SmartCarpenter.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.SmartCarpenter.dto.CustomerDto;
import lk.ijse.SmartCarpenter.dto.tm.CustomerTm;
import lk.ijse.SmartCarpenter.model.CustomerModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerFormController {
    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnClearUpdate;

    @FXML
    private JFXButton btnClearView;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnView;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private JFXTextField txtAddressUpdate;

    @FXML
    private JFXTextField txtAddressView;

    @FXML
    private TextField txtId;

    @FXML
    private JFXComboBox<String> cmbIdUpdate;

    @FXML
    private TextField txtIdView;

    @FXML
    private TextField txtName;

    @FXML
    private JFXTextField txtNameUpdate;

    @FXML
    private JFXTextField txtNameView;

    @FXML
    private TextField txtTel;

    @FXML
    private JFXTextField txtTelUpdate;

    @FXML
    private JFXTextField txtTelView;

    private CustomerModel cusModel = new CustomerModel();

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();

        boolean isValid = validateCustomer(id,name,address,tel);
        if (isValid == false){
            return;
        }

        CustomerDto dto = new CustomerDto(id,name,address,tel);

        try {
            boolean isSaved = CustomerModel.saveCustomer(dto);

            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved successfully").showAndWait();
                initialize();
                //tblCustomer.refresh();
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
    void btnUpdateOnAction(ActionEvent event) {

        String id = cmbIdUpdate.getValue();
        String name = txtNameUpdate.getText();
        String address = txtAddressUpdate.getText();
        String tel = txtTelUpdate.getText();

        boolean isValid = validateCustomer(id, name,address,tel);
        if (isValid == false){
            return;
        }

        CustomerDto dto = new CustomerDto(id,name,address,tel);

        try {
            boolean isUpdated = CustomerModel.updateCustomer(dto);

            if (isUpdated){
                initialize();
                new Alert(Alert.AlertType.CONFIRMATION,"Successfully updated").showAndWait();
            }
            else{
                new Alert(Alert.AlertType.ERROR,"error").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validateCustomer(String id,String name,String address,String tel){

        boolean matches = Pattern.matches("[C][0-9]{3,}",id);
        if (!matches) {
            new Alert(Alert.AlertType.ERROR,"Invalid Customer id").showAndWait();
            return false;
        }

        boolean matches1 = Pattern.matches("[A-Za-z]{4,}",name);
        if (!matches1){
            new Alert(Alert.AlertType.ERROR,"Invalid Customer name").showAndWait();
            return false;
        }

        boolean matches2 = Pattern.matches("[A-Za-z]+",address);
        if (!matches2){
            new Alert(Alert.AlertType.ERROR,"Invalid Customer Address").showAndWait();
            return false;
        }

        boolean matches3 = Pattern.matches("[0-9]{10}",tel);
        if (!matches3){
            new Alert(Alert.AlertType.ERROR,"Invalid Customer Address").showAndWait();
            return false;
        }

        return true;
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtName.clear();
        txtId.clear();
        txtAddress.clear();
        txtTel.clear();
    }

    @FXML
    void btnClearUpdateOnAction(ActionEvent event) {
        clearUpdateFields();
    }

    @FXML
    void btnClearViewOnAction(ActionEvent event) {
        txtAddressView.clear();
        txtIdView.clear();
        txtNameView.clear();
    }

    @FXML
    void btnViewOnAction(ActionEvent event) {

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

    public void initialize() {
        setCellValueFactory();
        loadAllCustomer();
        loadCustomerIds();
        clearUpdateFields();
    }

    private void clearUpdateFields() {
        txtNameUpdate.setText("");
        txtAddressUpdate.setText("");
        txtTelUpdate.setText("");
    }

    private void loadCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        List<CustomerDto> list = null;
        try {
            list = CustomerModel.getAllCustomers();

            for(CustomerDto dto : list){
                obList.add(dto.getId());
            }
        cmbIdUpdate.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    private void loadAllCustomer() {

        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> dtoList = CustomerModel.getAllCustomers();

            for (CustomerDto dto : dtoList) {
                obList.add(
                        new CustomerTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getAddress(),
                                dto.getTel()
                        )
                );
            }

            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
