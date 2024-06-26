package org.project.componentsystem.components.colliders;

import lombok.Getter;
import lombok.Setter;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.Component;

import java.util.ArrayList;

@Getter
@Setter
public abstract class Collider extends Component {
    private boolean movable;
    private ArrayList<Collider> ignoreColliders = new ArrayList<>();

    /**
     * Initializes a new Component with the given GameObject, enabled status and movable status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param enabled    Whether this Component is enabled or not
     * @param movable    Whether the collider is movable by others or not
     */
    public Collider(GameObject gameObject, boolean enabled, boolean movable) {
        super(gameObject, enabled);
        this.movable = movable;
    }

    /**
     * Initializes a new Component with the given GameObject and movable status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param movable    Whether the collider is movable by others or not
     */
    public Collider(GameObject gameObject, boolean movable) {
        super(gameObject);
        this.movable = movable;
    }

    public abstract boolean collidesWith(Collider other);

    public abstract void onCollide(Collider other);
}
