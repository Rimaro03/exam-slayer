package org.project.componentsystem;

import lombok.Getter;
import lombok.Setter;
import org.project.componentsystem.components.Component;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Getter @Setter
public class GameObject {
    private int id;
    private String name;
    private ArrayList<Component> components;
    /**
     * Initializes a new GameObject with the given id and name
     * @param id The unique identifier for this GameObject
     * @param name The name of this GameObject
     */
    public GameObject(int id, String name) {
        this.id = id;
        this.name = name;
        this.components = new ArrayList<>();
    }

    /**
     * Adds a new Component to this GameObject
     * @param component The Component to add
     */
    public void addComponent(Component component) {
        components.add(component);
    }

    /**
     * Removes a Component from this GameObject
     * @param component The Component to remove
     */
    public void removeComponent(Component component) {
        components.remove(component);
    }

    /**
     * Gets a Component from this GameObject by its class
     * @param clazz The class of the Component to get
     * @return The Component with the given class, or null if it does not exist
     */
    public Component getComponent(Class<? extends Component> clazz) {
        for (Component component : components) {
            if (clazz.isInstance(component)) {
                return component;
            }
        }
        return null;
    }

    /**
     * Runs the start method for all Components attached to this GameObject
     */
    public void start() {
        for (Component component : components) {
            component.start();
        }
    }

    /**
     * Runs the update method for all Components attached to this GameObject
     */
    public void update() {
        for (Component component : components) {
            component.update();
        }
    }
}
