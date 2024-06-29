package org.project.componentsystem.components;

import lombok.Getter;
import lombok.Setter;
import org.project.componentsystem.GameObject;
import org.project.utils.Vec2;

@Setter
@Getter
/**
 * A component that keeps track of the health and speed of a game object.
 * Its used as a base class for other stats components.
 */
public abstract class Component {
    private GameObject gameObject;
    private boolean enabled;

    /**
     * Initializes a new Component with the given GameObject and enabled status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param enabled    Whether this Component is enabled or not
     */
    public Component(GameObject gameObject, boolean enabled) {
        this.gameObject = gameObject;
        this.enabled = enabled;
    }

    /**
     * Initializes a new Component with the given GameObject
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     */
    public Component(GameObject gameObject) {
        this(gameObject, true);
        onEnable();
    }

    public Vec2 getPosition() {
        return gameObject.getPosition();
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled)// don't do anything if the state is the same
            return;

        this.enabled = enabled;

        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    /**
     * Runs when the Component is first created
     * This method is meant to be overridden by subclasses
     */
    public abstract void start();

    /**
     * Runs every frame
     * This method is meant to be overridden by subclasses
     */
    public abstract void update();

    /**
     * Destory the component
     */
    public abstract void destory();

    /**
     * Runs when the Component is enabled
     * This method is meant to be overridden by subclasses
     */
    public abstract void onEnable();

    /**
     * Runs when the Component is disabled
     * This method is meant to be overridden by subclasses
     */
    public abstract void onDisable();

    @Override
    public String toString() {
        return "Component{" +
                "enabled=" + enabled +
                ", name=" + this.getClass().getSimpleName() +
                '}';
    }
}
