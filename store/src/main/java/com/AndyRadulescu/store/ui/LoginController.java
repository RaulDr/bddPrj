package com.AndyRadulescu.store.ui;

import com.AndyRadulescu.store.controller.UserController;
import com.AndyRadulescu.store.model.User;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.IOException;

@FXMLController
public class LoginController implements ApplicationContextAware, SavedItems {
    public static User user;
    @Autowired
    private UserController userController;
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

    public static User getUser() {
        return user;
    }

    private void onLogin(Event event) throws IOException {
        String userName = tfUserName.getText();
        String password = tfPassword.getText();
        user = userController.getUserByName(userName);
        if (userName.compareTo("") != 0 && password.compareTo("") != 0) {
            lbFiledNotCompleted.setVisible(false);
            if (user != null && password.compareTo(user.getPassword()) == 0) {
                lbNotFound.setVisible(false);
                System.out.println("it matched!");
                loading(event, SavedItems.mainString);
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
    void onLoggedInClick(ActionEvent event) throws IOException {
        onLogin(event);
    }

    @FXML
    void onRegisterClick(ActionEvent event) throws IOException {
        loading(event, SavedItems.registerString);
    }

    private Object createControllerForType(final Class<?> type) {
        return context.getBean(type);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (this.context != null) {
            return;
        }
        this.context = applicationContext;
    }

    @FXML
    void onEnterPressed(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            onLogin(keyEvent);
        }

    }

    private void loading(Event event, String whereToGo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlroot/" + whereToGo + "Controller.fxml"));
        loader.setControllerFactory(this::createControllerForType);
        Parent rootNode = loader.load();
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}

