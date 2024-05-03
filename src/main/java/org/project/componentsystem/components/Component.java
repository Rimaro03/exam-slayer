package org.project.componentsystem;

import lombok.Setter;
import lombok.Getter;
@Setter
@Getter
public abstract class Component {
    private GameObject gameObject;
    private boolean enabled;
    private int id;
    private String name;

    /**
     * Initializes a new Component with the given GameObject, id, name, and enabled status
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param id The unique identifier for this Component
     * @param name The name of this Component
     * @param enabled Whether this Component is enabled or not
     */
    public Component(GameObject gameObject, int id, String name, boolean enabled) {
        this.gameObject = gameObject;
        this.id = id;
        this.name = name;
        this.enabled = enabled;
    }

    /**
     * Initializes a new Component with the given GameObject, id, and name
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param id The unique identifier for this Component
     * @param name The name of this Component
     */
    public Component(GameObject gameObject, int id, String name) {
        this(gameObject, id, name, true);
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

}
