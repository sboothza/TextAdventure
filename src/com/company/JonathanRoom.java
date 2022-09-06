package com.company;

public class JonathanRoom extends Room {
    private final LightObjectOfInterest _light;

    public JonathanRoom(String name) {
        super(name, "");
        addExit(new String[]{"east", "door"}, "Passage");
        addTrivial("door");
        addTrivial("cupboard");
        addTrivial("bed");
        _light = new LightObjectOfInterest("light");
        this.addObjectOfInterest(_light);
    }

    @Override
    public String getDetails() {
        return _light.getLight() ? "You are in a messy bedroom.\r\nThe curtains are closed, the light is on, and there is a faint smell of puppy.\r\nYou can see a cupboard, and clothes lying on the floor.\r\nThere appears to be some leftover food under the bed.\r\nThere is a door to the east." : "You are in a messy bedroom.\r\nThe curtains are closed, it is dark, and there is a faint smell of puppy.\r\nThere is a door to the east.";
    }
}
