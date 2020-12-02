package com.repl.store.api.rest;

import com.repl.store.api.dao.InventoryAction;
import com.repl.store.api.dto.StoreDto;
import com.repl.store.api.dto.StoreInventoryItemDto;
import com.repl.store.api.rest.createstore.CreateStoreRequestResource;
import com.repl.store.api.rest.createstore.InventoryDetails;
import com.repl.store.api.rest.deletestoreinventory.DeleteStoreInventoryRequestResource;
import com.repl.store.api.rest.getstore.GetStoreResponseResource;
import com.repl.store.api.rest.updatestore.UpdateStoreInventoryRequestResource;
import com.repl.store.api.sdk.util.CollectionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class StoreInventoryResourceMapperImpl implements StoreInventoryResourceMapper {

    @Override
    public StoreDto mapFromRequest(CreateStoreRequestResource requestResource) {
        StoreDto storeDto = new StoreDto();
        storeDto.setId(requestResource.getStoreId());

        Map<InventoryAction, List<StoreInventoryItemDto>> inventoryItems = new HashMap<>();
        if (!CollectionUtil.isEmpty(requestResource.getDelivery())) {
            inventoryItems.putAll(mapItemsForAction(InventoryAction.DELIVERY, requestResource.getDelivery()));
        }

        if (!CollectionUtil.isEmpty(requestResource.getRefund())) {
            inventoryItems.putAll(mapItemsForAction(InventoryAction.REFUND, requestResource.getRefund()));
        }

        if (!CollectionUtil.isEmpty(requestResource.getSale())) {
            inventoryItems.putAll(mapItemsForAction(InventoryAction.SALE, requestResource.getSale()));
        }

        storeDto.setStoreInventoryItems(inventoryItems);
        return storeDto;
    }

    private Map<InventoryAction, List<StoreInventoryItemDto>> mapItemsForAction(final InventoryAction action, final List<InventoryDetails> items) {
        return items.stream()
                .map(deliveryItem -> {
                    StoreInventoryItemDto inventoryItemDto = new StoreInventoryItemDto();
                    inventoryItemDto.setAction(action);
                    inventoryItemDto.setId(deliveryItem.getItemId());
                    inventoryItemDto.setName(deliveryItem.getItemName());
                    inventoryItemDto.setQuantity(deliveryItem.getQuantity());
                    return inventoryItemDto;
                })
                .collect(groupingBy(StoreInventoryItemDto::getAction));
    }
    @Override
    public GetStoreResponseResource mapToResponse(StoreDto dto) {
        GetStoreResponseResource responseResource = new GetStoreResponseResource();
        responseResource.setStoreId(dto.getId());

        dto.getStoreInventoryItems().forEach((key, value) -> {

            switch (key) {
                case DELIVERY:
                    responseResource.setDelivery(value.stream().map(this::buildInventoryDetails).collect(Collectors.toList()));
                    break;

                case SALE:
                    responseResource.setSale(value.stream().map(this::buildInventoryDetails).collect(Collectors.toList()));
                    break;

                case REFUND:
                    responseResource.setRefund(value.stream().map(this::buildInventoryDetails).collect(Collectors.toList()));
                    break;
            }
        });

        return responseResource;
    }

    private InventoryDetails buildInventoryDetails(StoreInventoryItemDto itemDto) {
        InventoryDetails inventoryDetails = new InventoryDetails();
        inventoryDetails.setItemId(itemDto.getId());
        inventoryDetails.setItemName(itemDto.getName());
        inventoryDetails.setQuantity(itemDto.getQuantity());
        return inventoryDetails;
    }

    @Override
    public StoreDto mapFromRequest(DeleteStoreInventoryRequestResource requestResource) {
        return mapFromRequest((CreateStoreRequestResource)requestResource);
    }

    @Override
    public StoreDto mapFromRequest(UpdateStoreInventoryRequestResource requestResource) {
        return mapFromRequest((CreateStoreRequestResource)requestResource);
    }
}
