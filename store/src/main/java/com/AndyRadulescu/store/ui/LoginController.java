package com.AndyRadulescu.store.ui;

import com.AndyRadulescu.store.controller.UserController;
import com.AndyRadulescu.store.model.User;
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
public class LoginController {
    @Autowired
    UserController userController;
    @Autowired
    private ApplicationContext context;

    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfUserName;
    @FXML
    private Label lbNotFound;
    @FXML
    private Label lbFiledNotCompleted;

    @FXML
    void onLoggedInClick(ActionEvent event) throws IOException {

        String userName = tfUserName.getText();
        String password = tfPassword.getText();
        User user = userController.getUserByName(userName);
        if (userName.compareTo("") != 0 && password.compareTo("") != 0) {
            lbFiledNotCompleted.setVisible(false);
            if (user != null && password.compareTo(user.getPassword()) == 0) {
                lbNotFound.setVisible(false);
                System.out.println("it matched!");
//                Parent loader = FXMLLoader.load(getClass().getResource("/fxmlroot/mainController.fxml"));
//                Scene scene = new Scene(loader);
//                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                stage.setScene(scene);
            } else {
                System.out.println("credentials don't match");
                lbNotFound.setVisible(true);
            }
            System.out.println("ceva");
        } else {
            lbFiledNotCompleted.setVisible(true);
        }
    }

    @FXML
    void onRegisterClick(ActionEvent event) throws IOException {
        Parent loader = FXMLLoader.load(getClass().getResource("/fxmlroot/registerController.fxml"));
        Scene scene = new Scene(loader);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}

