package org.project.generation;

import lombok.Getter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Level {
    @Getter
    private final Room[][] map;
    private Room currentRoom;
    public Level(Room[][] map, int startX, int startY){
        this.map = map;
        currentRoom = map[startY][startX];
    }

    public void move(int direction){
        Room next = currentRoom.getAdjacentRoom(direction);
        if(next != null){
            currentRoom = next;
        } else {
            throw new RuntimeException("No room in that direction");
        }
    }
    public void init(){


        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] == null ? " " : map[i][j].toString());
            }
            System.out.println();
        }
    }
    public void update(){

    }
}
