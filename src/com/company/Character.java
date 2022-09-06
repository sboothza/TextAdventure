package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Character {
    protected final String _name;
    protected final List<InventoryItem> _inventory;

    public Character(String name) {
        _name = name;
        _inventory = new ArrayList<>();
    }

    public String getName() {
        return _name;
    }

    public InventoryItem getItem(String name) {
        return _inventory.stream()
                         .filter(i -> i.getName()
                                       .equalsIgnoreCase(name))
                         .findFirst()
                         .orElse(null);
    }

    public void addItem(InventoryItem item) {
        _inventory.add(item);
    }

    public void removeItem(InventoryItem item) {
        _inventory.remove(item);
    }

    public String getInventory() {
        if (!_inventory.isEmpty()) {
            return _inventory.stream()
                             .map(InventoryItem::getName)
                             .collect(Collectors.joining(","));
        }
        return "nothing";
    }

}
