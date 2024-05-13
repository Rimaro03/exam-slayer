package org.project.componentsystem.components;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.GameObject;
import org.project.core.rendering.Renderer;
import org.project.items.Item;

import java.awt.*;
import java.util.HashMap;

@Setter @Getter @Log4j2
public class PlayerStats extends Component {
    private Stats stats;
    private HashMap<Item, Integer> inventory;

    /**
     * Initializes a new PlayerStats with the given GameObject
     * @param gameObject The reference to the GameObject that this PlayerStats is attached to
     * @param health The health of this PlayerStats
     * @param attack The attack of this PlayerStats
     * @param defense The defense of this PlayerStats
     * @param speed The speed of this PlayerStats
     */
    public PlayerStats(GameObject gameObject, int health, int attack, int defense, int speed) {
        this(gameObject, true, health, attack, defense, speed);
    }

    /**
     * Initializes a new PlayerStats with the given GameObject and enabled status
     * @param gameObject The reference to the GameObject that this PlayerStats is attached to
     * @param enabled Whether this PlayerStats is enabled or not
     * @param health The health of this PlayerStats
     * @param attack The attack of this PlayerStats
     * @param defense The defense of this PlayerStats
     * @param speed The speed of this PlayerStats
     */
    public PlayerStats(GameObject gameObject, boolean enabled, int health, int attack, int defense, int speed) {
        super(gameObject, enabled);
        this.stats = new Stats();
        this.stats.setHealth(health);
        this.stats.setAttack(attack);
        this.stats.setDefense(defense);
        this.stats.setSpeed(speed);
    }

    @Override
    public void start() { }

    @Override
    public void update() {
        Renderer.addTextToRenderQueue(50, 50, "Health: " + stats.getHealth(), Color.WHITE);
    }

    /**
     * Destory the component
     */
    @Override
    public void destory() {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
