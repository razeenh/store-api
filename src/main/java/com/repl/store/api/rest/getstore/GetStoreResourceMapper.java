package com.repl.store.api.rest.getstore;

import com.repl.store.api.dto.StoreDto;

public interface GetStoreResourceMapper {
    GetStoreResponseResource mapToResponse(StoreDto dto);
}
