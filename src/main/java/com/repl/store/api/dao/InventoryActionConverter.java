package com.repl.store.api.dao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter(autoApply = true)
public class InventoryActionConverter implements AttributeConverter<InventoryAction, Integer> {

    @Override
    public Integer convertToDatabaseColumn(InventoryAction attribute) {
        if (attribute != null) {
            return attribute.getValue();
        }
        return null;
    }

    @Override
    public InventoryAction convertToEntityAttribute(Integer dbData) {
        if (dbData != null) {
            return Arrays.stream(InventoryAction.values())
                    .filter(action -> action.getValue() == dbData)
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        }
        return null;
    }
}
