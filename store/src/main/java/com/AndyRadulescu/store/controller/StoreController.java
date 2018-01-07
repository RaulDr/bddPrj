package com.AndyRadulescu.store.controller;

import com.AndyRadulescu.store.model.Store;
import com.AndyRadulescu.store.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StoreController {

    @Autowired
    StoreRepository storeRepository;

    public boolean insertStore(String name, String address, String description) {
        Store store = new Store();
        store.setName(name);
        store.setAddress(address);
        store.setDescription(description);
        if (storeRepository.save(store) != null) {
            return true;
        } else {
            return false;
        }
    }
}
