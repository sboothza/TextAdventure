package com.company;

public class InventoryItem {
    protected final String _name;
    protected final String _description;

    public InventoryItem(String name, String description) {
        _name = name;
        _description = description;
    }

    public String getName() {
        return _name;
    }

    public String getDescription() {
        return _description;
    }
}
