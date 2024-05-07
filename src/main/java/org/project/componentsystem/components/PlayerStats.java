package org.project.componentsystem.components;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import org.project.componentsystem.GameObject;
import org.project.items.Item;

import java.util.HashMap;

@Setter @Getter @Log4j2
public class PlayerStats extends Component {
    private Stats stats;
    private HashMap<Item, Integer> inventory;

    /**
     * Initializes a new PlayerStats with the given GameObject, id, name
     * @param gameObject The reference to the GameObject that this PlayerStats is attached to
     * @param name The name of this PlayerStats
     * @param health The health of this PlayerStats
     * @param attack The attack of this PlayerStats
     * @param defense The defense of this PlayerStats
     * @param speed The speed of this PlayerStats
     */
    public PlayerStats(GameObject gameObject, String name, int health, int attack, int defense, int speed) {
        this(gameObject, name, true, health, attack, defense, speed);
    }

    /**
     * Initializes a new PlayerStats with the given GameObject, id, name, and enabled status
     * @param gameObject The reference to the GameObject that this PlayerStats is attached to
     * @param name The name of this PlayerStats
     * @param enabled Whether this PlayerStats is enabled or not
     * @param health The health of this PlayerStats
     * @param attack The attack of this PlayerStats
     * @param defense The defense of this PlayerStats
     * @param speed The speed of this PlayerStats
     */
    public PlayerStats(GameObject gameObject, String name, boolean enabled, int health, int attack, int defense, int speed) {
        super(gameObject, name, enabled);
        this.stats = new Stats();
        this.stats.setHealth(health);
        this.stats.setAttack(attack);
        this.stats.setDefense(defense);
        this.stats.setSpeed(speed);
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
