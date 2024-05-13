package org.project.componentsystem.components.colliders;

import org.project.componentsystem.GameObject;
import org.project.core.Physics;
import org.project.core.Debug;
import org.project.core.Game;
import org.project.core.rendering.Renderer;
import org.project.generation.wavecollapse.Direction;
import org.project.generation.Room;
import org.project.utils.Vec2;

import java.awt.*;

public class DoorCollider extends AbstractBoxCollider{
    private final int direction;
    /**
     * Initializes a new Component with the given GameObject, enabled status, size, movable status, inside status and direction
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param enabled    Whether this Component is enabled or not
     * @param size       The size of the box (width, height)
     * @param movable    Whether the collider is movable by others or not
     * @param inside     Whether the collider is inside the box or not
     * @param direction  The direction of the door
     */
    public DoorCollider(GameObject gameObject, boolean enabled, Vec2 size, boolean movable, boolean inside, int direction) {
        super(gameObject, enabled, size, movable, inside);
        this.direction = direction;
    }

    /**
     * Initializes a new Component with the given GameObjec, size, movable status, inside status and direction
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param size       The size of the box collider (width, height)
     * @param movable    Whether the collider is movable by others or not
     * @param inside     Whether the collider is inside the box or not
     * @param direction  The direction of the door
     */
    public DoorCollider(GameObject gameObject, Vec2 size, boolean movable, boolean inside, int direction) {
        super(gameObject, size, movable, inside);
        this.direction = direction;
    }

    /**
     * Runs when the Component is first created
     * This method is meant to be overridden by subclasses
     */
    @Override
    public void start() { }

    /**
     * Runs every frame
     * This method is meant to be overridden by subclasses
     */
    @Override
    public void update() {
        draw();
    }

    /**
     * Destory the component
     */
    @Override
    public void destory() {  }

    @Override
    public void onEnable() {
        Physics.addCollider(this);
    }

    @Override
    public void onDisable() {
        Physics.removeCollider(this);
    }

    /**
     * Runs when the collider collides with another collider
     * @param other The other collider that this collider collided with
     */
    @Override
    public void onCollide(Collider other) {
        // Change room if the player collides with the door collider
        Game.getCurrentLevel().getCurrentRoom().getGameObjects().forEach(go -> {
            if(go.getName().equals("Player")){
                go.setPosition(new Vec2(
                        Direction.x(0, direction) * (2.5f - Room.SIZE / 2),
                        Direction.y(0, direction) * (2.5f - Room.SIZE / 2))
                );
            }
        });
        Game.getCurrentLevel().changeRoom(direction);

    }

    public void draw(){
        if(Debug.ENABLED)
            Renderer.addRectToRenderQueue(
                    getGameObject().getPosition(),
                    getSize(),
                    Color.RED,
                    2
            );
    }
}
