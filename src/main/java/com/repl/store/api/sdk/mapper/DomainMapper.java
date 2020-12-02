package com.repl.store.api.sdk.mapper;

import com.repl.store.api.sdk.dao.DomainObject;
import com.repl.store.api.sdk.dto.DataTransferObject;


public interface DomainMapper<T extends DomainObject, D extends DataTransferObject> {
    T from(D dto);

    D to(T entity);
}
