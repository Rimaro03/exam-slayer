package org.project.componentsystem.components;

import lombok.Getter;
import org.project.componentsystem.GameObject;
import org.project.core.Game;
import org.project.core.GameStateListener;
import org.project.utils.Vec2;


public class Projectile extends Component implements GameStateListener {
    @Getter
    private final int damage;
    private final Vec2 velocity;

    public Projectile(GameObject gameObject, boolean enabled, int damage, Vec2 velocity) {
        super(gameObject, enabled);
        this.damage = damage;
        this.velocity = velocity;
    }

    public Projectile(GameObject gameObject, int damage, Vec2 velocity) {
        super(gameObject);
        this.damage = damage;
        this.velocity = velocity;
    }

    @Override
    public void start() {
        Game.addGameStateListener(this);
    }

    @Override
    public void update() {
        getGameObject().setPosition(
                getPosition().add(
                        velocity.multiply(Game.getTime().deltaTime())
                )
        );
    }

    @Override
    public void destory() {
        Game.removeGameStateListener(this);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onGamePaused() {
        setEnabled(false);
    }

    @Override
    public void onGameResumed() {
        setEnabled(true);
    }
}
