package com.AndyRadulescu.store.ui;


import com.AndyRadulescu.store.controller.CommentMongoController;
import com.AndyRadulescu.store.model.User;
import com.AndyRadulescu.store.mongoModel.Comment;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@FXMLController
public class CommentController implements ApplicationContextAware, Initializable {

    private User user;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private CommentMongoController commentController;
    @FXML
    private ListView<String> lvComments;
    @FXML
    private TextField tfNewComment;
    @FXML
    private Label lbNoComment;

    @FXML
    void onAddComment(MouseEvent event) {
        onClickKeyPressed();
    }

    @FXML
    void onEscPressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ESCAPE) {
            loading(event, SavedItems.mainString);
        }
    }

    @FXML
    void onEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            onClickKeyPressed();
        }
    }

    /**
     * Verifies if it's anything written in the comment bar. If it is,
     * the comment will be added to the db and the contents in the comment bar
     * will be deleted.
     * If there is nothing in the content bar, a label will be shown.
     */
    private void onClickKeyPressed() {
        String comment = tfNewComment.getText();
        if (comment.compareTo("") == 0) {
            lbNoComment.setVisible(true);
        } else {
            //important code
            lbNoComment.setVisible(false);
            ObservableList<String> commentList = lvComments.getItems();
            commentList.add(comment);
            commentController.addComment(new Comment(comment, String.valueOf(user.getId())));
            lvComments.setItems(commentList);
            tfNewComment.setText("");
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

    /**
     * Loads a new Form, and changes the stage.
     *
     * @param event     the event from where the source is taken.
     * @param whereToGo is the string that combined with "Controller.fxml" creates the name
     *                  of the fxml file, where the application needs to go next.
     * @throws IOException
     */
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
        ObservableList<String> commentStringList = FXCollections.observableArrayList();
        List<Comment> commentObjectList = commentController.getAllComments(String.valueOf(user.getId()));
        System.out.println(commentObjectList);
        for (Comment comment : commentObjectList) {
            commentStringList.add(comment.getBody());
        }
        lvComments.setItems(commentStringList);
    }
}
