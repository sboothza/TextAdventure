package com.company;

public class TV extends ObjectOfInterest {
    private boolean _switchedOn = false;

    public TV() {
        super("tv", "The tv");
    }

    @Override
    public String getDescription() {
        return _switchedOn ? "The tv is showing the current weather.\r\nIt looks like it's going to get cold." : "The tv is showing absolutely nothing.";
    }

    @Override
    public String getForAction(String action, Character player) {
        switch (action) {
            case "on":
                _switchedOn = true;
                return "Click";

            case "off":
                _switchedOn = false;
                return "Click";
        }
        return null;
    }
}
