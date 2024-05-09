package org.project.componentsystem.components.colliders;

import lombok.Getter;
import lombok.Setter;
import org.project.componentsystem.GameObject;
import org.project.utils.Vec2;

@Getter @Setter
public abstract class AbstractBoxCollider extends Collider {
    private boolean movable;
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
        super(gameObject, enabled);
        this.size = size;
        this.movable = movable;
    }

    /**
     * Initializes a new Component with the given GameObjec, size and movable status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param size       The size of the box collider (width, height)
     * @param movable    Whether the collider is movable by others or not
     */
    public AbstractBoxCollider(GameObject gameObject, Vec2 size, boolean movable) {
        super(gameObject);
        this.size = size;
        this.movable = movable;
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
            float otherX = otherBox.getGameObject().getPosition().getX();
            float otherY = otherBox.getGameObject().getPosition().getY();

            return thisX < otherX + otherBox.getSize().getX() &&
                    thisX + this.getSize().getX() > otherX &&
                    thisY < otherY + otherBox.getSize().getY() &&
                    thisY + this.getSize().getY() > otherY;
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

    /**
     * Gets the overlap between this box collider and another box collider
     *
     * @param other The other box collider to check overlap with
     * @return The overlap between the two colliders
     */
    public Vec2 getOverlap(BoxCollider other) {
        Vec2 thisCenter = this.getGameObject().getPosition().add(this.getSize().divide(2));
        Vec2 otherCenter = other.getGameObject().getPosition().add(other.getSize().divide(2));

        float overlapX = Math.abs(thisCenter.getX() - otherCenter.getX()) - (this.getSize().getX() / 2 + other.getSize().getX() / 2);
        float overlapY = Math.abs(thisCenter.getY() - otherCenter.getY()) - (this.getSize().getY() / 2 + other.getSize().getY() / 2);

        if (overlapX < 0 && overlapY < 0) return new Vec2(overlapX, overlapY);
        else return null;
    }
}
