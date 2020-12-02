package com.repl.store.api.dto;

import com.repl.store.api.dao.InventoryAction;
import com.repl.store.api.sdk.dto.DataTransferObject;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class StoreDto implements DataTransferObject {

    private Long id;
    private Integer version;

    private Map<InventoryAction, List<StoreInventoryItemDto>> storeInventoryItems;

    public StoreDto() {}

    public StoreDto(Long id) {
        this.id = id;
    }
}
