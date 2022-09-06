package com.company;

public class Echo extends ObjectOfInterest {
    private boolean _isHappy = false;

    public Echo() {
        super("dog", "Small puppy, sitting on the floor.");
    }

    @Override
    public String getDescription() {
        return _isHappy ? "Small puppy, wagging his tail." : "Small puppy, sitting on the floor.";
    }

    @Override
    public String getForAction(String action, Character player) {
        if (action == "pet") {
            _isHappy = true;
            return "Woof!";
        }
        return null;
    }

}
