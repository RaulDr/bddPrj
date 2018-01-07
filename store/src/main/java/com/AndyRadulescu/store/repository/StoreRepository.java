package com.AndyRadulescu.store.repository;

import com.AndyRadulescu.store.model.Store;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface StoreRepository extends CrudRepository<Store, Long> {

    @Transactional
    Store findStoreById(long id);
}
