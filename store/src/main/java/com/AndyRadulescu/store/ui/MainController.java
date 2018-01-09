package com.AndyRadulescu.store.ui;

import com.AndyRadulescu.store.controller.StoreController;
import com.AndyRadulescu.store.controller.UserController;
import com.AndyRadulescu.store.model.Store;
import com.AndyRadulescu.store.model.User;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
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
import java.util.Set;

@FXMLController
public class MainController implements ApplicationContextAware, Initializable {

    private User user;
    @Autowired
    private StoreController storeController;
    @Autowired
    private UserController userController;
    @Autowired
    private ApplicationContext context;
    private List<Store> storeList;
    private Set<Store> usersStoreList;


    @FXML
    private ListView<String> lvUsersStores;

    @FXML
    private ListView<String> lvStores;

    @FXML
    void onBackPressed(MouseEvent event) throws IOException {
        loading(event, SavedItems.loginString);
    }

    @FXML
    void onKeyPressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ESCAPE) {
            loading(event, SavedItems.loginString);
        }
    }

    @FXML
    void onCommentsPressed(MouseEvent event) throws IOException {
        loading(event, SavedItems.commentsString);
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
        System.out.println("back pressed !");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlroot/" + whereToGo + "Controller.fxml"));
        loader.setControllerFactory(this::createControllerForType);
        Parent rootNode = loader.load();
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    /**
     * Initializes the UI, and sets the Listeners.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();

        lvStores.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            if (observable.getValue() != null) {
                String[] splitting = observable.getValue().split(". ");
                String storeName = splitting[1];
                userController.subscribeToStore(user.getId(), storeName);
                addSetUsersList();
            }
        }));

        lvUsersStores.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            if (observable.getValue() != null) {
                String[] splitting = observable.getValue().split(": ");
                String storeName = splitting[1];
                userController.removeStore(user.getId(), storeName);
                deleteSetUserList(storeName);
            }
        }));
    }

    /**
     * Rewrites the left and right listViews, according to the event.
     *
     * @param removeStore
     */
    private void deleteSetUserList(String removeStore) {
        ObservableList<String> changeableUserStoreList = FXCollections.observableArrayList();
        System.out.println(changeableUserStoreList);
        storeList = storeController.getAllStores();
        usersStoreList = userController.getAllStores(user.getId());
        for (Store store : usersStoreList) {
            changeableUserStoreList.add("Subscribed to: " + store.getName()); //m-am super chinuit cu un remove.
        }

        lvUsersStores.setItems(changeableUserStoreList);

        ObservableList<String> changeableStoreList = FXCollections.observableArrayList();
        for (int i = 1; i <= storeList.size(); i++) {
            boolean ok = false;
            for (Store store : usersStoreList) {
                if (store.getId() == storeList.get(i - 1).getId()) {
                    ok = true;
                }
            }
            if (!ok) {
                changeableStoreList.add(i + ". " + storeList.get(i - 1).getName());
            }
        }
        lvStores.setItems(changeableStoreList);
    }

    /**
     * Rewrites the left and right listViews, according to the event.
     */
    private void addSetUsersList() {
        ObservableList<String> changeableUserStoreList = FXCollections.observableArrayList();
        storeList = storeController.getAllStores();
        usersStoreList = userController.getAllStores(user.getId());

        for (Store store : usersStoreList) {
            changeableUserStoreList.add("Subscribed to: " + store.getName());
        }
        lvUsersStores.setItems(changeableUserStoreList);
        ObservableList<String> changeableStoreList = FXCollections.observableArrayList();
        for (int i = 1; i <= storeList.size(); i++) {
            boolean ok = false;
            for (Store store : usersStoreList) {
                if (store.getId() == storeList.get(i - 1).getId()) {
                    ok = true;
                }
            }
            if (!ok) {
                changeableStoreList.add(i + ". " + storeList.get(i - 1).getName());
            }
        }
        lvStores.setItems(changeableStoreList);
    }

    /**
     * The very first called method.
     */
    private void init() {
        user = LoginController.getUser();
        addSetUsersList();
        System.out.println(usersStoreList);
        System.out.println(storeList);
    }
}
