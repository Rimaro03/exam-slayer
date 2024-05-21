package org.project.generation;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.GameObject;
import org.project.core.Physics;
import org.project.core.Input;
import org.project.core.rendering.Renderer;
import org.project.utils.Vec2;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * A class representing a playable level, it contains all the rooms as a linked graph of rooms.
 * Only one room can be enabled at once, and that is the room the player is currently inside.
 */
@Getter @Log4j2
public class Level {
    private Room currentRoom;
    private final List<Room> bossRooms;

    /** This constructor get as input the start room of the map,
     * all other room are supposed to be linked to each other forming a graph of rooms.
     */
    public Level(Room startRoom, List<Room> bossRooms){
        currentRoom = startRoom;
        this.bossRooms = bossRooms;
    }

    /**
     * Change the current room to the one in the specified direction relative to the current.
     * @param direction The direction of the room to change to.
     */
    public void changeRoom(int direction){
        Room next = currentRoom.getAdjacentRoom(direction);

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
        Physics.update();
    }
}
