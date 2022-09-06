package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListMap<K, V> {
    private final Map<K, List<V>> _maps;

    public ListMap() {
        _maps = new HashMap<>();
    }

    public void put(K key, V value) {
        if (!_maps.containsKey(key)) {
            _maps.put(key, new ArrayList<>());
        }
        List<V> list = _maps.get(key);
        list.add(value);
    }

    public List<V> get(K key) {
        if (_maps.containsKey(key))
            return _maps.get(key);
        return new ArrayList<>();
    }

    public boolean containsKey(K key) {
        return _maps.containsKey(key);
    }
}
