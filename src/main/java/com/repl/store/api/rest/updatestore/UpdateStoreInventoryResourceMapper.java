package com.repl.store.api.rest.updatestore;

import com.repl.store.api.dto.StoreDto;

public interface UpdateStoreInventoryResourceMapper {
    StoreDto mapFromRequest(UpdateStoreInventoryRequestResource requestResource);

}
