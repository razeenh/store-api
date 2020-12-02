package com.repl.store.api.dto;

import com.repl.store.api.dao.InventoryAction;
import com.repl.store.api.sdk.dto.DataTransferObject;
import lombok.Data;

@Data
public class StoreInventoryItemDto implements DataTransferObject {
    private Integer version;
    private InventoryAction action;
    private String name;
    private Long id;
    private Integer quantity;
}
