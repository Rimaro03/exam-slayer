package org.project.componentsystem.components;

import lombok.Getter;
import org.project.componentsystem.GameObject;
import org.project.core.Time;
import org.project.utils.Vec2;


public class Projectile extends Component {
    @Getter
    private final int damage;
    private final Vec2 step;

    public Projectile(GameObject gameObject, boolean enabled, int damage, Vec2 velocity) {
        super(gameObject, enabled);
        this.damage = damage;
        this.step = velocity.multiply(Time.TIME_STEP_IN_SECONDS);
    }

    public Projectile(GameObject gameObject, int damage, Vec2 velocity) {
        super(gameObject);
        this.damage = damage;
        step = velocity.multiply(Time.TIME_STEP_IN_SECONDS);
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {
        getGameObject().setPosition(
                getPosition().add(
                        step
                )
        );
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
