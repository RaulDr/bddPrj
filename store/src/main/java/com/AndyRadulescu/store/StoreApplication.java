package com.AndyRadulescu.store;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StoreApplication extends AbstractJavaFxApplicationSupport {

    private ConfigurableApplicationContext context;
    private Parent rootNode;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() throws Exception {
        context = SpringApplication.run(StoreApplication.class);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlroot/loginController.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);
        rootNode = fxmlLoader.load();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(rootNode));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        context.close();
    }

}
