package org.project.componentsystem.components.colliders;

import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.GameObject;
import org.project.core.Game;
import org.project.utils.Vec2;

@Log4j2
public class ProjectileCollider extends AbstractBoxCollider {
    private final GameObject parent;
    public ProjectileCollider(GameObject gameObject, boolean enabled, Vec2 size, boolean movable, boolean inside, GameObject parent) {
        super(gameObject, enabled, size, movable, inside);
        this.parent = parent;
    }

    public ProjectileCollider(GameObject gameObject, Vec2 size, boolean movable, boolean inside, GameObject parent) {
        super(gameObject, size, movable, inside);
        this.parent = parent;
    }

    @Override
    public void onCollide(Collider other) {


        if(other.getGameObject() != parent) {
            Game.getCurrentLevel().destroyGameObject(this.getGameObject());
           log.info("Projectile collided with {}", other.getGameObject().getName());
           /*
            TODO: Implement this
            Stats stats = other.getGameObject().getComponent(Stats.class);
            if(stats != null){
                Projectile projectile = (Projectile)this.getGameObject().getComponent(Projectile.class);
                stats.takeDamage(projectile.getDamage());
            }
            */
        }
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
        Game.getCurrentLevel().getPhysicsEngine().addCollider(this);
    }

    @Override
    public void onDisable() {
        Game.getCurrentLevel().getPhysicsEngine().removeCollider(this);
    }
}
