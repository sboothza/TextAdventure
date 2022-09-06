package com.company;

public class MainBedroomCupboard extends ObjectOfInterest {
    private boolean _lightOn = false;

    public MainBedroomCupboard() {
        super("cupboard", "");
    }

    @Override
    public String getDescription() {
        return _lightOn ? "The cupboard is brightly lit.\r\nYou can see clothes hanging on the left.\r\nThere are other clothes in shelves on the right." : "The cupboard is dark.\r\nYou can vaguely make out the shapes of clothes.\r\nYou see a switch on the side.";
    }

    @Override
    public String getForAction(String action, Character player) {
        switch (action) {
            case "on":
                _lightOn = true;
                return "Click";

            case "off":
                _lightOn = false;
                return "Click";
        }
        return null;
    }
}
