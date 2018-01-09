package com.AndyRadulescu.store.repository;

import com.AndyRadulescu.store.model.Store;
import com.AndyRadulescu.store.model.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Set;

public interface StoreRepository extends CrudRepository<Store, Long> {

    @Transactional
    Store findStoreById(long id);

    @Transactional
    Store findStoreByName(String name);

    Set<Store> findAllByUsers(User user);
}
