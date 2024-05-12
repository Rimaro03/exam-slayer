package org.project.generation;

import lombok.Getter;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.GameObjectFactory;
import org.project.componentsystem.components.colliders.BoxCollider;
import org.project.componentsystem.components.colliders.Collider;
import org.project.utils.Vec2;

import java.util.ArrayList;

public class Room {
    public static final float SIZE = 15.75f;
    @Getter
    private final ArrayList<GameObject> gameObjects;
    private final Room[] adjacentRooms;
    @Getter
    private boolean initialized;
    @Getter
    private final int x, y;
    public Room(int x, int y) {
        gameObjects = new ArrayList<>();
        adjacentRooms = new Room[4];
        initialized = false;
        this.x = x;
        this.y = y;
    }

    public void setAdjacentRoom(int direction, Room room) {
        if(direction < 0 || direction >= 4) { throw new InvalidDirectionException(); }
        adjacentRooms[direction] = room;
    }
    public Room getAdjacentRoom(int direction) {
        if(direction < 0 || direction >= 4) { throw new InvalidDirectionException(); }
        return adjacentRooms[direction];
    }

    public GameObject getGameObject(String name) {
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getName().equals(name)) {
                return gameObject;
            }
        }
        return null;
    }
    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }
    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }


    public void setEnabled(boolean enabled){
        if(!initialized)
            init();

        for(GameObject go : gameObjects){
            go.setEnabled(enabled);
        }
    }
    /**
     * Initializes the room by creating the setting up the room game objects.
     */
    private void init(){
        if(initialized)
            throw new RuntimeException("Room already initialized");

        // Create room collider-sprite game object
        GameObject roomGameObject = GameObjectFactory.createRoomGameObject();
        gameObjects.add(roomGameObject);

        // Create room door collider game objects
        for (int direction = 0; direction < 4; direction++) {
            if(adjacentRooms[direction] == null) { continue; }

            GameObject door = GameObjectFactory.createDoorGameObject(direction, (Collider) roomGameObject.getComponent(BoxCollider.class));
            door.setPosition(door.getPosition().add(new Vec2(Direction.x(0, direction) * (SIZE * .5f + .9f), Direction.y(0, direction) * (SIZE * 0.5f + .9f))));
            gameObjects.add(door);
        }

        // IF U NEED TO ADD MORE GAME OBJECTS, ADD THEM HERE!!!

        initialized = true;
    }
    public void updateGameObjects() {
        for (GameObject gameObject : gameObjects) {
            gameObject.update();
        }
    }


    public String toString(){
        if(adjacentRooms[0] != null && adjacentRooms[2] != null && adjacentRooms[3] != null && adjacentRooms[1] != null){
            return "┼";
        } else if(adjacentRooms[0] != null && adjacentRooms[2] != null && adjacentRooms[3] != null){
            return "┤";
        } else if(adjacentRooms[0] != null && adjacentRooms[2] != null && adjacentRooms[1] != null){
            return "├";
        } else if(adjacentRooms[0] != null && adjacentRooms[3] != null && adjacentRooms[1] != null){
            return "┬";
        } else if(adjacentRooms[2] != null && adjacentRooms[3] != null && adjacentRooms[1] != null){
            return "┴";
        } else if(adjacentRooms[0] != null && adjacentRooms[1] != null){
            return "┌";
        } else if(adjacentRooms[0] != null && adjacentRooms[3] != null){
            return "┐";
        } else if(adjacentRooms[2] != null && adjacentRooms[1] != null){
            return "└";
        } else if(adjacentRooms[2] != null && adjacentRooms[3] != null){
            return "┘";
        } else if(adjacentRooms[0] != null && adjacentRooms[2] != null){
            return "│";
        } else if(adjacentRooms[3] != null && adjacentRooms[1] != null){
            return "─";
        } else if(adjacentRooms[0] != null){
            return "│";
        } else if(adjacentRooms[2] != null){
            return "│";
        } else if(adjacentRooms[3] != null){
            return "─";
        } else if(adjacentRooms[1] != null) {
            return "─";
        } else {
            return " ";
        }
    }
}
