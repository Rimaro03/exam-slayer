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
            float repel = 0.06f;
            float xDistance = this.getGameObject().getPosition().getX() - otherBox.getGameObject().getPosition().getX();
            float yDistance = this.getGameObject().getPosition().getY() - otherBox.getGameObject().getPosition().getY();
            float xMove = (float) (Math.cos(Math.atan2(yDistance, xDistance)) * (this.getSize().getX() + otherBox.getSize().getX() - Math.abs(xDistance)));
            float yMove = (float) Math.sin(Math.atan2(yDistance, xDistance)) * (this.getSize().getY() + otherBox.getSize().getY() - Math.abs(yDistance));
            BoxCollider.repulsion(this, otherBox, xMove, yMove, repel);
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

    /**
     * Repels two colliders from each other (A box and an any type collider)
     *
     * @param collider1 The first collider
     * @param collider2 The second collider
     * @param xMove     The amount to move in the x direction
     * @param yMove     The amount to move in the y direction
     * @param repel     The amount to repel by
     */
    public static void repulsion(Collider collider1, Collider collider2, float xMove, float yMove, float repel){
        if(collider1.isMovable()){
                collider1.getGameObject().setPosition(
                        collider1.getGameObject().getPosition().add(
                                new Vec2(xMove / (1 / repel), yMove / (1 / repel))
                        )
                );
            }
        if(collider2.isMovable()){
            collider2.getGameObject().setPosition(
                    collider2.getGameObject().getPosition().add(
                            new Vec2(-xMove / (1 / repel), -yMove / (1 / repel))
                    )
            );
        }
    }
}
