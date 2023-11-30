package lk.ijse.SmartCarpenter.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.SmartCarpenter.model.OrderDetailModel;
import lk.ijse.SmartCarpenter.model.OrderModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    @FXML
    private PieChart pieChart;

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
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        Parent rootNew = FXMLLoader.load(getClass().getResource("/view/employee_form.fxml"));
        this.rootVary.getChildren().clear();
        this.rootVary.getChildren().add(rootNew);
    }

    @FXML
    void btnFurnitureOnAction(ActionEvent event) throws IOException {
        Parent rootNew = FXMLLoader.load(getClass().getResource("/view/furniture_form.fxml"));
        this.rootVary.getChildren().clear();
        this.rootVary.getChildren().add(rootNew);
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {

    }

    @FXML
    void btnMaterialsOnAction(ActionEvent event) throws IOException {
        Parent rootNew = FXMLLoader.load(getClass().getResource("/view/rawMaterial_form.fxml"));
        this.rootVary.getChildren().clear();
        this.rootVary.getChildren().add(rootNew);
    }

    @FXML
    void btnOrderOnAction(ActionEvent event) throws IOException {
        Parent rootNew = FXMLLoader.load(getClass().getResource("/view/order_form.fxml"));
        this.rootVary.getChildren().clear();
        this.rootVary.getChildren().add(rootNew);
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {
        Parent rootNew = null;
        try {
            rootNew = FXMLLoader.load(getClass().getResource("/view/payment_form.fxml"));
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

        this.rootVary.getChildren().clear();
        this.rootVary.getChildren().add(rootNew);
    }

    @FXML
    void btnSalaryOnAction(ActionEvent event) {

    }

    @FXML
    void btnToolsOnAction(ActionEvent event) {

    }

    void initializePieChart(){

        double totalOrderAmount = OrderDetailModel.getTotalOrdedersAmount();

        System.out.println(totalOrderAmount);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Category A", 30),
                new PieChart.Data("Category B", 20),
                new PieChart.Data("Category C", 50)
        );

        // Setting data to the pie chart
        pieChart.setData(pieChartData);

        // Adding values to the pie chart
        for (PieChart.Data data : pieChart.getData()) {
            data.getNode().setOnMouseEntered(e -> {
                // Display value on hover
                double value = data.getPieValue();
                // You can show the value in a Tooltip or any other UI element
                System.out.println("Value: " + value);
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializePieChart();
    }
}
