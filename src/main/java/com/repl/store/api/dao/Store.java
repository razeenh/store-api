package com.repl.store.api.dao;

import com.repl.store.api.sdk.dao.DomainObject;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Store implements DomainObject {
    @Id
    private Long id;

    @Version
    private Integer version;

    @OneToMany(
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "store")
    private List<StoreInventory> storeInventoryItems;
}
