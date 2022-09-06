package com.company;

public class LockableDoor extends ObjectOfInterest {
    private final String _keyName;
    private boolean _locked = true;
    private boolean _open = false;

    public LockableDoor(String name, String keyName) {
        super(name, "");
        _keyName = keyName;
    }

    @Override
    public String getDescription() {
        if (_locked)
            return "The door is closed and locked";
        if (!_open)
            return "The door is closed";
        return "The door is open";
    }

    @Override
    public String getForAction(String action, Character player) {
        switch (action) {
            case "lock":
                if (_open)
                    return "Close the door first";

                if (player.getItem(_keyName) == null)
                    return "You try to lock the door with your fingers but it has no effect.";

                _locked = true;
                return "The door is locked";

            case "unlock":
                if (player.getItem(_keyName) == null)
                    return "You try to unlock the door with your fingers but it has no effect.";

                _locked = false;
                return "The door is unlocked";

            case "open":
                if (_locked)
                    return "The door is locked";
                _open = true;
                return "The door is open";

            case "close":
                _open = false;
                return "The door is closed";
        }
        return null;
    }

    public boolean isOpen() {
        return _open;
    }
}
