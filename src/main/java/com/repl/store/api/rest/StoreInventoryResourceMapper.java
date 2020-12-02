package com.repl.store.api.rest;

import com.repl.store.api.rest.createstore.CreateStoreResourceMapper;
import com.repl.store.api.rest.deletestoreinventory.DeleteStoreInventoryResourceMapper;
import com.repl.store.api.rest.getstore.GetStoreResourceMapper;
import com.repl.store.api.rest.updatestore.UpdateStoreInventoryResourceMapper;

public interface StoreInventoryResourceMapper extends CreateStoreResourceMapper, GetStoreResourceMapper, DeleteStoreInventoryResourceMapper,
        UpdateStoreInventoryResourceMapper {
}
