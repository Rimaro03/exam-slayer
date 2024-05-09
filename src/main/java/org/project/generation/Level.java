package org.project.generation;

import lombok.Getter;
import org.project.componentsystem.GameObjectFactory;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Level {
    //private Room[][] rooms;
    @Getter
    private Room currentRoom;
    public Level(Room startRoom){
        currentRoom = startRoom;
    }
    /*public Level(Room[][] rooms, int startX, int startY){
        this.rooms = rooms;
        currentRoom = rooms[startY][startX];
        init();
    }*/

    private void changeRoom(int direction){
        Room next = currentRoom.getAdjacentRoom(direction);
        if(next != null){
            currentRoom = next;
        } else {
            throw new RuntimeException("No room in that direction");
        }
    }
    public void init(){
        currentRoom.addGameObject(GameObjectFactory.createPlayer("Player"));
    }
    public void update(){
        currentRoom.updateGameObjects();
    }
}
