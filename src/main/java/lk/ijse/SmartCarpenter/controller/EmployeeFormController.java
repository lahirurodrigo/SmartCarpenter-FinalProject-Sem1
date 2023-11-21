package lk.ijse.SmartCarpenter.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.SmartCarpenter.model.EmployeeModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {

    @FXML
    private JFXButton BtnDeleteSalary;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnSaveSalary;

    @FXML
    private JFXButton btnViewSalary;

    @FXML
    private JFXComboBox<?> cmbGender;

    @FXML
    private JFXComboBox<?> cmbMonthManage;

    @FXML
    private JFXComboBox<?> cmbSIdView;

    @FXML
    private Label lblId;

    @FXML
    private Label lblMonthView;

    @FXML
    private Label lblSalaryIdManage;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtAmountManage;

    @FXML
    private TextField txtAmountView;

    @FXML
    private JFXTextField txtEIdView;

    @FXML
    private TextField txtEmpIdManage;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPosition;

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteSalaryOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = lblId.getText();
        String name = txtName.getText();
        String position = txtPosition.getText();
        String gender = (String) cmbGender.getValue();
        int age = Integer.parseInt(txtAge.getText());
    }

    @FXML
    void btnSaveSalaryOnAction(ActionEvent event) {

    }

    @FXML
    void btnViewSalaryOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateNextSalaryId();
        generateNextEmployeeId();
    }

    private void generateNextEmployeeId() {
        try {
            lblId.setText(EmployeeModel.getNextEmployeeId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextSalaryId() {
    }
}
