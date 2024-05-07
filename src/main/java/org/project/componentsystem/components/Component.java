package org.project.componentsystem.components;

import lombok.Getter;
import lombok.Setter;
import org.project.componentsystem.GameObject;

@Setter @Getter
public abstract class Component {
    private GameObject gameObject;
    private boolean enabled;
    private String name;

    /**
     * Initializes a new Component with the given GameObject, id, name, and enabled status
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param name The name of this Component
     * @param enabled Whether this Component is enabled or not
     */
    public Component(GameObject gameObject, String name, boolean enabled) {
        this.gameObject = gameObject;
        this.name = name;
        this.enabled = enabled;
    }

    /**
     * Initializes a new Component with the given GameObject, id, and name
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param name The name of this Component
     */
    public Component(GameObject gameObject, String name) {
        this(gameObject, name, true);
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
                ", name='" + name + '\'' +
                '}';
    }
}
