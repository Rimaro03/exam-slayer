package org.project.componentsystem.components.colliders;

import lombok.Getter;
import lombok.Setter;
import org.project.componentsystem.GameObject;
import org.project.utils.Vec2;

@Getter
@Setter
public abstract class AbstractBoxCollider extends Collider {
    private Vec2 size; // (width, height)
    private boolean inside;

    /**
     * Initializes a new Component with the given GameObject, enabled status, size, movable status and inside status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param enabled    Whether this Component is enabled or not
     * @param size       The size of the box (width, height)
     * @param movable    Whether the collider is movable by others or not
     * @param inside     Whether the collider is inside the box or not
     */
    public AbstractBoxCollider(GameObject gameObject, boolean enabled, Vec2 size, boolean movable, boolean inside) {
        super(gameObject, enabled, movable);
        this.size = size;
        this.inside = inside;
    }

    /**
     * Initializes a new Component with the given GameObjec, size, movable status and inside status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param size       The size of the box collider (width, height)
     * @param movable    Whether the collider is movable by others or not
     * @param inside     Whether the collider is inside the box or not
     */
    public AbstractBoxCollider(GameObject gameObject, Vec2 size, boolean movable, boolean inside) {
        super(gameObject, movable);
        this.size = size;
        this.inside = inside;
    }

    public abstract void onCollide(Collider other);


    /**
     * Checks if this collider collides with another collider
     *
     * @param other The other collider to check collision with
     * @return Whether the two colliders collide or not
     */
    public boolean collidesWith(Collider other) {
        if (this.getIgnoreColliders().contains(other)) return false;

        if (other instanceof AbstractBoxCollider) {
            AbstractBoxCollider otherBox = (AbstractBoxCollider) other;

            float thisX = this.getGameObject().getPosition().getX();
            float thisY = this.getGameObject().getPosition().getY();
            float thisHalfWidth = this.getSize().getX() / 2;
            float thisHalfHeight = this.getSize().getY() / 2;

            float otherX = otherBox.getGameObject().getPosition().getX();
            float otherY = otherBox.getGameObject().getPosition().getY();
            float otherHalfWidth = otherBox.getSize().getX() / 2;
            float otherHalfHeight = otherBox.getSize().getY() / 2;

            if (isInside() && otherBox.isInside()) {
                if (Math.abs(thisX - otherX) > (thisHalfWidth + otherHalfWidth)) {
                    return false;
                }
                return !(Math.abs(thisY - otherY) > (thisHalfHeight + otherHalfHeight));
            }

            if (!isInside() && otherBox.isInside()) {
                return otherX + otherHalfWidth > thisX + thisHalfWidth ||
                        otherX - otherHalfWidth < thisX - thisHalfWidth ||
                        otherY + otherHalfHeight > thisY + thisHalfHeight ||
                        otherY - otherHalfHeight < thisY - thisHalfHeight;
            }

            if (isInside()) {
                return thisX + thisHalfWidth > otherX + otherHalfWidth ||
                        thisX - thisHalfWidth < otherX - otherHalfWidth ||
                        thisY + thisHalfHeight > otherY + otherHalfHeight ||
                        thisY - thisHalfHeight < otherY - otherHalfHeight;
            }
            return true;
        }
        return false;
    }

}
