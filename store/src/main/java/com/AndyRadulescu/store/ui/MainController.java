package com.AndyRadulescu.store.ui;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

@FXMLController
public class MainController {

    @FXML
    void onBackPressed(ActionEvent event) throws IOException {
        System.out.println("back pressed !");
        Parent loader = FXMLLoader.load(getClass().getResource("/fxmlroot/loginController.fxml"));
        Scene scene = new Scene(loader);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

}
