package org.project.generation;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.GameObject;
import org.project.core.Game;
import org.project.core.Physics;
import org.project.generation.wavecollapse.Algorithm;
import org.project.savingsystem.SavingIO;
import org.project.utils.Vec2;
import org.project.utils.Vec2Int;

import java.util.*;
import java.util.List;

/**
 * A class representing a playable level, it contains all the rooms as a linked graph of rooms.
 * Only one room can be enabled at once, and that is the room the player is currently inside.
 */
@Getter @Log4j2
public class Level {
    private Room currentRoom;
    private final List<Room> bossRooms;
    private final Physics physicsEngine;

    private final long seed;
    private Room previousRoom;

    /** This constructor get as input the start room of the map,
     * all other room are supposed to be linked to each other forming a graph of rooms.
     */
    public Level(Room startRoom, List<Room> bossRooms, long seed){
        currentRoom = startRoom;
        previousRoom = startRoom;
        this.bossRooms = bossRooms;
        this.physicsEngine = new Physics();
        this.seed = seed;
    }
    public void updateToSavedData(){
        Set<Room> allRooms = Algorithm.getConnectedRooms(currentRoom);

        SavingIO savingIO = Game.getSavingIO();
        List<Vec2Int> clearedRooms = savingIO.getVec2IntList("LevelClearedRooms");
        Vec2Int startRoomPosition = savingIO.getVec2Int("LevelStartRoomPosition");

        if(clearedRooms == null || startRoomPosition == null){
            log.warn("No saved data found for the level");
            return;
        }

        for(Room room : allRooms){
            if(room.getX() == startRoomPosition.getX() && room.getY() == startRoomPosition.getY()){
                room.setInitType(Room.InitType.Start);
                currentRoom = room;
            }
            else if(clearedRooms.contains(new Vec2Int(room.getX(), room.getY()))){
                room.setCleared(true);
                room.setInitType(Room.InitType.Empty);
            }
        }
    }
    public void save(){
        Set<Room> allRooms = Algorithm.getConnectedRooms(currentRoom);

        SavingIO savingIO = Game.getSavingIO();
        List<Vec2Int> clearedRooms = new ArrayList<>();
        Vec2Int startRoomPosition = null;

        for(Room room : allRooms){
            if(room.isCleared())
                clearedRooms.add(new Vec2Int(room.getX(), room.getY()));
            if(previousRoom.getX() == room.getX() && previousRoom.getY() == room.getY())
                startRoomPosition = new Vec2Int(room.getX(), room.getY());
        }

        savingIO.setVec2IntList("LevelClearedRooms", clearedRooms);
        savingIO.setVec2Int("LevelStartRoomPosition", startRoomPosition);
        savingIO.setLong("LevelSeed", seed);
    }
    /**
     * Change the current room to the one in the specified direction relative to the current.
     * @param direction The direction of the room to change to.
     */
    public void changeRoom(int direction){
        Room next = currentRoom.getAdjacentRoom(direction);

        currentRoom.setCleared(true);
        previousRoom = currentRoom;

        if(next != null){
            GameObject player = currentRoom.getGameObject("Player");
            currentRoom.removeGameObject(player);
            currentRoom.setEnabled(false);
            currentRoom = next;

            currentRoom.setEnabled(true);
            currentRoom.addGameObject(player);
        } else {
            throw new RuntimeException("No room in that direction");
        }
    }

    /**
     * Instantiate a game object in the current room at the specified position.
     * @param gameObject The game object to instantiate.
     * @param position The position to instantiate the game object at.
     */
    public void instantiateGameObject(GameObject gameObject, Vec2 position){
        gameObject.setPosition(position);
        currentRoom.addGameObject(gameObject);
    }

    /**
     * Destroy a game object in the current room.
     * @param gameObject The game object to destroy.
     */
    public void destroyGameObject(GameObject gameObject){
        currentRoom.removeGameObject(gameObject);
    }

    /**
     * Initialize the level by enabling the start room.
     */
    public void init(){
        currentRoom.setEnabled(true);
    }
    /**
     * Performs the game loop on the current room and updates the physics engine.
     */
    public void update(){
        currentRoom.updateGameObjects();
        physicsEngine.update();
    }
}
