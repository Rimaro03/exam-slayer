package org.project.componentsystem.components;

import lombok.Getter;
import lombok.Setter;
import org.project.componentsystem.GameObject;

@Setter @Getter
public class EntityStats extends Component {
    private Stats stats;
    /**
     * Initializes a new EntityStats with the given GameObject, id, name
     * @param gameObject The reference to the GameObject that this EntityStats is attached to
     * @param name The name of this EntityStats
     * @param health The health of this EntityStats
     * @param attack The attack of this EntityStats
     * @param defense The defense of this EntityStats
     * @param speed The speed of this EntityStats
     */
    public EntityStats(GameObject gameObject, String name, int health, int attack, int defense, int speed) {
        this(gameObject, name, true, health, attack, defense, speed);
    }

    /**
     * Initializes a new EntityStats with the given GameObject, id, name, and enabled status
     * @param gameObject The reference to the GameObject that this EntityStats is attached to
     * @param name The name of this EntityStats
     * @param enabled Whether this EntityStats is enabled or not
     * @param health The health of this EntityStats
     * @param attack The attack of this EntityStats
     * @param defense The defense of this EntityStats
     * @param speed The speed of this EntityStats
     */
    public EntityStats(GameObject gameObject, String name, boolean enabled, int health, int attack, int defense, int speed) {
        super(gameObject, name, enabled);
        this.stats = new Stats();
        this.stats.setHealth(health);
        this.stats.setAttack(attack);
        this.stats.setDefense(defense);
        this.stats.setSpeed(speed);
    }

    @Override
    public void start() { }

    @Override
    public void update() { }
}
