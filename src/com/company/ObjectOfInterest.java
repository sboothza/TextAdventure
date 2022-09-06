package com.company;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class ObjectOfInterest {
    protected final String _name;
    protected final String _description;
    protected final Map<String, String> _actions;

    public ObjectOfInterest(String name, String description) {
        _name = name;
        _description = description;
        _actions = new HashMap<>();
    }

    public static ObjectOfInterest Trivial(String name) {
        return new ObjectOfInterest(name, "It looks like a " + name);
    }

    public static ObjectOfInterest Trivial(String name, String description) {
        return new ObjectOfInterest(name, description);
    }

    public String getName() {
        return _name;
    }

    public String getDescription() {
        return _description;
    }

    public String getDetails() {
        return MessageFormat.format("{0}.\r\n{1}", _name, _description);
    }

    public void addAction(String name, String description) {
        _actions.put(name, description);
    }

    public String getForAction(String action, Character player) {
        if (_actions.containsKey(action))
            return _actions.get(action);
        return null;
    }
}
