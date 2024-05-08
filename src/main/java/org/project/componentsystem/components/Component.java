package org.project.componentsystem.components;

import lombok.Getter;
import lombok.Setter;
import org.project.componentsystem.GameObject;

@Setter @Getter
public abstract class Component {
    private GameObject gameObject;
    private boolean enabled;

    /**
     * Initializes a new Component with the given GameObject, id, name, and enabled status
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param enabled Whether this Component is enabled or not
     */
    public Component(GameObject gameObject, boolean enabled) {
        this.gameObject = gameObject;
        this.enabled = enabled;
    }

    /**
     * Initializes a new Component with the given GameObject, id, and name
     * @param gameObject The reference to the GameObject that this Component is attached to
     */
    public Component(GameObject gameObject) {
        this(gameObject, true);
    }

    /**
     * Runs when the Component is first created
     * This method is meant to be overridden by subclasses
     */
    public void start() {}

    /**
     * Runs every frame
     * This method is meant to be overridden by subclasses
     */
    public void update() {}

    @Override
    public String toString() {
        return "Component{" +
                "enabled=" + enabled +
                '}';
    }
}
