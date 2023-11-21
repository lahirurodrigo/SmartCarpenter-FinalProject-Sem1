package lk.ijse.SmartCarpenter.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.SmartCarpenter.dto.CustomerDto;
import lk.ijse.SmartCarpenter.dto.FurnitureDto;
import lk.ijse.SmartCarpenter.dto.tm.CartTm;
import lk.ijse.SmartCarpenter.model.CustomerModel;
import lk.ijse.SmartCarpenter.model.FurnitureModel;
import lk.ijse.SmartCarpenter.model.OrderModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class OrderFormController implements Initializable {

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXButton btnNew;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colCodeInfo;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colQuanInfo;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private DatePicker dtpDue;

    @FXML
    private DatePicker dtpPlaced;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<CartTm> tblOrderCart;

    @FXML
    private TableView<?> tblStockInfo;

    @FXML
    private TextField txtQty;

    private ObservableList<CartTm> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setCellValueFactory();
        generateNextOrderId();
        loadCustomerIds();
        loadItemCodes();

    }

    private void loadItemCodes() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<FurnitureDto> furnitureDtos = FurnitureModel.loadAllItems();
            for (FurnitureDto dto : furnitureDtos) {
                obList.add(dto.getCode());
            }
            cmbItemCode.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> idList = CustomerModel.getAllCustomers();

            for (CustomerDto dto : idList) {
                obList.add(dto.getId());
            }

            cmbCustomerId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextOrderId() {

        String orderId = null;
        try {
            orderId = OrderModel.generateNextOrderId();
            lblOrderId.setText(orderId);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String code = cmbItemCode.getValue();
        String desc = lblDescription.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double total = qty*unitPrice;
        Button btn = new Button("Remove");

        setRemoveBtnAction(btn);
        btn.setCursor(Cursor.HAND);

        CartTm cartTm = new CartTm(code,desc,qty,unitPrice,total,btn);

        if (!obList.isEmpty()) {
            for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
                if (colItemCode.getCellData(i).equals(code)) {
                    int col_qty = (int) colQty.getCellData(i);
                    qty += col_qty;
                    total = unitPrice * qty;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTotal(total);

                    calculateTotal();
                    tblOrderCart.refresh();
                    return;
                }
            }
        }

        obList.add(cartTm);

        tblOrderCart.setItems(obList);

        calculateTotal();
        txtQty.clear();

    }
    private void setRemoveBtnAction(Button btn) {

    }

    private void calculateTotal() {
        double total = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(total));
    }


    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) {
        String id = (String) cmbCustomerId.getValue();
//        CustomerModel customerModel = new CustomerModel();
        try {
            CustomerDto customerDto = CustomerModel.searchCustomerId(id);
            lblCustomerName.setText(customerDto.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) {
        String code = (String) cmbItemCode.getValue();

        txtQty.requestFocus();

        FurnitureDto dto = null;
        try {
            dto = FurnitureModel.searchItem(code);
            lblDescription.setText(dto.getDescription());
            lblUnitPrice.setText(String.valueOf(dto.getUnitPrice()));
            lblQtyOnHand.setText(String.valueOf(dto.getQtyOnHand()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);
    }


}
