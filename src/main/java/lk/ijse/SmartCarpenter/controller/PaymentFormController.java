package lk.ijse.SmartCarpenter.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PaymentFormController {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnMake;

    @FXML
    private JFXButton btnView;

    @FXML
    private JFXComboBox<?> cmbOIdDetails;

    @FXML
    private JFXComboBox<?> cmbOidPayments;

    @FXML
    private JFXComboBox<?> cmbType;

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
    private TextField txtOId;

    @FXML
    private TextField txtPayId;

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnMakeOnAction(ActionEvent event) {

    }

    @FXML
    void btnViewOnAction(ActionEvent event) {

    }

    @FXML
    void cmbTypeOnAction(ActionEvent event) {

    }

}