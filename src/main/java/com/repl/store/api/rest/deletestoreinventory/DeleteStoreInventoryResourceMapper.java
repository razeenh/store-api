package com.repl.store.api.rest.deletestoreinventory;

import com.repl.store.api.dto.StoreDto;

public interface DeleteStoreInventoryResourceMapper {
    StoreDto mapFromRequest(DeleteStoreInventoryRequestResource requestResource);

}
