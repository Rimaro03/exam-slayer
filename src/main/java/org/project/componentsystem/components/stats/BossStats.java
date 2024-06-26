package org.project.componentsystem.components.stats;

import lombok.Getter;
import lombok.Setter;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.weapons.WeaponType;

@Getter @Setter
public class BossStats extends Stats {
    private final WeaponType weapon;
    private final float attackCooldown;
    private final float moveCooldown;
    /**
     * Initializes a new EntityStats with the given GameObject and stats
     * @param gameObject The reference to the GameObject that this EntityStats is attached to
     * @param health     The health of this Boss
     * @param speed      The speed of this Boss
     * @param weapon     The weapon of this Boss
     */
    public BossStats(GameObject gameObject, int health, float speed, WeaponType weapon, float attackCooldown, float moveCooldown) {
        this(gameObject, true, health, speed, weapon, attackCooldown, moveCooldown);
    }

    /**
     * Initializes a new EntityStats with the given GameObject, enabled status, and stats
     * @param gameObject The reference to the GameObject that this EntityStats is attached to
     * @param enabled    Whether this EntityStats is enabled or not
     * @param health     The health of this Boss
     * @param speed      The speed of this Boss
     * @param weapon     The weapon of this Boss
     */
    public BossStats(GameObject gameObject, boolean enabled, int health, float speed, WeaponType weapon, float attackCooldown, float moveCooldown) {
        super(gameObject, enabled, health, speed);
        this.weapon = weapon;
        this.attackCooldown = attackCooldown;
        this.moveCooldown = moveCooldown;
    }

    @Override
    public void start() {
    }

    @Override
    public void update() {
    }

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
