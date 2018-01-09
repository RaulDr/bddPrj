package com.AndyRadulescu.store.controller;

import com.AndyRadulescu.store.model.Store;
import com.AndyRadulescu.store.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Controller class. Manipulates the crud operations for the Store model.
 */
@Component
public class StoreController {

    @Autowired
    private StoreRepository storeRepository;

    /**
     * Inserts a store into the database.
     *
     * @param name        field of Store.
     * @param address     field of Store.
     * @param description field of Store.
     */
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

    /**
     * @returns all the stores from the database.
     */
    public List<Store> getAllStores() {
        return (List<Store>) storeRepository.findAll();
    }
}
