package com.repl.store.api.mapper;

import com.repl.store.api.dao.Store;
import com.repl.store.api.dao.StoreInventory;
import com.repl.store.api.dto.StoreDto;
import com.repl.store.api.dto.StoreInventoryItemDto;
import com.repl.store.api.sdk.mapper.DomainMapper;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class StoreMapperImpl implements DomainMapper<Store, StoreDto> {

    private DomainMapper<StoreInventory, StoreInventoryItemDto> inventoryRegisterMapper;

    public StoreMapperImpl() {
        this.inventoryRegisterMapper = new StoreInventoryItemMapperImpl();
    }

    @Override
    public Store from(StoreDto dto) {
        Store store = new Store();
        store.setId(dto.getId());
        store.setVersion(dto.getVersion());

        if (dto.getStoreInventoryItems() != null) {
            store.setStoreInventoryItems(dto.getStoreInventoryItems().values().stream()
                    .flatMap(Collection::stream)
                    .map(inventoryRegisterMapper::from)
                    .collect(Collectors.toList()));
        }

        return store;
    }

    @Override
    public StoreDto to(Store entity) {
        StoreDto store = new StoreDto();
        store.setId(entity.getId());
        store.setVersion(entity.getVersion());
        if (entity.getStoreInventoryItems() != null) {
            store.setStoreInventoryItems(entity.getStoreInventoryItems().stream().map(inventoryRegisterMapper::to)
                    .collect(groupingBy(StoreInventoryItemDto::getAction)));
        }
        return store;
    }
}
