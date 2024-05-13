package org.project.componentsystem.components.colliders;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.Physics;
import org.project.core.Debug;
import org.project.core.rendering.Renderer;
import org.project.utils.Vec2;

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
            if(otherBox.isInside()) circleBoxCollision(otherBox, this);
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
        if(Debug.ENABLED) { draw(); }
    }

    /**
     * Destroy the component
     */
    @Override
    public void destory() {

    }

    @Override
    public void onEnable() {
        Physics.addCollider(this);
    }

    @Override
    public void onDisable() {
        Physics.removeCollider(this);
    }

    public void draw() {
        Renderer.addCircleToRenderQueue(getGameObject().getPosition(), getRadius(), Color.red, 2);
    }


    public static void circleBoxCollision(AbstractBoxCollider box, AbstractCircleCollider circle){
        Vec2 distance = collisionDistance(circle, box);
        float overlap = circle.getRadius() - distance.magnitude();
        if(overlap > 0){
            boolean thisMovable = circle.isMovable();
            boolean otherMovable = box.isMovable();
            if (thisMovable && otherMovable) {
                circle.getGameObject().setPosition(
                       circle.getGameObject().getPosition().add(
                                distance.normalized().multiply(-overlap * 0.5f)
                        )
                );

                box.getGameObject().setPosition(
                        box.getGameObject().getPosition().add(
                                distance.normalized().multiply(overlap * 0.5f)
                        )
                );
            }
            else if (thisMovable) {
                circle.getGameObject().setPosition(
                        circle.getGameObject().getPosition().add(
                                distance.normalized().multiply(-overlap)
                        )
                );
            }
            else if (otherMovable) {
                box.getGameObject().setPosition(
                        box.getGameObject().getPosition().add(
                                distance.normalized().multiply(-overlap)
                        )
                );
            }
        }
    }
}
