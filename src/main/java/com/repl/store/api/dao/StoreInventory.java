package com.repl.store.api.dao;

import com.repl.store.api.sdk.dao.DomainObject;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class StoreInventory implements DomainObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(name = "inventory_action")
    private InventoryAction action;

    @Column(name = "item_id")
    private Long itemId;

    @NotNull
    @Column(name = "item_name")
    private String itemName;

    private Integer quantity;
}
