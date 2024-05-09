package org.project.componentsystem.components.colliders;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.Physics;
import org.project.core.rendering.Renderer;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.awt.*;

@Log4j2 @Getter @Setter
public class CircleCollider extends AbstractCircleCollider{
    /**
     * Initializes a new Component with the given GameObject, enabled status, radius and movable status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param enabled    Whether this Component is enabled or not
     * @param radius     The radius of the circle collider
     */
    public CircleCollider(GameObject gameObject, boolean enabled, float radius, boolean movable) {
        super(gameObject, enabled, radius, movable);
    }

    /**
     * Initializes a new Component with the given GameObject, radius and movable status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param radius     The radius of the circle collider
     * @param movable    Whether the object is movable or not
     */
    public CircleCollider(GameObject gameObject, float radius, boolean movable) {
        super(gameObject, radius, movable);
    }

    @Override
    public void onCollide(Collider other) {
        if(other instanceof CircleCollider){
           /* CircleCollider otherCircle = (CircleCollider) other;
            float repelForce = 0.09f;
            float distance = (float) Math.sqrt(
                    Math.pow(this.getGameObject().getPosition().getX() - otherCircle.getGameObject().getPosition().getX(), 2) +
                    Math.pow(this.getGameObject().getPosition().getY() - otherCircle.getGameObject().getPosition().getY(), 2)
            );
            float xDistance = this.getGameObject().getPosition().getX() - otherCircle.getGameObject().getPosition().getX();
            float yDistance = this.getGameObject().getPosition().getY() - otherCircle.getGameObject().getPosition().getY();
            float xMove = (float) (Math.cos(Math.atan2(yDistance, xDistance)) * (this.getRadius() + otherCircle.getRadius() - distance));
            float yMove = (float) Math.sin(Math.atan2(yDistance, xDistance)) * (this.getRadius() + otherCircle.getRadius() - distance);
            if(this.isMovable()){
                this.getGameObject().getPosition().setX((int) (this.getGameObject().getPosition().getX() + xMove / (1 / repelForce)));
                this.getGameObject().getPosition().setY((int) (this.getGameObject().getPosition().getY() + yMove / (1 / repelForce)));
            }
            if(otherCircle.isMovable()){
                otherCircle.getGameObject().getPosition().setX((int) (otherCircle.getGameObject().getPosition().getX() - xMove / (1 / repelForce)));
                otherCircle.getGameObject().getPosition().setY((int) (otherCircle.getGameObject().getPosition().getY() - yMove / (1 / repelForce)));
            } */
        }
    }

    /**
     * Runs when the Component is first created
     * This method is meant to be overridden by subclasses
     */
    @Override
    public void start() {
        Physics.addCollider(this);
    }

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
    public void destory() {
        Physics.removeCollider(this);
    }

    public void draw() {
        Renderer.drawPixel(getGameObject().getPosition(), Color.pink);
        Renderer.drawCircle(getGameObject().getPosition(), getRadius(), Color.red);
    }
}
