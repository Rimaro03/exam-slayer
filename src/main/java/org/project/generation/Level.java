package org.project.generation;

import lombok.Getter;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.GameObjectFactory;
import org.project.core.Input;
import org.project.core.rendering.Renderer;
import org.project.utils.Vec2;

import java.awt.*;
import java.util.*;

@Getter
public class Level {
    private Room currentRoom;

    /** This constructor get as input the start room of the map,
     * all other room are supposed to be linked to each other forming a graph of rooms.
     */
    public Level(Room startRoom){
        currentRoom = startRoom;
    }

    private void changeRoom(int direction){
        Room next = currentRoom.getAdjacentRoom(direction);

        if(next != null){
            GameObject player = currentRoom.getGameObject("Player");
            currentRoom.removeGameObject(player);
            next.addGameObject(player);
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

        debugMap();
    }

    private void debugMap(){
        int x = -currentRoom.getX();
        int y = -currentRoom.getY();

        Queue<Room> queue = new ArrayDeque<>();
        Set<Room> visited = new HashSet<>();
        queue.add(currentRoom);

        while(!queue.isEmpty()){
            Room room = queue.poll();

            Renderer.drawRect(new Vec2(x + room.getX(), y + room.getY()).multiply(4), new Vec2(2, 2), Color.BLUE);
            visited.add(room);

            for (int i = 0; i < 4; i++) {
                if(room.getAdjacentRoom(i) == null || visited.contains(room.getAdjacentRoom(i))) { continue; }
                queue.add(room.getAdjacentRoom(i));
            }
        }

        Renderer.drawCircle(new Vec2(0, 1).multiply(4), 1, currentRoom.getAdjacentRoom(0) != null ? Color.GREEN : Color.RED);
        Renderer.drawCircle(new Vec2(1, 0).multiply(4), 1, currentRoom.getAdjacentRoom(1) != null ? Color.GREEN : Color.RED);
        Renderer.drawCircle(new Vec2(0, -1).multiply(4), 1, currentRoom.getAdjacentRoom(2) != null ? Color.GREEN : Color.RED);
        Renderer.drawCircle(new Vec2(-1, 0).multiply(4), 1, currentRoom.getAdjacentRoom(3) != null ? Color.GREEN : Color.RED);

        if(Input.isKeyPressed(Input.KEY_UP) && currentRoom.getAdjacentRoom(0) != null) { changeRoom(0); }
        if(Input.isKeyPressed(Input.KEY_RIGHT) && currentRoom.getAdjacentRoom(1) != null) { changeRoom(1); }
        if(Input.isKeyPressed(Input.KEY_DOWN) && currentRoom.getAdjacentRoom(2) != null) { changeRoom(2); }
        if(Input.isKeyPressed(Input.KEY_LEFT) && currentRoom.getAdjacentRoom(3) != null) { changeRoom(3); }
    }
}
