package com.repl.store.api.service;

import com.repl.store.api.dto.StoreDto;

import java.util.List;

public interface StoreService {

    StoreDto createStore(Long id);

    StoreDto getStore(Long id);

    List<StoreDto> getAllStores();

    void deleteStores(List<StoreDto> stores);
}
