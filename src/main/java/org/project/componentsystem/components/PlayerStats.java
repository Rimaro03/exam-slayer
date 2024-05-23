package org.project.componentsystem.components;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.GameObject;
import org.project.core.rendering.Renderer;
import org.project.items.Heart;
import org.project.items.Item;
import org.project.utils.Vec2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.HashMap;

@Setter @Getter @Log4j2
public class PlayerStats extends Component {
    private Stats stats;
    private HashMap<Item, Integer> inventory = new HashMap<>();
    private Vec2 position = new Vec2(-10, 7);

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
        this.stats.setBaseSpeed(speed);
        this.stats.setBaseHealth(health);
    }

    @Override
    public void start() { }

    @Override @SneakyThrows
    public void update() {
        updateSpeed();
        updateHealth();
        Renderer.addTextToRenderQueue(position, "Health: " + stats.getHealth(), Color.WHITE, 10, 2);
        Renderer.addTextToRenderQueue(position.add(new Vec2(0, -0.5f)), "Attack: " + stats.getAttack(), Color.WHITE, 10, 2);
        Renderer.addTextToRenderQueue(position.add(new Vec2(0, -1)), "Defense: " + stats.getDefense(), Color.WHITE, 10, 2);
        Renderer.addTextToRenderQueue(position.add(new Vec2(0, -1.5f)), "Speed: " + stats.getSpeed(), Color.WHITE, 10, 2);
        Object[] keys = inventory.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            Item item = (Item) keys[i];
            Renderer.addImageToRenderQueue(
                    position.add(new Vec2(0.3f, -2 - i * 0.6f)),
                    ImageIO.read(new File(item.getInvetoryPath())),
                    i
            );
            for (int j = 0; j < inventory.get(item); j++) {
                Renderer.addImageToRenderQueue(
                        position.add(new Vec2(0.3f + j / 2f * 0.6f, -2 - i * 0.6f)),
                        ImageIO.read(new File(item.getInvetoryPath())),
                        i + j
                );
            }
        }


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

    private void updateSpeed() {
        int totalWeight = 0;
        for (Item item : inventory.keySet()) {
            totalWeight += item.getWeight() * inventory.get(item);
        }
        int tempSpeed = stats.getBaseSpeed() - totalWeight;
        stats.setSpeed(Math.max(tempSpeed, 5));
    }

    private void updateHealth() {
        int totalHealth = 0;
        for (Item item : inventory.keySet()) {
            if (item instanceof Heart) {
                totalHealth += ((Heart) item).getHealth() * inventory.get(item);
            }
        }
        stats.setHealth(stats.getBaseHealth() + totalHealth);
    }
}
