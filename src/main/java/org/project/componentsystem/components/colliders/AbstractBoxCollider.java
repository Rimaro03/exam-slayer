package org.project.componentsystem.components.colliders;

import lombok.Getter;
import lombok.Setter;
import org.project.componentsystem.GameObject;
import org.project.utils.Vec2;

@Getter @Setter
public abstract class AbstractBoxCollider extends Collider {
    private Vec2 size; // (width, height)

    /**
     * Initializes a new Component with the given GameObject and enabled status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param enabled    Whether this Component is enabled or not
     * @param size       The size of the box (width, height)
     * @param movable    Whether the collider is movable by others or not
     */
    public AbstractBoxCollider(GameObject gameObject, boolean enabled, Vec2 size, boolean movable) {
        super(gameObject, enabled, movable);
        this.size = size;
    }

    /**
     * Initializes a new Component with the given GameObjec, size and movable status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param size       The size of the box collider (width, height)
     * @param movable    Whether the collider is movable by others or not
     */
    public AbstractBoxCollider(GameObject gameObject, Vec2 size, boolean movable) {
        super(gameObject, movable);
        this.size = size;
    }

    public abstract void onCollide(Collider other);


    /**
     * Checks if this collider collides with another collider
     *
     * @param other The other collider to check collision with
     * @return Whether the two colliders collide or not
     */
    public boolean collidesWith(Collider other) {
        if(other instanceof AbstractBoxCollider){
            AbstractBoxCollider otherBox = (AbstractBoxCollider) other;

            float thisX = this.getGameObject().getPosition().getX();
            float thisY = this.getGameObject().getPosition().getY();
            float thisHalfWidth = this.getSize().getX() / 2;
            float thisHalfHeight = this.getSize().getY() / 2;

            float otherX = otherBox.getGameObject().getPosition().getX();
            float otherY = otherBox.getGameObject().getPosition().getY();
            float otherHalfWidth = otherBox.getSize().getX() / 2;
            float otherHalfHeight = otherBox.getSize().getY() / 2;

            if (Math.abs(thisX - otherX) > (thisHalfWidth + otherHalfWidth)) {
                return false;
            }

            return !(Math.abs(thisY - otherY) > (thisHalfHeight + otherHalfHeight));
        }
        else if (other instanceof AbstractCircleCollider) {
            AbstractCircleCollider otherCircle = (AbstractCircleCollider) other;
            Vec2 circlePos = otherCircle.getGameObject().getPosition();
            Vec2 boxPos = this.getGameObject().getPosition();

            Vec2 halfSize = this.getSize().divide(2);
            Vec2 distance = circlePos.subtract(boxPos);
            Vec2 clamp = Vec2.clamp(distance, new Vec2(-halfSize.getX(), -halfSize.getY()), halfSize);
            Vec2 closest = boxPos.add(clamp);
            distance = closest.subtract(circlePos);
            return distance.magnitude() < otherCircle.getRadius();
        }
        return false;
    }



}
