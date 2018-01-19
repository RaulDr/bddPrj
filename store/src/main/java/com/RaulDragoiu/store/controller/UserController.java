package com.RaulDragoiu.store.controller;

import com.RaulDragoiu.store.model.Store;
import com.RaulDragoiu.store.model.User;
import com.RaulDragoiu.store.repository.StoreRepository;
import com.RaulDragoiu.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Controller class. Manipulates the crud operations for the User model.
 */
@Component
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StoreRepository storeRepository;

    public User getUser(long id) {
        return userRepository.findUserById(id);
    }

    /**
     * Gets a User by it's name.
     *
     * @param name
     */
    public User getUserByName(String name) {
        return userRepository.findUserByUserName(name);
    }

    /**
     * Creates a new User into the database.
     *
     * @param name     field of User.
     * @param password field of User.
     */
    public boolean registerUser(String name, String password) {
        User user = new User();
        user.setUserName(name);
        user.setPassword(password);
        userRepository.save(user);
        return true;
    }

    /**
     * Returns a list of all the stores
     *
     * @param id
     */
    public Set<Store> getAllStores(long id) {
        User user = userRepository.findUserById(id);
        return storeRepository.findAllByUsers(user);
    }

    /**
     * Creates a relation between a user and a sore. This is a many to many relation so, another
     * table is made in the database.
     *
     * @param idUser
     * @param storeName
     */
    public boolean subscribeToStore(long idUser, String storeName) {
        User user = userRepository.findUserById(idUser);
        Store store = storeRepository.findStoreByName(storeName);

        user.getStores().add(store);

//        store.getUsers().add(user);

        userRepository.save(user);
        return true;
    }

    /**
     * Removes a store form the database. By the userId and the store's name.
     *
     * @param idUser
     * @param storeName
     */
    public boolean removeStore(long idUser, String storeName) {
        User user = userRepository.findUserById(idUser);
        Store store = storeRepository.findStoreByName(storeName);
        System.out.println(store);
        user.getStores().remove(storeRepository.findStoreByName(storeName));
        userRepository.save(user);
        return true;
    }

}
