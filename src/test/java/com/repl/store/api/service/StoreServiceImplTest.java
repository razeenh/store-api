package com.repl.store.api.service;

import com.repl.store.api.BaseTest;
import com.repl.store.api.dao.Store;
import com.repl.store.api.dao.StoreRepository;
import com.repl.store.api.dto.StoreDto;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StoreServiceImplTest  extends BaseTest {

    @Mock
    private StoreRepository repo;

    @InjectMocks
    private StoreServiceImpl service;

    @Test
    public void canCreateStore() {
        Store store = buildStoreEntity();
        when(repo.save(any())).thenReturn(store);

        StoreDto storeDto = service.createStore(0L);
        Assert.assertEquals(store.getId(), storeDto.getId());

        verify(repo, times(1)).save(any());
    }

    @Test
    public void canGetStore() {
        Store store = buildStoreEntity();
        when(repo.findById(anyLong())).thenReturn(Optional.of(store));

        StoreDto storeDto = service.getStore(0L);

        Assert.assertEquals(store.getId(), storeDto.getId());
        verify(repo, times(1)).findById(any());
    }

    @Test
    public void canFailToGetStoreIfNonExistent() {
        when(repo.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,  () -> service.getStore(0L));
        verify(repo, times(1)).findById(any());
    }

    @Test
    public void canGetAllStores() {
        Store store = buildStoreEntity();

        when(repo.findAll()).thenReturn(Collections.singletonList(store));

        List<StoreDto> allStores = service.getAllStores();

        Assert.assertNotNull(allStores);
        Assert.assertEquals(1, allStores.size());
        Assert.assertEquals(store.getId(), allStores.get(0).getId());
    }

    @Test
    public void canDeleteStores() {
        when(repo.findById(anyLong())).thenReturn(Optional.of(buildStoreEntity()));

        service.deleteStores(buildStores());

        verify(repo, times(1)).findById(any());
        verify(repo, times(1)).delete(any());
    }

    @Test
    public void canFailToDeleteStoresIfNonExistent() {
        when(repo.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,  () -> service.deleteStores(buildStores()));
        verify(repo, times(1)).findById(any());
        verify(repo, never()).delete(any());
    }

    private Store buildStoreEntity() {
        Store store = new Store();
        store.setId(0L);
        return store;
    }
}