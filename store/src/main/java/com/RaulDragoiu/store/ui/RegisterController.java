package com.RaulDragoiu.store.ui;

import com.RaulDragoiu.store.controller.UserController;
import de.felixroske.jfxsupport.FXMLView;
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

@FXMLView
public class RegisterController implements ApplicationContextAware {
    @Autowired
    private UserController userController;
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
        onRegister(event);
    }

    private void onRegister(Event event) throws IOException {
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
                lbPasswordMissmatch.setVisible(false);
                userController.registerUser(name, password);
                loading(event, SavedItems.loginString);
            } else {
                lbPasswordMissmatch.setVisible(true);
            }
        }
    }

    @FXML
    void onBackClick(ActionEvent event) throws IOException {
        loading(event, SavedItems.loginString);
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
    void onKeyPressed(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            onRegister(keyEvent);
        }
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            loading(keyEvent, SavedItems.loginString);
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
