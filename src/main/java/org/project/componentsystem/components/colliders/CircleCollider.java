package org.project.componentsystem.components.colliders;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.Physics;
import org.project.core.rendering.Renderer;
import org.project.utils.Vec2;

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
            CircleCollider otherCircle = (CircleCollider) other;
            float distance = this.getGameObject().getPosition().distance(otherCircle.getGameObject().getPosition());
            float overlap = getRadius() + otherCircle.getRadius() - distance;
            if(overlap > 0){
                boolean thisMovable = this.isMovable();
                boolean otherMovable = otherCircle.isMovable();
                if (thisMovable && otherMovable) {
                    getGameObject().setPosition(
                            getGameObject().getPosition().add(
                                    this.getGameObject().getPosition().subtract(other.getGameObject().getPosition()).normalized().multiply(overlap * 0.5f)
                            )
                    );

                    other.getGameObject().setPosition(
                            other.getGameObject().getPosition().add(
                                    other.getGameObject().getPosition().subtract(getGameObject().getPosition()).normalized().multiply(overlap * 0.5f)
                            )
                    );
                } else if (thisMovable) {
                    getGameObject().setPosition(
                            getGameObject().getPosition().add(
                                    this.getGameObject().getPosition().subtract(other.getGameObject().getPosition()).normalized().multiply(overlap)
                            )
                    );
                } else if (otherMovable) {
                    other.getGameObject().setPosition(
                            other.getGameObject().getPosition().add(
                                    other.getGameObject().getPosition().subtract(getGameObject().getPosition()).normalized().multiply(overlap)
                            )
                    );
                }
            }
        }
        if(other instanceof BoxCollider){
            BoxCollider otherBox = (BoxCollider) other;
            float repel = 0.06f;
            float xDistance = this.getGameObject().getPosition().getX() - otherBox.getGameObject().getPosition().getX();
            float yDistance = this.getGameObject().getPosition().getY() - otherBox.getGameObject().getPosition().getY();
            float xMove = (float) (Math.cos(Math.atan2(yDistance, xDistance)) * (this.getRadius() + otherBox.getSize().getX() - Math.abs(xDistance)));
            float yMove = (float) Math.sin(Math.atan2(yDistance, xDistance)) * (this.getRadius() + otherBox.getSize().getY() - Math.abs(yDistance));

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

    public void draw() {
        Renderer.drawCircle(getGameObject().getPosition(), getRadius(), Color.red);
    }
}
