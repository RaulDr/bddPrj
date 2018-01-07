package com.AndyRadulescu.store.ui;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.IOException;

@FXMLController
public class MainController implements ApplicationContextAware {

    @Autowired
    private ApplicationContext context;

    @FXML
    void onBackPressed(MouseEvent event) throws IOException {
        loading(event, SavedItems.loginString);
    }

    @FXML
    void onKeyPressed(KeyEvent event) throws IOException {
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

    private void loading(Event event, String whereToGo) throws IOException {
        System.out.println("back pressed !");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlroot/" + whereToGo + "Controller.fxml"));
        loader.setControllerFactory(this::createControllerForType);
        Parent rootNode = loader.load();
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

}
