package org.project.componentsystem.components.stats;

import lombok.Getter;
import lombok.Setter;
import org.project.componentsystem.GameObject;

@Setter
@Getter
/**
 * A component that keeps track of the health, speed, attack damage, attack speed, and attack range of an Entity game object.
 */
public class EntityStats extends Stats {
    private int attackDamage;
    private float attackSpeed;
    private float attackRange;
    /**
     * Initializes a new EntityStats with the given GameObject and stats
     *
     * @param gameObject The reference to the GameObject that this EntityStats is attached to
     * @param health     The health of this Entity
     * @param speed      The speed of this Entity
     * @param attackDamage The attack damage of this Entity
     * @param attackSpeed The attack speed of this Entity
     * @param attackRange The attack range of this Entity
     */
    public EntityStats(GameObject gameObject, int health, int speed, int attackDamage, float attackSpeed, float attackRange) {
        this(gameObject, true, health, speed, attackDamage, attackSpeed, attackRange);
    }

    /**
     * Initializes a new EntityStats with the given GameObject, enabled status, and stats
     *
     * @param gameObject The reference to the GameObject that this EntityStats is attached to
     * @param enabled    Whether this EntityStats is enabled or not
     * @param health     The health of this Entity
     * @param speed      The speed of this Entity
     * @param attackDamage The attack damage of this Entity
     * @param attackSpeed The attack speed of this Entity
     * @param attackRange The attack range of this Entity
     */
    public EntityStats(GameObject gameObject, boolean enabled, int health, int speed, int attackDamage, float attackSpeed, float attackRange) {
        super(gameObject, enabled, health, speed);
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.attackRange = attackRange;
    }

    @Override
    public void start() {
    }

    @Override
    public void update() {
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
