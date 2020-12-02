package com.repl.store.api.service;

import com.repl.store.api.dao.InventoryAction;
import com.repl.store.api.dao.Store;
import com.repl.store.api.dao.StoreInventory;
import com.repl.store.api.dao.StoreInventoryRepository;
import com.repl.store.api.dto.StoreDto;
import com.repl.store.api.dto.StoreInventoryItemDto;
import com.repl.store.api.mapper.StoreMapperImpl;
import com.repl.store.api.sdk.mapper.DomainMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StoreInventoryServiceImpl implements StoreInventoryService<StoreDto> {

    private final StoreInventoryRepository inventoryRepo;
    private final StoreService storeService;
    private final DomainMapper<Store, StoreDto> storeMapper;
    private final Logger logger = LogManager.getLogger(StoreInventoryServiceImpl.class);

    @Autowired
    public StoreInventoryServiceImpl(StoreInventoryRepository inventoryRepo, StoreService storeService) {
        this.inventoryRepo = inventoryRepo;
        this.storeService = storeService;
        this.storeMapper = new StoreMapperImpl();
    }

    @Override
    @Transactional
    public void createInventoryItems(List<StoreDto> stores) {
        stores.forEach(storeDto -> {
            logger.debug("Creating inventory items for store ID " + storeDto.getId());

            StoreDto store;
            try {
                store = storeService.getStore(storeDto.getId());

            } catch (EntityNotFoundException e) {
                logger.info("Could not find store for ID " + storeDto.getId() + ". Creating...");
                store = storeService.createStore(storeDto.getId());
            }

            if (storeDto.getStoreInventoryItems() != null && !storeDto.getStoreInventoryItems().isEmpty()) {
                Map<InventoryAction, List<StoreInventory>> storeInventory = inventoryRepo.findByStore(storeMapper.from(store))
                        .collect(Collectors.toMap(StoreInventory::getAction, Arrays::asList));

                List<StoreInventory> inventoryList = new ArrayList<>();

                for (StoreInventoryItemDto inventoryItem : storeDto.getStoreInventoryItems().values().stream()
                        .flatMap(Collection::stream).collect(Collectors.toList())) {

                    if (storeInventory.containsKey(inventoryItem.getAction())) {
                        Optional<StoreInventory> storeInventoryOpt = storeInventory.get(inventoryItem.getAction()).stream()
                                .filter(storeItem -> storeItem.getItemName().equalsIgnoreCase(inventoryItem.getName()))
                                .findFirst();

                        if (storeInventoryOpt.isPresent()) {
                            throw new EntityExistsException("Inventory item "+inventoryItem.getName()+" already exists for store " +
                                    storeDto.getId());
                        }
                    }

                    logger.info("Including inventory item " + inventoryItem.getName() + " to store ID " + store.getId());
                    StoreInventory storeInventoryItem = new StoreInventory();
                    storeInventoryItem.setItemId(inventoryItem.getId());
                    storeInventoryItem.setItemName(inventoryItem.getName());
                    storeInventoryItem.setQuantity(inventoryItem.getQuantity());
                    storeInventoryItem.setAction(inventoryItem.getAction());
                    storeInventoryItem.setStore(storeMapper.from(store));
                    inventoryList.add(storeInventoryItem);
                }

                logger.info("Saving inventory items for store ID " + store.getId());
                inventoryRepo.saveAll(inventoryList);
            }
        });
    }

    @Override
    @Transactional
    public void updateInventoryItems(List<StoreDto> stores) {
        updateOrDeleteInventoryItems(stores, true);
    }

    @Override
    @Transactional
    public void deleteInventoryItems(List<StoreDto> stores) {
        updateOrDeleteInventoryItems(stores, false);
    }

    private void updateOrDeleteInventoryItems(final List<StoreDto> stores, final boolean isUpdate) {
        stores.forEach(storeDto -> storeDto.getStoreInventoryItems().forEach((key, value) -> {
            List<StoreInventory> storeInventoryItems = inventoryRepo
                    .findByStoreAndAction(storeMapper.from(storeService.getStore(storeDto.getId())), key)
                    .collect(Collectors.toList());

            List<StoreInventory> storeInventoryItemsToAction = new ArrayList<>();

            if (!storeInventoryItems.isEmpty()) {
                value.forEach(inventoryItem -> {
                    Optional<StoreInventory> storeInventoryItemOpt = storeInventoryItems.stream()
                            .filter(storeInventoryItem -> storeInventoryItem.getItemName().equalsIgnoreCase(inventoryItem.getName()))
                            .findFirst();

                    if (storeInventoryItemOpt.isPresent()) {
                        StoreInventory storeInventoryItem = storeInventoryItemOpt.get();

                        if (isUpdate) {
                            storeInventoryItem.setItemId(inventoryItem.getId());
                            storeInventoryItem.setQuantity(inventoryItem.getQuantity());
                        }

                        storeInventoryItemsToAction.add(storeInventoryItem);

                    } else {    //Inventory item doesn't exist
                        logger.error("Could not find inventory item "+ inventoryItem.getName() + " for store ID " + storeDto.getId());
                        throw new EntityNotFoundException("Inventory item " + inventoryItem.getName() + " does not exist for store "+ storeDto.getId());
                    }
                });

            } else {    //No inventory items for the store and action exists
                logger.error("Could not find any inventory items for store ID " + storeDto.getId());
                throw new EntityNotFoundException("Could not find any inventory items for store " + storeDto.getId());
            }

            if (isUpdate) {
                logger.info("Saving inventory items for store " + storeDto.getId());
                inventoryRepo.saveAll(storeInventoryItemsToAction);

            } else {
                logger.info("Deleting inventory items for store " + storeDto.getId());
                inventoryRepo.deleteAll(storeInventoryItemsToAction);
            }
        }));
    }
}
