package org.project.componentsystem.components.stats;

import lombok.Getter;
import lombok.Setter;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.Component;
import org.project.core.Game;

@Getter
@Setter
public abstract class Stats extends Component {
    protected int health;
    protected float speed;
    protected float baseSpeed;
    protected int baseHealth;

    public Stats(GameObject gameObject, boolean enabled, int health, float speed) {
        super(gameObject, enabled);
        this.health = health;
        this.speed = speed;
        this.baseSpeed = speed;
        this.baseHealth = health;
    }

    public Stats(GameObject gameObject, int health, int speed) {
        super(gameObject);
        this.health = health;
        this.speed = speed;
        this.baseSpeed = speed;
        this.baseHealth = health;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            Game.getCurrentLevel().destroyGameObject(getGameObject());
        }
    }

}
