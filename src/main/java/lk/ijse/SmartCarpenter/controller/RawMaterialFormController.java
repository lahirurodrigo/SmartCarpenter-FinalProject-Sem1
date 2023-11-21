package lk.ijse.SmartCarpenter.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class RawMaterialFormController {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnAddToStock;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXComboBox<?> cmbCodeEdit;

    @FXML
    private JFXComboBox<?> cmbCodeManage;

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableView<?> tblStock;

    @FXML
    private TextField txtCategory;

    @FXML
    private JFXTextField txtCategoryEdit;

    @FXML
    private JFXTextField txtCategoryManage;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtQuantityManage;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private JFXTextField txtUnitPriceEdit;

    @FXML
    private JFXTextField txtUnitPriceManage;

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

}
