package com.repl.store.api.rest.getstore;

import com.repl.store.api.rest.createstore.InventoryDetails;
import com.repl.store.api.sdk.rest.ResponseResource;
import lombok.Data;

import java.util.List;

@Data
public class GetStoreResponseResource implements ResponseResource {
    
    private Long storeId;

    private List<InventoryDetails> refund;

    private List<InventoryDetails> delivery;

    private List<InventoryDetails> sale;
}
