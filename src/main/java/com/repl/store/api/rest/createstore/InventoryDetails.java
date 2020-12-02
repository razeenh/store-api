package com.repl.store.api.rest.createstore;

import com.repl.store.api.sdk.validation.SubValidationGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class InventoryDetails {
    @NotEmpty(groups = SubValidationGroup.class)
    private String itemName;

    private Long itemId;

    private Integer quantity;
}
