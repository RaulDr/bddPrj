package com.AndyRadulescu.store.controller;

import com.AndyRadulescu.store.model.User;
import com.AndyRadulescu.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserController {

    @Autowired
    UserRepository userRepository;

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
}
