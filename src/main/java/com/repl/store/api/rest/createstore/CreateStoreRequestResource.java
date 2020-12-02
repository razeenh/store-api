package com.repl.store.api.rest.createstore;

import com.repl.store.api.sdk.rest.RequestResource;
import com.repl.store.api.sdk.validation.SubValidationGroup;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import java.util.List;

@Data
public class CreateStoreRequestResource implements RequestResource {

    @NotNull
    private Long storeId;

    @Valid @ConvertGroup(to = SubValidationGroup.class)
    private List<InventoryDetails> refund;

    @Valid @ConvertGroup(to = SubValidationGroup.class)
    private List<InventoryDetails> delivery;

    @Valid @ConvertGroup(to = SubValidationGroup.class)
    private List<InventoryDetails> sale;
}
