package org.project.componentsystem;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.components.Component;
import org.project.utils.Vec2;

import java.util.ArrayList;

@Getter
@Setter
@Log4j2
public class GameObject {
    private String name;
    private ArrayList<Component> components;
    private Vec2 position;
    private boolean enabled;

    /**
     * Initializes a new GameObject with the given name
     *
     * @param name The name of this GameObject
     */
    public GameObject(String name) {
        this.name = name;
        this.components = new ArrayList<>();
        this.position = new Vec2(0, 0);
        this.enabled = true;
    }

    /**
     * Initializes a new GameObject with the given name and position
     *
     * @param name     The name of this GameObject
     * @param position The position of this GameObject
     */
    public GameObject(String name, Vec2 position) {
        this.name = name;
        this.components = new ArrayList<>();
        this.position = position;
        this.enabled = true;
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) {
            return;
        } // don't do anything if the state is the same

        this.enabled = enabled;

        for (Component component : components)
            component.setEnabled(enabled);
    }

    /**
     * Adds a new Component to this GameObject
     *
     * @param component The Component to add
     */
    public void addComponent(Component component) {
        if (!components.contains(component)) {
            component.setEnabled(enabled);
            components.add(component);
        }
    }

    /**
     * Removes a Component from this GameObject
     *
     * @param component The Component to remove
     */
    public void removeComponent(Component component) {
        if (components.contains(component)) {
            component.setEnabled(false);
            components.remove(component);
        }
    }

    /**
     * Gets a Component from this GameObject by its class
     *
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
        ArrayList<Component> componentsCopy = new ArrayList<>(components);
        for (Component component : componentsCopy) {
            component.start();
            log.info("Started component: {} on GameObject: {}", component.getClass().getSimpleName(), name);
        }
    }

    /**
     * Runs the update method for all Components attached to this GameObject
     */
    public void update() {
        for (Component component : components) {
            if(component.isEnabled())
                component.update();
        }
    }

    public void destroy() {
        for (Component component : components) {
            log.info("Destroyed component: {} on GameObject: {}", component.getClass().getSimpleName(), name);
            component.destory();
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
