package org.project.componentsystem.components.stats;

import lombok.Getter;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.Component;
import org.project.core.Game;

@Getter
public abstract class Stats extends Component {
    protected int health;
    protected int attack;
    protected int defense;
    protected int speed;
    protected int baseSpeed;
    protected int baseHealth;

    public Stats(GameObject gameObject, boolean enabled, int health, int attack, int defense, int speed) {
        super(gameObject, enabled);
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.baseSpeed = speed;
        this.baseHealth = health;
    }

    public Stats(GameObject gameObject, int health, int attack, int defense, int speed) {
        super(gameObject);
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.baseSpeed = speed;
        this.baseHealth = health;
    }

    public void takeDamage(int damage){
        health -= damage;
        if(health <= 0){
            Game.getCurrentLevel().destroyGameObject(getGameObject());
        }
    }

}
