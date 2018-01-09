package com.AndyRadulescu.store.ui;


import com.AndyRadulescu.store.model.User;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class CommentController implements ApplicationContextAware, Initializable {

    private User user;
    @Autowired
    private ApplicationContext context;
    @FXML
    private ListView<String> lvComments;
    @FXML
    private TextField tfNewComment;
    @FXML
    private Label lbNoComment;

    @FXML
    void onAddComment(MouseEvent event) {
        String comment = tfNewComment.getText();
        if (comment.compareTo("") == 0) {
            lbNoComment.setVisible(true);
        } else {
            lbNoComment.setVisible(false);
            ObservableList<String> commentList = lvComments.getItems();
            commentList.add(comment);
            lvComments.setItems(commentList);
        }
    }

    @FXML
    void onBackPressed(MouseEvent event) throws IOException {
        loading(event, SavedItems.mainString);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlroot/" + whereToGo + "Controller.fxml"));
        loader.setControllerFactory(this::createControllerForType);
        Parent rootNode = loader.load();
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = LoginController.getUser();
    }
}
