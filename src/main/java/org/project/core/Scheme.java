package org.project.core;

import org.project.Room;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.GameObjectFactory;
import org.project.componentsystem.Physics;
import org.project.componentsystem.components.KeyboardController;
import org.project.componentsystem.components.colliders.BoxCollider;
import org.project.componentsystem.components.colliders.CircleCollider;
import org.project.core.rendering.Renderer;
import org.project.utils.Vec2;

import java.awt.*;
import java.util.ArrayList;

public class Scheme {

    private static Scheme instance;
    private final ArrayList<Room> rooms = new ArrayList<>();
    private final GameObject player;
    private final GameObject player2;

    private Scheme() {
        player = GameObjectFactory.createPlayer("PlayerName");
        player2 = GameObjectFactory.createPlayer("PlayerName2");
        player2.setPosition(new Vec2(5, 5));
        player2.removeComponent(player2.getComponent(KeyboardController.class));
//        player2.removeComponent(player2.getComponent(BoxCollider.class));
//        player2.addComponent(new CircleCollider(player2, 2, true));
        ((BoxCollider) player2.getComponent(BoxCollider.class)).setMovable(true);
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
        for(Room room : rooms) {
            room.update();
        }
    }

    public void update() {
        player.update();
        player2.update();

        Physics.update();
        for(Room room : rooms) {
            room.update();
        }
    }
}
