package com.repl.store.api.dao;

public enum InventoryAction {
    SALE(0),
    REFUND(1),
    DELIVERY(2);

    private int value;

    InventoryAction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
