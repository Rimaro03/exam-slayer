package org.project.componentsystem.components;

import lombok.Getter;
import lombok.Setter;
import org.project.componentsystem.GameObject;
import org.project.utils.Vec2;

@Setter @Getter
public abstract class Component {
    private GameObject gameObject;
    private boolean enabled;

    /**
     * Initializes a new Component with the given GameObject and enabled status
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param enabled Whether this Component is enabled or not
     */
    public Component(GameObject gameObject, boolean enabled) {
        this.gameObject = gameObject;
        this.enabled = enabled;
    }
    public Vec2 getPosition() {
        return gameObject.getPosition();
    }

    /**
     * Initializes a new Component with the given GameObject
     * @param gameObject The reference to the GameObject that this Component is attached to
     */
    public Component(GameObject gameObject) {
        this(gameObject, true);
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
     *  Destory the component
     */
    public abstract void destory();

    @Override
    public String toString() {
        return "Component{" +
                "enabled=" + enabled +
                ", name=" + this.getClass().getSimpleName() +
                '}';
    }
}
