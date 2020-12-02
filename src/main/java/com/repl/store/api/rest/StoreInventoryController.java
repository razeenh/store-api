package com.repl.store.api.rest;

import com.repl.store.api.dto.StoreDto;
import com.repl.store.api.rest.createstore.CreateStoreRequestResource;
import com.repl.store.api.rest.deletestoreinventory.DeleteStoreInventoryRequestResource;
import com.repl.store.api.rest.deletestores.DeleteStoresRequestResource;
import com.repl.store.api.rest.getstore.GetStoreResponseResource;
import com.repl.store.api.rest.updatestore.UpdateStoreInventoryRequestResource;
import com.repl.store.api.service.StoreInventoryService;
import com.repl.store.api.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StoreInventoryController {

    private final StoreInventoryService<StoreDto> inventoryService;
    private final StoreService storeService;
    private StoreInventoryResourceMapper resourceMapper;

    public static final String URI_STORE = "/store";
    public static final String URI_STORE_INVENTORY = URI_STORE + "/inventory";
    public static final String URI_STORES = "/stores";
    public static final String URI_STORE_INVENTORIES = URI_STORES + "/inventories";

    @Autowired
    public StoreInventoryController(StoreInventoryService<StoreDto> inventoryService, StoreService storeService) {
        this.inventoryService = inventoryService;
        this.storeService = storeService;
        this.resourceMapper = new StoreInventoryResourceMapperImpl();
    }

    @PostMapping(value = URI_STORE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createStoreInventory(@Valid @RequestBody CreateStoreRequestResource requestResource) {
        inventoryService.createInventoryItems(Collections.singletonList(resourceMapper.mapFromRequest(requestResource)));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = URI_STORES,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createStoreInventories(@Valid @RequestBody List<CreateStoreRequestResource> requestResources) {
        inventoryService.createInventoryItems(requestResources.stream()
                .map(store -> resourceMapper.mapFromRequest(store))
                .collect(Collectors.toList())
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = URI_STORE_INVENTORY,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStoreInventory(@Valid @RequestBody UpdateStoreInventoryRequestResource requestResource) {
        inventoryService.updateInventoryItems(Collections.singletonList(resourceMapper.mapFromRequest(requestResource)));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = URI_STORE_INVENTORIES,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStoreInventories(@Valid @RequestBody List<UpdateStoreInventoryRequestResource> requestResource) {
        inventoryService.updateInventoryItems(requestResource.stream()
                .map(store -> resourceMapper.mapFromRequest(store))
                .collect(Collectors.toList())
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = URI_STORE + "/{storeId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteStore(@Valid @PathVariable Long storeId) {
        storeService.deleteStores(Collections.singletonList(new StoreDto(storeId)));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = URI_STORES,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteStores(@Valid @RequestBody DeleteStoresRequestResource requestResource) {
        storeService.deleteStores(requestResource.getStoreIds().stream().map(StoreDto::new).collect(Collectors.toList()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = URI_STORE_INVENTORY,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteStoreInventoryItem(@Valid @RequestBody DeleteStoreInventoryRequestResource requestResource) {
        inventoryService.deleteInventoryItems(Collections.singletonList(resourceMapper.mapFromRequest(requestResource)));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = URI_STORE_INVENTORIES,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteStoreInventoryItems(@Valid @RequestBody List<DeleteStoreInventoryRequestResource> requestResources) {
        inventoryService.deleteInventoryItems(requestResources.stream()
                .map(store -> resourceMapper.mapFromRequest(store))
                .collect(Collectors.toList())
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = URI_STORE + "/{storeId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetStoreResponseResource> getStore(@Valid @PathVariable Long storeId) {

        return new ResponseEntity<>(resourceMapper.mapToResponse(storeService.getStore(storeId)), HttpStatus.OK);
    }

    @GetMapping(value = URI_STORES,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GetStoreResponseResource>> getAllStores() {

        return new ResponseEntity<>(storeService.getAllStores()
                .stream()
                .map(store -> resourceMapper.mapToResponse(store)).collect(Collectors.toList()), HttpStatus.OK);
    }
}
