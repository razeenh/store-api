package com.repl.store.api.rest.deletestores;

import com.repl.store.api.sdk.rest.RequestResource;
import lombok.Data;

import java.util.List;

@Data
public class DeleteStoresRequestResource implements RequestResource {
    private List<Long> storeIds;
}
