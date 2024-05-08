package org.project.core;

import org.project.Room;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.GameObjectFactory;
import org.project.core.rendering.Renderer;
import org.project.utils.Vec2;

import java.awt.*;
import java.util.ArrayList;

public class Scheme {

    private static Scheme instance;
    private final ArrayList<Room> rooms = new ArrayList<>();
    private final GameObject player;

    private Scheme() {
        instance = this;
        player = GameObjectFactory.createPlayer("PlayerName");
    }

    /**
     * Get the instance of the Scheme.
     * @return the instance of the Scheme.
     */
    public static Scheme getInstance() {
        if(instance == null){ instance = new Scheme(); }
        return instance;
    }
    public void start(){
        player.start();
        for(Room room : rooms) {
            room.update();
        }
    }
    public void update() {
        Vec2 playerPosition = player.getPosition();
        //Renderer.fillRect(new Rectangle(playerPosition.getX(), playerPosition.getY(), 50, 50), Color.BLUE);
        player.update();

        for(Room room : rooms) {
            room.update();
        }
    }
}
