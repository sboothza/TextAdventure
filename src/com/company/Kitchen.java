package com.company;

public class Kitchen extends Room {
    private final LockableDoor _door;

    public Kitchen(String name) {
        super(name,
              "You are in a small galley kitchen overlooking the lounge to the north.\r\nThere is a stove, refrigerator, cupboards, microwave.\r\nThere is a door to the south.");
        addExit(new String[]{"lounge", "north"}, "Lounge");
        addExit(new String[]{"south", "door"}, "Frontyard");
        addTrivial("stove");
        addTrivial("refrigerator");
        addTrivial("cupboard");
        addTrivial("microwave");
        _door = new LockableDoor("Front door", "key");
        this.addObjectOfInterest(_door);
    }

    @Override
    public Room handleNavigate(String trigger, String[] words) {
        Room nextRoom = getExit(trigger);
        if (nextRoom != null && nextRoom.getName()
                                        .equalsIgnoreCase("frontyard")) {
            if (!_door.isOpen()) {
                System.out.println("Must open the door first");
                nextRoom = this;
            }
        }
        return nextRoom;
    }
}
