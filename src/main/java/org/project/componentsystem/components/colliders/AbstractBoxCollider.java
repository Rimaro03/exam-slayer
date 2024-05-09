package org.project.componentsystem.components.colliders;

import lombok.Getter;
import lombok.Setter;
import org.project.componentsystem.GameObject;
import org.project.utils.Vec2;

@Getter @Setter
public abstract class AbstractBoxCollider extends Collider{
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
            int thisX = this.getGameObject().getPosition().getX();
            int thisY = this.getGameObject().getPosition().getY();
            int otherX = otherBox.getGameObject().getPosition().getX();
            int otherY = otherBox.getGameObject().getPosition().getY();

            return thisX < otherX + otherBox.getSize().getX() &&
                    thisX + this.getSize().getX() > otherX &&
                    thisY < otherY + otherBox.getSize().getY() &&
                    thisY + this.getSize().getY() > otherY;
        }
        return false;
    }
}
