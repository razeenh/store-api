package com.repl.store.api.mapper;

import com.repl.store.api.dao.StoreInventory;
import com.repl.store.api.dto.StoreInventoryItemDto;
import com.repl.store.api.sdk.mapper.DomainMapper;

public class StoreInventoryItemMapperImpl implements DomainMapper<StoreInventory, StoreInventoryItemDto> {

    @Override
    public StoreInventory from(StoreInventoryItemDto dto) {
        StoreInventory storeInventory = new StoreInventory();
        storeInventory.setVersion(dto.getVersion());
        storeInventory.setAction(dto.getAction());
        storeInventory.setItemId(dto.getId());
        storeInventory.setItemName(dto.getName());
        storeInventory.setQuantity(dto.getQuantity());

        return storeInventory;
    }

    @Override
    public StoreInventoryItemDto to(StoreInventory entity) {
        StoreInventoryItemDto inventoryRegisterItem = new StoreInventoryItemDto();
        inventoryRegisterItem.setVersion(entity.getVersion());
        inventoryRegisterItem.setAction(entity.getAction());
        inventoryRegisterItem.setId(entity.getItemId());
        inventoryRegisterItem.setName(entity.getItemName());
        inventoryRegisterItem.setQuantity(entity.getQuantity());
        return inventoryRegisterItem;
    }
}
