package lk.ijse.SmartCarpenter.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.SmartCarpenter.dto.OrderDto;
import lk.ijse.SmartCarpenter.dto.PaymentDto;
import lk.ijse.SmartCarpenter.model.OrderDetailModel;
import lk.ijse.SmartCarpenter.model.OrderModel;
import lk.ijse.SmartCarpenter.model.PaymentModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentFormController implements Initializable {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnMake;

    @FXML
    private JFXButton btnView;

    @FXML
    private JFXComboBox<String> cmbOIdDetails;

    @FXML
    private JFXComboBox<String> cmbOidPayments;

    @FXML
    private JFXComboBox<String> cmbType;


    @FXML
    private JFXComboBox<String> cmbOId;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colPayId;

    @FXML
    private DatePicker dtpDate;

    @FXML
    private Label lblPaidDetails;

    @FXML
    private TableView<?> tblPayments;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtBalanceDetails;

    @FXML
    private TextField txtPayId;

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnMakeOnAction(ActionEvent event) {

        String id = txtPayId.getText();
        LocalDate date = dtpDate.getValue();
        String type =  cmbType.getValue();
        double amount = Double.parseDouble(txtAmount.getText());
        String oId = cmbOId.getValue();

        PaymentDto dto = new PaymentDto(id,date,type,amount,oId);

        try {

            boolean isSaved = PaymentModel.savePayment(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "payment saved successfully").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"error").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void btnViewOnAction(ActionEvent event) {

        String id = cmbOIdDetails.getValue();

        try {
            double orderTotal = OrderDetailModel.getTotal(id);
            double paidAmount = PaymentModel.getPaidAmount(id);

            double balance = orderTotal - paidAmount;

            lblPaidDetails.setText(String.valueOf(paidAmount));
            txtBalanceDetails.setText(String.valueOf(balance));

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void cmbTypeOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setOrderIds();
        setTyes();
        dtpDate.setValue(LocalDate.now());
    }

    private void setTyes() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        obList.add("cash");
        obList.add("card");

        cmbType.setItems(obList);

    }

    private void setOrderIds() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> list = OrderModel.loadAllIds();

            for (String id : list){
                obList.add(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cmbOId.setItems(obList);
        cmbOidPayments.setItems(obList);
        cmbOIdDetails.setItems(obList);
    }
}