package lk.ijse.SmartCarpenter.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.SmartCarpenter.db.DbConnection;
import lk.ijse.SmartCarpenter.dto.EmployeeDto;
import lk.ijse.SmartCarpenter.dto.SalaryDto;
import lk.ijse.SmartCarpenter.model.EmployeeModel;
import lk.ijse.SmartCarpenter.model.SalaryModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.lang.model.element.NestingKind;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

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
    private JFXButton btnPrint;

    @FXML
    private JFXComboBox<String> cmbGender;

    @FXML
    private JFXComboBox<String> cmbMonthManage;

    @FXML
    private JFXComboBox<String> cmbSIdView;

    @FXML
    private JFXComboBox<?> cmbEIdManage;

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

        boolean isValid = validateEmployee(id, name,position,txtAge.getText());
        if (isValid == false){
            return;
        }

        EmployeeDto dto = new EmployeeDto(id,name,position,gender,age);

        try {
            boolean isSaved = EmployeeModel.saveEmployee(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "employee saved!").show();
                clear();
                generateNextEmployeeId();
            }else {
                new Alert(Alert.AlertType.ERROR, "error").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clear() {
        txtName.clear();
        txtAge.clear();
        txtPosition.clear();
    }

    private boolean validateEmployee(String id,String name,String position,String age){

        boolean matches = Pattern.matches("[E][0-9]{3,}",id);
        if (!matches) {
            new Alert(Alert.AlertType.ERROR,"Invalid employee id").showAndWait();
            return false;
        }

        boolean matches1 = Pattern.matches("[A-Za-z\\s]{2,}",name);
        if (!matches1){
            new Alert(Alert.AlertType.ERROR,"Invalid Customer name").showAndWait();
            return false;
        }

        boolean matches2 = Pattern.matches("[A-Za-z]+",position);
        if (!matches2){
            new Alert(Alert.AlertType.ERROR,"Invalid Customer Address").showAndWait();
            return false;
        }

        boolean matches3 = Pattern.matches("[0-9]{2}",age);
        if (!matches3){
            new Alert(Alert.AlertType.ERROR,"Invalid Customer Address").showAndWait();
            return false;
        }

        return true;
    }

    @FXML
    void btnSaveSalaryOnAction(ActionEvent event) {

        String sId = lblSalaryIdManage.getText();
        String eId = (String) cmbEIdManage.getValue();
        int month = Integer.parseInt(cmbMonthManage.getValue());
        double amount = Double.parseDouble(txtAmountManage.getText());

        SalaryDto dto = new SalaryDto(sId,eId,month,amount);

        try {
            boolean isSaved = SalaryModel.saveSalary(dto);

            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "salary saved!").show();
                clearSalaryFields();
                generateNextSalaryId();
            }else {
                new Alert(Alert.AlertType.ERROR, "error").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void clearSalaryFields() {
        txtAmountManage.clear();
    }

    @FXML
    void btnViewSalaryOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateNextSalaryId();
        generateNextEmployeeId();
        setCmbGenders();
        loadAllEmpIds();
        setMonths();

    }

    private void setMonths() {

        ObservableList<String> obList =FXCollections.observableArrayList();
        for (int i = 1; i <= 12; i++) {
            obList.add(String.valueOf(i));
        }

        cmbMonthManage.setItems(obList);
    }

    private void loadAllEmpIds() {

        ObservableList obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> dtoList = EmployeeModel.getAllEmployeeIds();

            for (EmployeeDto dto: dtoList){
                obList.add(dto.getId());
            }
            cmbEIdManage.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCmbGenders() {
        ObservableList<String > obList = FXCollections.observableArrayList();

        obList.add("male");
        obList.add("female");

        cmbGender.setItems(obList);
    }

    private void generateNextEmployeeId() {
        try {
            lblId.setText(EmployeeModel.getNextEmployeeId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextSalaryId() {
        try {
            lblSalaryIdManage.setText(SalaryModel.getNextSalaryId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnPrintOnAction(ActionEvent event) throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/Employee_details.jrxml");

        JasperDesign load = JRXmlLoader.load(resourceAsStream);

        JasperReport jasperReport = JasperCompileManager.compileReport(load);

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                null,
                DbConnection.getInstance().getConnection()
        );

        JasperViewer.viewReport(jasperPrint,false);

    }
}
