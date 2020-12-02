package com.repl.store.api;

import com.repl.store.api.dao.InventoryAction;
import com.repl.store.api.dao.StoreInventory;
import com.repl.store.api.dto.StoreDto;
import com.repl.store.api.dto.StoreInventoryItemDto;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public abstract class BaseTest {

    protected List<StoreDto> buildStores() {
        return Collections.singletonList(buildStore());
    }

    protected StoreDto buildStore() {
        StoreDto storeDto = new StoreDto();
        storeDto.setId(0L);

        Map<InventoryAction, List<StoreInventoryItemDto>> inventoryMap = new HashMap<>();
        inventoryMap.put(InventoryAction.DELIVERY, Collections.singletonList(buildInventoryItem()));
        storeDto.setStoreInventoryItems(inventoryMap);
        return storeDto;
    }

    private StoreInventoryItemDto buildInventoryItem() {
        StoreInventoryItemDto inventoryItem = new StoreInventoryItemDto();
        inventoryItem.setName("stuff");
        inventoryItem.setAction(InventoryAction.DELIVERY);
        return inventoryItem;
    }

    protected StoreInventory buildInventory(String inventoryItemName) {
        StoreInventory inventory = new StoreInventory();
        inventory.setItemId(0L);
        inventory.setItemName(inventoryItemName);
        inventory.setAction(InventoryAction.DELIVERY);
        return inventory;
    }

}
