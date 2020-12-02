package com.repl.store.api.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface StoreInventoryRepository extends CrudRepository<StoreInventory, Long> {

    Stream<StoreInventory> findByStore(Store store);

    Stream<StoreInventory> findByStoreAndAction(Store store, InventoryAction action);
}
