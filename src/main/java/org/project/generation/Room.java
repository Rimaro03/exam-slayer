package org.project.generation;

import lombok.Getter;
import org.project.componentsystem.GameObject;
import org.project.core.Game;

import java.util.ArrayList;

public class Room {
    private final ArrayList<GameObject> gameObjects;
    private final Room[] adjacentRooms;
    @Getter
    private final int x, y;
    public Room(int x, int y) {
        gameObjects = new ArrayList<>();
        adjacentRooms = new Room[4];
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
