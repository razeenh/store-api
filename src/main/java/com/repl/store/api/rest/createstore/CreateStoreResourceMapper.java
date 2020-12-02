package com.repl.store.api.rest.createstore;

import com.repl.store.api.dto.StoreDto;

public interface CreateStoreResourceMapper {

    StoreDto mapFromRequest(CreateStoreRequestResource requestResource);
}
