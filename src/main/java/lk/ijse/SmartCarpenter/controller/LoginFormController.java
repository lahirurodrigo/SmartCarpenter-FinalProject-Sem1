package lk.ijse.SmartCarpenter.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.SmartCarpenter.dto.CredentialsDto;
import lk.ijse.SmartCarpenter.model.CredentialModel;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private AnchorPane loginAnchorPane;


    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {

        String username = txtUsername.getText();
        String password = txtPassword.getText();

        CredentialsDto dto = new CredentialsDto(username,password);

        try {
            boolean isVerified = CredentialModel.checkCredentials(dto);
            System.out.println(isVerified);

            if (isVerified){

                Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

                Scene scene = new Scene(rootNode);
                Stage stage = (Stage) loginAnchorPane.getScene().getWindow();

                stage.setScene(scene);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

