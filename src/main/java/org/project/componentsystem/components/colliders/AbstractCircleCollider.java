package org.project.componentsystem.components.colliders;

import lombok.Getter;
import lombok.Setter;
import org.project.componentsystem.GameObject;
import org.project.utils.Vec2;

@Getter @Setter
public abstract class AbstractCircleCollider extends Collider {
    private float radius;
    /**
     * Initializes a new Component with the given GameObject, enabled status, radius and movable status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param enabled    Whether this Component is enabled or not
     * @param radius     The radius of the circle collider
     * @param movable    Whether the object is movable or not
     */
    public AbstractCircleCollider(GameObject gameObject, boolean enabled, float radius, boolean movable) {
        super(gameObject, enabled, movable);
        this.radius = radius;
    }

    /**
     * Initializes a new Component with the given GameObject, radius and movable status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param radius     The radius of the circle collider
     * @param movable    Whether the object is movable or not
     */
    public AbstractCircleCollider(GameObject gameObject, float radius, boolean movable) {
        super(gameObject, movable);
        this.radius = radius;
    }

    public boolean collidesWith(Collider other) {
        if(this.getIgnoreColliders().contains(other)) return false;
        if (other instanceof AbstractCircleCollider) {
            AbstractCircleCollider otherCircle = (AbstractCircleCollider) other;
            float distance = this.getGameObject().getPosition().distance(otherCircle.getGameObject().getPosition());

            return distance < getRadius() + otherCircle.getRadius();
        }
        if (other instanceof AbstractBoxCollider) {
            AbstractBoxCollider otherBox = (AbstractBoxCollider) other;
            if (otherBox.isInside()) {
               Vec2 distance = collisionDistance(this, otherBox);
                return distance.magnitude() < this.getRadius();
            }
            float x = this.getGameObject().getPosition().getX();
            float y = this.getGameObject().getPosition().getY();
            float r = this.getRadius();
            float x1 = otherBox.getGameObject().getPosition().getX() - otherBox.getSize().getX() / 2;
            float x2 = otherBox.getGameObject().getPosition().getX() + otherBox.getSize().getX() / 2;
            float y1 = otherBox.getGameObject().getPosition().getY() - otherBox.getSize().getY() / 2;
            float y2 = otherBox.getGameObject().getPosition().getY() + otherBox.getSize().getY() / 2;

            return x - r < x1 || x + r > x2 || y - r < y1 || y + r > y2;
        }
        return false;
    }

    public abstract void onCollide(Collider other);


    protected static Vec2 collisionDistance(AbstractCircleCollider circle, AbstractBoxCollider box) {
        Vec2 circlePos = circle.getGameObject().getPosition();
        Vec2 boxPos = box.getGameObject().getPosition();
        Vec2 halfSize = box.getSize().divide(2);
        Vec2 distance = circlePos.subtract(boxPos);
        Vec2 clamp = Vec2.clamp(distance, new Vec2(-halfSize.getX(), -halfSize.getY()), halfSize);
        Vec2 closest = boxPos.add(clamp);
        return closest.subtract(circlePos);
    }
}
