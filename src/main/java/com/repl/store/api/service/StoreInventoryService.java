package com.repl.store.api.service;

import com.repl.store.api.dto.StoreDto;
import com.repl.store.api.sdk.dto.DataTransferObject;

import java.util.List;

public interface StoreInventoryService<T extends DataTransferObject> {

    void createInventoryItems(List<T> stores);

    void updateInventoryItems(List<StoreDto> stores);

    void deleteInventoryItems(List<StoreDto> stores);
}
