package com.company;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Room {
    protected final String _name;
    protected final String _description;
    protected final Map<String, Room> _exits;
    protected final Map<String, String> _tempExits;
    protected final Map<String, ObjectOfInterest> _objectsOfInterest;
    protected final List<InventoryItem> _inventory;

    public Room(String name, String description) {
        _name = name;
        _description = description;
        _exits = new HashMap<>();
        _tempExits = new HashMap<>();
        _objectsOfInterest = new HashMap<>();
        _inventory = new ArrayList<>();
    }

    public Room handleNavigate(String trigger, String[] words) {
        return getExit(trigger);
    }

    public Room getExit(String exit) {
        if (_exits.containsKey(exit))
            return _exits.get(exit);
        return null;
    }

    public String getDetails() {
        String inventory = "";
        if (!_inventory.isEmpty()) {
            inventory = "\r\nYou see " + _inventory.stream()
                                                   .map(InventoryItem::getName)
                                                   .collect(Collectors.joining(","));
        }
        return MessageFormat.format("You are in {0}.\r\n{1}{2}", _name, _description, inventory);
    }

    public void addExit(String exit, String room) {
        _tempExits.put(exit, room);
    }

    public void addExit(String[] exits, String room) {
        for (String exit : exits)
            _tempExits.put(exit, room);
    }

    public void remapExits(List<Room> rooms) {
        for (Map.Entry<String, String> entry : _tempExits.entrySet()) {
            rooms.stream()
                 .filter(r -> r.getName()
                               .equalsIgnoreCase(entry.getValue()))
                 .findFirst()
                 .ifPresent(room -> _exits.put(entry.getKey(), room));
        }
        _tempExits.clear();
    }

    public String getName() {
        return this._name;
    }

    public ObjectOfInterest getObjectOfInterest(String name) {
        if (_objectsOfInterest.containsKey(name))
            return _objectsOfInterest.get(name);
        return null;
    }

    public void addObjectOfInterest(ObjectOfInterest objectOfInterest) {
        _objectsOfInterest.put(objectOfInterest.getName(), objectOfInterest);
    }

    public void addTrivial(String name) {
        _objectsOfInterest.put(name, ObjectOfInterest.Trivial(name));
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

}
