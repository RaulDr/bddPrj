package com.AndyRadulescu.store.controller;

import com.AndyRadulescu.store.model.Store;
import com.AndyRadulescu.store.model.User;
import com.AndyRadulescu.store.repository.StoreRepository;
import com.AndyRadulescu.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    StoreRepository storeRepository;

    public User getUser(long id) {
        return userRepository.findUserById(id);
    }

    public User getUserByName(String name) {
        return userRepository.findUserByUserName(name);
    }

    public boolean registerUser(String name, String password) {
        User user = new User();
        user.setUserName(name);
        user.setPassword(password);
        userRepository.save(user);
        return true;
    }

    public Set<Store> getAllStores(long id) {
        User user = userRepository.findUserById(id);
        return storeRepository.findAllByUsers(user);
    }

    public boolean subscribeToStore(long idUser, String storeName) {
        User user = userRepository.findUserById(idUser);
        Store store = storeRepository.findStoreByName(storeName);

        user.getStores().add(store);
        store.getUsers().add(user);

        userRepository.save(user);
        return true;
    }

    public boolean removeStore(long idUser, String storeName) {
        User user = userRepository.findUserById(idUser);
        Store store = storeRepository.findStoreByName(storeName);
        System.out.println(store);
        user.getStores().remove(storeRepository.findStoreByName(storeName));
        userRepository.save(user);
        return true;
    }

}
