package org.project.componentsystem.components.colliders;

import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.Component;

public abstract class Collider extends Component {
    /**
     * Initializes a new Component with the given GameObject and enabled status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param enabled    Whether this Component is enabled or not
     */
    public Collider(GameObject gameObject, boolean enabled) {
        super(gameObject, enabled);
    }

    /**
     * Initializes a new Component with the given GameObject
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     */
    public Collider(GameObject gameObject) {
        super(gameObject);
    }

    public abstract boolean collidesWith(Collider other);
    public abstract void onCollide(Collider other);
}
