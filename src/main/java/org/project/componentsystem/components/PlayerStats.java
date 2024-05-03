package org.project.componentsystem.components;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.items.Item;

import java.util.HashMap;

@Setter @Getter
public class PlayerStats extends Component {
    private int health;
    private HashMap<Item, Integer> inventory;
    private static final Logger logger = LogManager.getLogger(PlayerStats.class);

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
        logger.info("Stats component started");
    }
    @Override
    public void update() {
        logger.info("Stats component updated");
    }
}
