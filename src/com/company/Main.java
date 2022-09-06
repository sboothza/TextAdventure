package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final List<Room> Rooms = new ArrayList<>();
    public static final Character Player = new Character("Player");
    public static boolean exit = false;
    public static Room CurrentRoom;

    public static void main(String[] args) throws IOException {
        Parser parser = new Parser("choice? ");

        Room room = new Room("Study",
                             "You are in a small, square room with a window to the north and a door to the south.\r\nThere are 2 desks with computers on them, and a cupboard with junk on it.\r\nA small dog is lying on the floor looking at you.");
        room.addExit(new String[]{"door", "south"}, "Passage");
        room.addObjectOfInterest(new Echo());
        room.addTrivial("door");
        room.addTrivial("desk");
        room.addTrivial("cupboard");
        room.addTrivial("computer");
        room.addObjectOfInterest(ObjectOfInterest.Trivial("junk", "It looks like junk"));
        room.addItem(new InventoryItem("Hammer", "Normal diy hammer"));
        room.addItem(new InventoryItem("Key", "Small silver key"));
        Rooms.add(room);

        room = new Room("Passage",
                        "You are in a tiny enclave passage.\r\nThere are doors to the north, west and south.\r\nThe passage opens into a room in the east.");
        room.addExit("north", "Study");
        room.addExit(new String[]{"east", "lounge"}, "Lounge");
        room.addExit("west", "Jonathan's Room");
        room.addExit("south", "Bathroom");
        room.addTrivial("door");
        Rooms.add(room);

        room = new Room("Lounge",
                        "You are in a large room with a couch and a tv.\r\nThere is a sliding door to the north, a small door to the east, and a passage to the west.\r\nThe room opens into a kitchen to the south.");
        room.addExit(new String[]{"west", "passage"}, "Passage");
        room.addExit(new String[]{"south", "kitchen"}, "Kitchen");
        room.addExit("east", "Main Bedroom");
        room.addExit(new String[]{"outside", "north", "door"}, "Backyard");
        room.addObjectOfInterest(new TV());
        room.addTrivial("door");
        room.addTrivial("couch");
        Rooms.add(room);

        room = new JonathanRoom("Jonathan's Room");
        Rooms.add(room);

        room = new Kitchen("Kitchen");
        Rooms.add(room);

        room = new Room("Bathroom",
                        "You are in a small bathroom.\r\nThere is a toilet and basin, and a bath.\r\nThere is a door to the north.");
        room.addExit(new String[]{"door", "north"}, "Passage");
        room.addTrivial("door");
        room.addTrivial("toilet");
        room.addTrivial("basin");
        room.addTrivial("bath");
        Rooms.add(room);

        room = new Room("Main Bedroom",
                        "You are in a bedroom.\r\nThe room is dominated by a large bed.\r\nThere is a window and cupboards.\r\nTo the south is an en-suite bathroom.\r\nTo the west is a door.");
        room.addExit(new String[]{"door", "west"}, "Lounge");
        room.addExit(new String[]{"bathroom", "south"}, "Main Bathroom");
        room.addObjectOfInterest(new MainBedroomCupboard());
        room.addTrivial("door");
        room.addTrivial("bed");
        room.addTrivial("window");
        Rooms.add(room);

        room = new Room("Main Bathroom",
                        "You are in a tiny bathroom.\r\nThere is a basin, toilet and shower.\r\nSwing doors lead to the main bedroom");
        room.addExit(new String[]{"bedroom", "north"}, "Main Bedroom");
        room.addTrivial("basin");
        room.addTrivial("toilet");
        room.addTrivial("shower");
        Rooms.add(room);

        room = new Room("Backyard",
                        "You are outside in a small garden enclosed by high walls.\r\nA sliding door to the south leads indoors.");
        room.addExit(new String[]{"door", "inside", "south", "lounge"}, "Lounge");
        room.addTrivial("door");
        Rooms.add(room);

        room = new Room("Frontyard",
                        "You are outside.\r\nA driveway extends to the south.\r\nYour overwhelming agoraphobia makes it impossible to go farther.");
        room.addExit(new String[]{"north", "door", "kitchen"}, "Kitchen");
        room.addTrivial("door");
        Rooms.add(room);

        Rooms.forEach(r -> r.remapExits(Rooms));

        CurrentRoom = Rooms.stream()
                           .filter(r -> r.getName()
                                         .equalsIgnoreCase("Study"))
                           .findFirst()
                           .orElse(null);

        parser.addListener("inventory", (trigger, words) -> handleInventory(), false);
        parser.addListener("get", (trigger, words) -> handleGet(words), false);
        parser.addListener("drop", (trigger, words) -> handleDrop(words), false);
        parser.addListener("look", (trigger, words) -> handleLook(words), false);
        parser.addListener("help", (trigger, words) -> System.out.println(
                                   "exit/quit to quit\r\nlook to describe\r\ndirection or object to move\r\ninventory to show items\r\npick up/drop item"),
                           false);
        parser.addListeners(new String[]{"exit", "quit"}, (trigger, words) -> exit = true);

        parser.addListener("", (trigger, words) -> handleNavigate(trigger, words), true);

        parser.addOverride("turn on", "on");
        parser.addOverride("turn off", "off");
        parser.addOverride("switch on", "on");
        parser.addOverride("switch off", "off");
        parser.addOverride("look at", "look");
        parser.addOverride("pat", "pet");
        parser.addOverride("move", "");
        parser.addOverride("puppy", "dog");
        parser.addOverride("fridge", "refrigerator");
        parser.addOverride("pick up", "get");

        System.out.println("Welcome to my home!\r\nHave a look around, help for help, quit to exit.\r\n");
        System.out.println(CurrentRoom.getDetails());
        do {
            parser.process();
        }
        while (!exit);
    }

    public static void handleInventory() {
        System.out.println("You are holding " + Player.getInventory());
    }

    public static void handleGet(String[] words) {
        if (words.length > 1) {
            InventoryItem item = CurrentRoom.getItem(words[1]);
            if (item != null) {
                Player.addItem(item);
                CurrentRoom.removeItem(item);
                System.out.println("Picked up " + item.getName());
                return;
            }
        }
        System.out.println("Sorry I have no idea what that means");
    }

    public static void handleDrop(String[] words) {
        if (words.length > 1) {
            InventoryItem item = Player.getItem(words[1]);
            if (item != null) {
                CurrentRoom.addItem(item);
                Player.removeItem(item);
                System.out.println("Dropped " + item.getName());
                return;
            }
        }
        System.out.println("Sorry I have no idea what that means");
    }

    public static void handleLook(String[] words) {
        if (words.length > 1) {
            ObjectOfInterest objectOfInterest = CurrentRoom.getObjectOfInterest(words[1]);
            if (objectOfInterest != null) {
                System.out.println(objectOfInterest.getDescription());
            } else {
                System.out.println("Sorry there is nothing like that here");
            }
        } else {
            System.out.println(CurrentRoom.getDetails());
        }
    }

    public static void handleNavigate(String trigger, String[] words) {
        if (words.length > 1) {
            ObjectOfInterest objectOfInterest = CurrentRoom.getObjectOfInterest(words[1]);
            if (objectOfInterest != null) {
                String action = objectOfInterest.getForAction(words[0], Player);
                if (action != null) {
                    System.out.println(action);
                    return;
                }
            }
        }
        Room nextRoom = CurrentRoom.handleNavigate(trigger, words);
        if (nextRoom != null) {
            CurrentRoom = nextRoom;
            System.out.println(CurrentRoom.getDetails());
            return;
        }

        System.out.println("Sorry I have no idea what that means");
    }
}
