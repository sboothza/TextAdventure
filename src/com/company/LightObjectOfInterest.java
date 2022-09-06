package com.company;

public class LightObjectOfInterest extends ObjectOfInterest {
    private boolean _light;

    public LightObjectOfInterest(String name) {
        super(name, "");
        _light = false;
    }

    public boolean getLight() {
        return _light;
    }

    @Override
    public String getDetails() {
        return _light ? "The light is on" : "The light is off";
    }

    @Override
    public String getForAction(String action, Character player) {
        switch (action) {
            case "on":
                _light = true;
                return "Click";

            case "off":
                _light = false;
                return "Click";
        }
        return null;
    }
}
