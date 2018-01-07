package com.AndyRadulescu.store.ui;

import com.AndyRadulescu.store.StoreApplication;
import com.AndyRadulescu.store.controller.UserController;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@FXMLController
public class RegisterController {
    @Autowired
    UserController userController;
    @Autowired
    private ApplicationContext context;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfConfirmPassword;

    @FXML
    private Label lbPropNotDefined;

    @FXML
    private TextField tfName;

    @FXML
    private Label lbPasswordMissmatch;

    @FXML
    void onConfimedClick(ActionEvent event) throws IOException {
        String name = tfName.getText();
        String password = tfPassword.getText();
        String confirmedPassword = tfConfirmPassword.getText();
        if (name.compareTo("") == 0 ||
                confirmedPassword.compareTo("") == 0 ||
                password.compareTo("") == 0) {
            lbPropNotDefined.setVisible(true);
        } else {
            lbPropNotDefined.setVisible(false);
            if (password.compareTo(confirmedPassword) == 0) {
                context.getBean(StoreApplication.class);
                lbPasswordMissmatch.setVisible(false);

                userController.registerUser(name, password);

                FXMLLoader loader = FXMLLoader.load(getClass().getResource("/fxmlroot/loginController.fxml"));
                loader.setControllerFactory(context::getBean);
                Parent rootNode = loader.load();
                Scene scene = new Scene(rootNode);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                System.out.println("user registered!");
            } else {
                lbPasswordMissmatch.setVisible(true);
            }
        }
    }

    @FXML
    void onBackClick(ActionEvent event) throws IOException {
        System.out.println("back pressed !");
        Parent loader = FXMLLoader.load(getClass().getResource("/fxmlroot/loginController.fxml"));
        Scene scene = new Scene(loader);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
