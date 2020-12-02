package com.repl.store.api.service;

import com.repl.store.api.BaseTest;
import com.repl.store.api.dao.StoreInventoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StoreInventoryServiceImplTest extends BaseTest {

    @Mock
    private StoreInventoryRepository inventoryRepo;
    @Mock
    private StoreService storeService;

    @InjectMocks
    private StoreInventoryServiceImpl service;

    @Test
    void canCreateInventoryItemsForNewStoreSuccessfully() {
        when(storeService.getStore(any())).thenThrow(new EntityNotFoundException());
        when(storeService.createStore(any())).thenReturn(buildStore());
        when(inventoryRepo.findByStore(any())).thenReturn(Stream.of(buildInventory("stuff2")));

        service.createInventoryItems(buildStores());

        verify(storeService, times(1)).getStore(any());
        verify(storeService, times(1)).createStore(any());
        verify(inventoryRepo, times(1)).findByStore(any());
        verify(inventoryRepo, times(1)).saveAll(any());
    }

    @Test
    void canCreateInventoryItemsForExistingStoreSuccessfully() {
        when(storeService.getStore(any())).thenReturn(buildStore());
        when(inventoryRepo.findByStore(any())).thenReturn(Stream.of(buildInventory("stuff2")));

        service.createInventoryItems(buildStores());

        verify(storeService, times(1)).getStore(any());
        verify(storeService, never()).createStore(any());
        verify(inventoryRepo, times(1)).findByStore(any());
        verify(inventoryRepo, times(1)).saveAll(any());
    }

    @Test
    void failsToCreateInventoryItemsIfAlreadyExists() {
        when(storeService.getStore(any())).thenReturn(buildStore());
        when(inventoryRepo.findByStore(any())).thenReturn(Stream.of(buildInventory("stuff")));

        Assertions.assertThrows(EntityExistsException.class,  () -> service.createInventoryItems(buildStores()));

        verify(storeService, times(1)).getStore(any());
        verify(inventoryRepo, times(1)).findByStore(any());
        verify(storeService, never()).createStore(any());
        verify(inventoryRepo, never()).saveAll(any());
    }

    @Test
    void updateInventoryItems() {
        when(storeService.getStore(any())).thenReturn(buildStore());
        when(inventoryRepo.findByStoreAndAction(any(), any())).thenReturn(Stream.of(buildInventory("stuff")));

        service.updateInventoryItems(buildStores());

        verify(storeService, times(1)).getStore(any());
        verify(inventoryRepo, times(1)).findByStoreAndAction(any(), any());
        verify(inventoryRepo, times(1)).saveAll(any());
        verify(inventoryRepo, never()).deleteAll(any());
    }

    @Test
    void failsToUpdateInventoryItemsIfNonExistent() {
        when(storeService.getStore(any())).thenReturn(buildStore());
        when(inventoryRepo.findByStoreAndAction(any(), any())).thenReturn(Stream.empty());

        Assertions.assertThrows(EntityNotFoundException.class,  () -> service.updateInventoryItems(buildStores()));

        verify(storeService, times(1)).getStore(any());
        verify(inventoryRepo, times(1)).findByStoreAndAction(any(), any());
        verify(inventoryRepo, never()).saveAll(any());
        verify(inventoryRepo, never()).deleteAll(any());

    }

    @Test
    void failsToUpdateInventoryItemsIfSpecificItemNonExistent() {
        when(storeService.getStore(any())).thenReturn(buildStore());
        when(inventoryRepo.findByStoreAndAction(any(), any())).thenReturn(Stream.of(buildInventory("stuff2")));

        Assertions.assertThrows(EntityNotFoundException.class,  () -> service.updateInventoryItems(buildStores()));

        verify(storeService, times(1)).getStore(any());
        verify(inventoryRepo, times(1)).findByStoreAndAction(any(), any());
        verify(inventoryRepo, never()).saveAll(any());
        verify(inventoryRepo, never()).deleteAll(any());

    }

    @Test
    void deleteInventoryItems() {
        when(storeService.getStore(any())).thenReturn(buildStore());
        when(inventoryRepo.findByStoreAndAction(any(), any())).thenReturn(Stream.of(buildInventory("stuff")));

        service.deleteInventoryItems(buildStores());

        verify(storeService, times(1)).getStore(any());
        verify(inventoryRepo, times(1)).findByStoreAndAction(any(), any());
        verify(inventoryRepo, never()).saveAll(any());
        verify(inventoryRepo, times(1)).deleteAll(any());
    }
}