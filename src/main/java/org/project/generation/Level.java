package org.project.generation;

import org.project.componentsystem.GameObjectFactory;

public class Level {
    private final Room[][] rooms;
    public Level(Room[][] rooms){
        this.rooms = rooms;
    }
    public void init(){

    }
    public void update(){

    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Room[] row : rooms){
            for(Room room : row){
                sb.append(room.toString());
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
