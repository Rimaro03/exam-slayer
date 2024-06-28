package org.project.generation;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.Component;
import org.project.componentsystem.components.bosses.BossesInfo;
import org.project.core.Game;
import org.project.core.Physics;
import org.project.generation.wavecollapse.Algorithm;
import org.project.generation.wavecollapse.GenerationSettings;
import org.project.items.*;
import org.project.savingsystem.SavingIO;
import org.project.utils.Vec2;
import org.project.utils.Vec2Int;

import java.util.*;

/**
 * A class representing a playable level, it contains all the rooms as a linked graph of rooms.
 * Only one room can be enabled at once, and that is the room the player is currently inside.
 */
@Getter
@Log4j2
public class Level {
    private final List<Room> bossRooms;
    private final Physics physicsEngine;
    private final long seed;
    private Room currentRoom;
    private Room previousRoom;

    private final LinkedList<Item> itemsQueue;
    private final LinkedList<Integer> bossesIdsQueue;

    /**
     * This constructor get as input the start room of the map,
     * all other room are supposed to be linked to each other forming a graph of rooms.
     */
    public Level(Room startRoom, List<Room> bossRooms, long seed) {
        currentRoom = startRoom;
        previousRoom = startRoom;
        this.bossRooms = bossRooms;
        this.physicsEngine = new Physics();
        this.seed = seed;

        itemsQueue = new LinkedList<>();
        Sword sword = new Sword(
                "DiamondSword",
                2,
                "resources/textures/touchable/sword.png",
                "resources/textures/stats/items/sword.png"
        );
        Heart heart = new Heart(
                "Heart",
                0,
                "resources/textures/touchable/heart.png",
                "resources/textures/stats/items/heart.png",
                10
        );
        Pencil pencil = new Pencil(
                "Pencil",
                1,
                "resources/textures/touchable/pencil.png",
                "resources/textures/stats/items/pencil.png"
        );


        for (int i = 0; i < GenerationSettings.ITEM_ROOM_COUNT * 10; i++) {
            itemsQueue.add(heart);
            itemsQueue.add(sword);
            itemsQueue.add(pencil);
        }

        Collections.shuffle(itemsQueue);

        bossesIdsQueue = new LinkedList<>();
        for (int id = 0; id < BossesInfo.IMPLEMENTED_BOSSES; id++)
            bossesIdsQueue.add(id);
        Collections.shuffle(bossesIdsQueue);
    }

    public void loadMapData() {
        Set<Room> allRooms = Algorithm.getConnectedRooms(currentRoom);

        SavingIO savingIO = Game.getSavingIO();
        List<Vec2Int> clearedRooms = savingIO.getVec2IntList("LevelClearedRooms");
        Vec2Int startRoomPosition = savingIO.getVec2Int("LevelStartRoomPosition");

        if (clearedRooms == null || startRoomPosition == null) {
            log.warn("No saved data found for the level");
            return;
        }

        for (Room room : allRooms) {
            if (room.getX() == startRoomPosition.getX() && room.getY() == startRoomPosition.getY()) {
                room.setInitType(Room.InitType.Start);
                currentRoom = room;
            } else if (clearedRooms.contains(new Vec2Int(room.getX(), room.getY()))) {
                room.setCleared(true);
                room.setInitType(Room.InitType.Empty);
            }
        }

        List<Integer> savedBossesIds = Game.getSavingIO().getIntList("BossesIds");

        if (savedBossesIds != null) {
            bossesIdsQueue.clear();
            for (Integer id : savedBossesIds)
                bossesIdsQueue.add(id);
        }
    }


    public void saveMapData() {
        Set<Room> allRooms = Algorithm.getConnectedRooms(currentRoom);

        SavingIO savingIO = Game.getSavingIO();
        List<Vec2Int> clearedRooms = new ArrayList<>();
        Vec2Int startRoomPosition = null;

        for (Room room : allRooms) {
            if (room.isCleared())
                clearedRooms.add(new Vec2Int(room.getX(), room.getY()));
            if (previousRoom.getX() == room.getX() && previousRoom.getY() == room.getY())
                startRoomPosition = new Vec2Int(room.getX(), room.getY());
        }

        savingIO.setVec2IntList("LevelClearedRooms", clearedRooms);
        savingIO.setVec2Int("LevelStartRoomPosition", startRoomPosition);
        savingIO.setLong("LevelSeed", seed);

        savingIO.setIntList("BossesIds", bossesIdsQueue);
    }
    public void destroyAllGameObjects() {
        Set<Room> allRooms = Algorithm.getConnectedRooms(currentRoom);
        for (Room room : allRooms) {
            room.destroyGameObjects();
        }
    }

    /**
     * Change the current room to the one in the specified direction relative to the current.
     *
     * @param direction The direction of the room to change to.
     */
    public void changeRoom(int direction) {
        Room next = currentRoom.getAdjacentRoom(direction);

        currentRoom.setCleared(true);
        previousRoom = currentRoom;

        if (next != null) {
            GameObject player = currentRoom.getGameObject("Player");
            currentRoom.removeGameObject(player);
            currentRoom.setEnabled(false);
            currentRoom = next;

            currentRoom.addGameObject(player);
            currentRoom.setEnabled(true);
        } else {
            throw new RuntimeException("No room in that direction");
        }
    }

    /**
     * Instantiate a game object in the current room at the specified position.
     *
     * @param gameObject The game object to instantiate.
     * @param position   The position to instantiate the game object at.
     */
    public void instantiateGameObject(GameObject gameObject, Vec2 position) {
        gameObject.setPosition(position);
        currentRoom.addGameObject(gameObject);
        gameObject.setEnabled(true);
        gameObject.start();
    }

    public GameObject findGameObject(String name) {
        return currentRoom.getGameObject(name);
    }
    public GameObject[] findGameObjects(String name){
        return currentRoom.getGameObjects(name);
    }

    /**
     * Destroy a game object in the current room.
     *
     * @param gameObject The game object to destroy.
     */
    public void destroyGameObject(GameObject gameObject) {
        gameObject.setEnabled(false);
        gameObject.destroy();
        currentRoom.removeGameObject(gameObject);
    }

    public void addComponentToGameObject(GameObject gameObject, Component component) {
        if(findGameObject(gameObject.getName()) == null)
            log.warn("GameObject not found in the current room! {}", gameObject.getName());

        gameObject.addComponent(component);
        component.start();
    }

    /**
     * Initialize the level by enabling the start room.
     */
    public void init() {
        currentRoom.setEnabled(true);
    }

    /**
     * Performs the game loop on the current room and updates the physics engine.
     */
    public void update() {
        currentRoom.updateGameObjects();
        physicsEngine.update();
    }

    /* ------------------------- QUEUE METHODS ------------------------- */

    public Integer popBossId() {
        return bossesIdsQueue.poll();
    }

    public Item popItem() {
        return itemsQueue.poll();
    }

    public Item getItemByName(String name) {
        for (Item item : itemsQueue) {
            if (item.getName().equals(name))
                return item;
        }
        return null;
    }

    public Item removeItem(Item item) {
        itemsQueue.remove(item);
        return item;
    }
}
