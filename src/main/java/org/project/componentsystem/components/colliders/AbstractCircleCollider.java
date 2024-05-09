package org.project.componentsystem.components;

import org.project.componentsystem.GameObject;

public abstract class AbstractCircleCollider extends Component{
    /**
     * Initializes a new Component with the given GameObject and enabled status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param enabled    Whether this Component is enabled or not
     */
    public AbstractCircleCollider(GameObject gameObject, boolean enabled) {
        super(gameObject, enabled);
    }

    /**
     * Initializes a new Component with the given GameObject
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     */
    public AbstractCircleCollider(GameObject gameObject) {
        super(gameObject);
    }

    public boolean collidesWith(AbstractCircleCollider other) {
        return false;
    }

    public abstract void onCollide(AbstractCircleCollider other);

    public void update() {
        for (Component component : getGameObject().getComponents()) {
            if (component instanceof AbstractCircleCollider) {
                AbstractCircleCollider collider = (AbstractCircleCollider) component;
                if (collidesWith(collider)) {
                    onCollide(collider);
                }
            }
        }
    }
}
