package org.project.componentsystem;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.components.Component;
import org.project.utils.Vec2;

import java.util.ArrayList;

@Getter @Setter @Log4j2
public class GameObject {
    private String name;
    private ArrayList<Component> components;
    private Vec2 position;

    /**
     * Initializes a new GameObject with the given name
     * @param name The name of this GameObject
     */
    public GameObject(String name) {
        this.name = name;
        this.components = new ArrayList<>();
        this.position = new Vec2(0, 0);
    }

    /**
     * Initializes a new GameObject with the given name and position
     * @param name The name of this GameObject
     * @param position The position of this GameObject
     */
    public GameObject(String name, Vec2 position) {
        this.name = name;
        this.components = new ArrayList<>();
        this.position = position;
    }

    /**
     * Adds a new Component to this GameObject
     * @param component The Component to add
     * @return This GameObject
     */
    public GameObject addComponent(Component component) {
        if (!components.contains(component)) {
            log.info("Starting {} component", component.toString());
            component.start();
            components.add(component);
        }
        return this;
    }

    /**
     * Removes a Component from this GameObject
     * @param component The Component to remove
     */
    public GameObject removeComponent(Component component) {
        if (components.contains(component)){
            component.destory();
            components.remove(component);
        }
        return this;
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

    @Override
    public String toString() {
        return "GameObject{" +
                "name='" + name + '\'' +
                ", components=" + components +
                ", position=" + position +
                "}";
    }
}
