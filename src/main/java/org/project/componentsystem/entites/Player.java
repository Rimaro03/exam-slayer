package org.project.componentsystem.entites;

import lombok.Getter;
import lombok.Setter;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.Component;
import org.project.componentsystem.components.PlayerPosition;
import org.project.componentsystem.components.PlayerStats;
import org.project.componentsystem.items.Item;
import org.project.utils.Vec2;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Getter @Setter
public class Player {
    private GameObject gameObject;
    private int id;
    private String name;

    /**
     * Initializes a new Player with the given id and name
     * @param id The unique identifier for this Player
     * @param name The name of this Player
     */
    public Player(AtomicInteger id, String name, int health) {
        this.id = id.get();
        this.name = name;
        this.gameObject = new GameObject(id.getAndAdd(1), name);
        addComponent(new PlayerPosition(gameObject, id.getAndAdd(1), "Position", new Vec2(0, 0)));
        addComponent(new PlayerStats(gameObject, id.getAndAdd(1), "Stats", health));
    }

    /**
     * Adds a new Component to this Player
     * @param component The Component to add
     */
    public void addComponent(Component component) {
        gameObject.addComponent(component);
    }

    /**
     * Removes a Component from this Player
     * @param component The Component to remove
     */
    public void removeComponent(Component component) {
        gameObject.removeComponent(component);
    }

    /**
     * Gets a Component from this Player by its class
     * @param clazz The class of the Component to get
     * @return The Component with the given class, or null if it does not exist
     */
    public Component getComponent(Class<? extends Component> clazz) {
        return gameObject.getComponent(clazz);
    }

    /**
     * Gets the position of this Player
     * @return The position of this Player
     */
    public Vec2 getPosition() {
        return ((PlayerPosition) getComponent(PlayerPosition.class)).getPosition();
    }

    /**
     * Sets the position of this Player
     * @param position The new position of this Player
     */
    public void setPosition(Vec2 position) {
        ((PlayerPosition) getComponent(PlayerPosition.class)).setPosition(position);
    }

    /**
     * Sets the health of this Player
     * @param health The new health of this Player
     */
    public void setHealth(int health) {
        ((PlayerStats) getComponent(PlayerStats.class)).setHealth(health);
    }

    /**
     * Gets the health of this Player
     * @return The health of this Player
     */
    public int getHealth() {
        return ((PlayerStats) getComponent(PlayerStats.class)).getHealth();
    }

    /**
     * Sets the inventory of this Player
     * @param inventory The new inventory of this Player
     */
    public void setInventory(HashMap<Item, Integer> inventory) {
        ((PlayerStats) getComponent(PlayerStats.class)).setInventory(inventory);
    }

    /**
     * Gets the inventory of this Player
     * @return The inventory of this Player
     */
    public HashMap<Item, Integer> getInventory() {
        return ((PlayerStats) getComponent(PlayerStats.class)).getInventory();
    }



    /**
     * Runs the start method for all Components attached to this Player
     */
    public void start() {
        gameObject.start();
    }

    /**
     * Runs the update method for all Components attached to this Player
     */
    public void update() {
        gameObject.update();
    }
}
