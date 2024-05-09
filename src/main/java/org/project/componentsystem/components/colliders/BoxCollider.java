package org.project.componentsystem.components.colliders;

import org.project.componentsystem.GameObject;
import org.project.componentsystem.Physics;
import org.project.core.rendering.Renderer;
import org.project.utils.Vec2;

import java.awt.*;

public class BoxCollider extends AbstractBoxCollider{
    
        /**
     * Initializes a new Component with the given GameObject and enabled status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param enabled    Whether this Component is enabled or not
     * @param size       The size of the box (width, height)
     * @param movable    Whether the collider is movable by others or not
     */
    public BoxCollider(GameObject gameObject, boolean enabled, Vec2 size, boolean movable) {
        super(gameObject, enabled, size, movable);
    }

    /**
     * Initializes a new Component with the given GameObjec, size and movable status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param size       The size of the box collider (width, height)
     * @param movable    Whether the collider is movable by others or not
     */
    public BoxCollider(GameObject gameObject, Vec2 size, boolean movable) {
        super(gameObject, size, movable);
    }

    @Override
    public void onCollide(Collider other) {
        if(other instanceof BoxCollider){
            BoxCollider otherBox = (BoxCollider) other;
            Vec2 overlap = getOverlap(otherBox);
            if(overlap != null){
                float repel = 0.3f;
                boolean thisMovable = this.isMovable();
                boolean otherMovable = otherBox.isMovable();
                if (thisMovable && otherMovable) {
                    getGameObject().setPosition(
                            getGameObject().getPosition().add(
                                    overlap.multiply(0.1f)
                            )
                    );

                    other.getGameObject().setPosition(
                            other.getGameObject().getPosition().subtract(
                                    overlap.multiply(repel)
                            )
                    );
                } else if (thisMovable) {
                    getGameObject().setPosition(
                            getGameObject().getPosition().add(
                                    overlap.multiply(repel)
                            )
                    );
                } else if (otherMovable) {
                    other.getGameObject().setPosition(
                            other.getGameObject().getPosition().subtract(
                                    overlap.multiply(repel)
                            )
                    );
                }
            }
        }
    }

    /**
     * Runs when the Component is first created
     */
    @Override
    public void start() {
        Physics.addCollider(this);
    }

    /**
     * Runs every frame
     */
    @Override
    public void update() {
        draw();
    }

    /**
     * Destroy the component
     */
    @Override
    public void destory() {
        Physics.removeCollider(this);
    }

    public void draw(){
        Renderer.drawRect(
                getGameObject().getPosition(),
                getSize(),
                Color.RED
        );
    }
}
