package org.project.componentsystem.components;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import org.project.componentsystem.GameObject;
import org.project.componentsystem.items.Item;

import java.util.HashMap;

@Setter @Getter @Log4j2
public class PlayerStats extends Component {
    private int health;
    private HashMap<Item, Integer> inventory;
    /**
     * Initializes a new PlayerStats with the given GameObject, id, name, and enabled status
     * @param gameObject The reference to the GameObject that this PlayerStats is attached to
     * @param id The unique identifier for this PlayerStats
     * @param name The name of this PlayerStats
     */
    public PlayerStats(GameObject gameObject, int id, String name, int health) {
        super(gameObject, id, name);
        this.health = health;
        this.inventory = new HashMap<>();
    }

    /**
     * Initializes a new PlayerStats with the given GameObject, id, name, and enabled status
     * @param gameObject The reference to the GameObject that this PlayerStats is attached to
     * @param id The unique identifier for this PlayerStats
     * @param name The name of this PlayerStats
     * @param enabled Whether this PlayerStats is enabled or not
     */
    public PlayerStats(GameObject gameObject, int id, String name, boolean enabled, int health) {
        super(gameObject, id, name, enabled);
        this.health = health;
        this.inventory = new HashMap<>();
    }

    @Override
    public void start() {
        log.info("Stats component started");
    }
    @Override
    public void update() {
        log.info("Stats component updated");
    }
}
