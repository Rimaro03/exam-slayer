package org.project.componentsystem.components.colliders;

import lombok.Getter;
import lombok.Setter;
import org.project.componentsystem.GameObject;

@Getter @Setter
public abstract class AbstractCircleCollider extends Collider {
    private float radius;
    private boolean movable;
    /**
     * Initializes a new Component with the given GameObject, enabled status, radius and movable status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param enabled    Whether this Component is enabled or not
     * @param radius     The radius of the circle collider
     * @param movable    Whether the object is movable or not
     */
    public AbstractCircleCollider(GameObject gameObject, boolean enabled, float radius, boolean movable) {
        super(gameObject, enabled);
        this.radius = radius;
        this.movable = movable;
    }

    /**
     * Initializes a new Component with the given GameObject, radius and movable status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param radius     The radius of the circle collider
     * @param movable    Whether the object is movable or not
     */
    public AbstractCircleCollider(GameObject gameObject, float radius, boolean movable) {
        super(gameObject);
        this.radius = radius;
        this.movable = movable;
    }

    public boolean collidesWith(Collider other) {
        if (other instanceof AbstractCircleCollider) {
            AbstractCircleCollider otherCircle = (AbstractCircleCollider) other;
            float distance = this.getGameObject().getPosition().distance(otherCircle.getGameObject().getPosition());
            System.out.println(distance / 2 < getRadius() + otherCircle.getRadius());
            return distance < getRadius() + otherCircle.getRadius();
        }
        return false;
    }

    public abstract void onCollide(Collider other);
}
