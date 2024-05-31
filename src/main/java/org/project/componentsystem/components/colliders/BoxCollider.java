package org.project.componentsystem.components.colliders;

import org.project.componentsystem.GameObject;
import org.project.core.Debug;
import org.project.core.Game;
import org.project.core.rendering.Renderer;
import org.project.utils.Vec2;

import java.awt.*;

public class BoxCollider extends AbstractBoxCollider {

    /**
     * Initializes a new Component with the given GameObject, enabled status, size and movable status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param enabled    Whether this Component is enabled or not
     * @param size       The size of the box collider (width, height)
     * @param movable    Whether the collider is movable by others or not
     * @param inside     Whether the collider is inside the box or not
     */
    public BoxCollider(GameObject gameObject, boolean enabled, Vec2 size, boolean movable, boolean inside) {
        super(gameObject, enabled, size, movable, inside);
    }

    /**
     * Initializes a new Component with the given GameObjec, size, movable status and inside status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param size       The size of the box collider (width, height)
     * @param movable    Whether the collider is movable by others or not
     * @param inside     Whether the collider is inside the box or not
     */
    public BoxCollider(GameObject gameObject, Vec2 size, boolean movable, boolean inside) {
        super(gameObject, size, movable, inside);
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
    public static void repulsion(Collider collider1, Collider collider2, float xMove, float yMove, float repel) {
        if (collider1.isMovable()) {
            collider1.getGameObject().setPosition(
                    collider1.getGameObject().getPosition().add(
                            new Vec2(xMove / (1 / repel), yMove / (1 / repel))
                    )
            );
        }
        if (collider2.isMovable()) {
            collider2.getGameObject().setPosition(
                    collider2.getGameObject().getPosition().add(
                            new Vec2(-xMove / (1 / repel), -yMove / (1 / repel))
                    )
            );
        }
    }

    @Override
    public void onCollide(Collider other) {
        if (other instanceof BoxCollider) {
            BoxCollider otherBox = (BoxCollider) other;
            if (this.isInside() && otherBox.isInside()) {
                float repel = 0.02f;
                float xDistance = this.getGameObject().getPosition().getX() - otherBox.getGameObject().getPosition().getX();
                float yDistance = this.getGameObject().getPosition().getY() - otherBox.getGameObject().getPosition().getY();
                float xMove = (float) (Math.cos(Math.atan2(yDistance, xDistance)) * (this.getSize().getX() + otherBox.getSize().getX() - Math.abs(xDistance)));
                float yMove = (float) Math.sin(Math.atan2(yDistance, xDistance)) * (this.getSize().getY() + otherBox.getSize().getY() - Math.abs(yDistance));
                BoxCollider.repulsion(this, otherBox, xMove, yMove, repel);
                return;
            }
            if (otherBox.isMovable()) {
                float x = otherBox.getGameObject().getPosition().getX();
                float y = otherBox.getGameObject().getPosition().getY();
                float x1 = this.getGameObject().getPosition().getX() - this.getSize().getX() / 2;
                float y1 = this.getGameObject().getPosition().getY() - this.getSize().getY() / 2;

                x = Math.max(x1 + otherBox.getSize().getX() / 2, Math.min(x1 + this.getSize().getX() - otherBox.getSize().getX() / 2, x));
                y = Math.max(y1 + otherBox.getSize().getY() / 2, Math.min(y1 + this.getSize().getY() - otherBox.getSize().getY() / 2, y));
                otherBox.getGameObject().setPosition(new Vec2(x, y));
            }
            if (this.isMovable()) {
                float x = this.getGameObject().getPosition().getX();
                float y = this.getGameObject().getPosition().getY();
                float x1 = otherBox.getGameObject().getPosition().getX() - otherBox.getSize().getX() / 2;
                float y1 = otherBox.getGameObject().getPosition().getY() - otherBox.getSize().getY() / 2;

                x = Math.max(x1 + this.getSize().getX() / 2, Math.min(x1 + otherBox.getSize().getX() - this.getSize().getX() / 2, x));
                y = Math.max(y1 + this.getSize().getY() / 2, Math.min(y1 + otherBox.getSize().getY() - this.getSize().getY() / 2, y));
                this.getGameObject().setPosition(new Vec2(x, y));
            }
        }
        if (other instanceof CircleCollider) {
            CircleCollider otherCircle = (CircleCollider) other;
            if (this.isInside()) {
                CircleCollider.circleBoxCollision(this, otherCircle);
                return;
            }
            float x = otherCircle.getGameObject().getPosition().getX();
            float y = otherCircle.getGameObject().getPosition().getY();
            float r = otherCircle.getRadius();
            float x1 = this.getGameObject().getPosition().getX() - this.getSize().getX() / 2;
            float y1 = this.getGameObject().getPosition().getY() - this.getSize().getY() / 2;
            float w = this.getSize().getX();
            float h = this.getSize().getY();

            x = Math.max(x1 + r, Math.min(x1 + w - r, x));
            y = Math.max(y1 + r, Math.min(y1 + h - r, y));
            otherCircle.getGameObject().setPosition(new Vec2(x, y));
        }
    }

    /**
     * Runs when the Component is first created
     */
    @Override
    public void start() {

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
    }

    @Override
    public void onEnable() {
        Game.getCurrentLevel().getPhysicsEngine().addCollider(this);
    }

    @Override
    public void onDisable() {
        Game.getCurrentLevel().getPhysicsEngine().removeCollider(this);
    }

    public void draw() {
        if (Debug.ENABLED && isEnabled())
            Renderer.addRectToRenderQueue(
                    getGameObject().getPosition(),
                    getSize(),
                    Color.RED,
                    2
            );
    }
}
