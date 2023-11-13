package lk.ijse.SmartCarpenter.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController {

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnFurniture;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnMaterials;

    @FXML
    private JFXButton btnOrder;

    @FXML
    private JFXButton btnPayment;

    @FXML
    private JFXButton btnSalary;

    @FXML
    private JFXButton btnTools;

    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane rootDash;

    @FXML
    private AnchorPane rootVary;

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        Parent rootNew = FXMLLoader.load(getClass().getResource("/view/customer_form.fxml"));
        this.rootVary.getChildren().clear();
        this.rootVary.getChildren().add(rootNew);

    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.centerOnScreen();
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) {

    }

    @FXML
    void btnFurnitureOnAction(ActionEvent event) {

    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {

    }

    @FXML
    void btnMaterialsOnAction(ActionEvent event) {

    }

    @FXML
    void btnOrderOnAction(ActionEvent event) {

    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {

    }

    @FXML
    void btnSalaryOnAction(ActionEvent event) {

    }

    @FXML
    void btnToolsOnAction(ActionEvent event) {

    }
}
