package org.project.componentsystem.components;

import org.project.componentsystem.GameObject;
import org.project.componentsystem.GameObjectFactory;
import org.project.core.Game;
import org.project.core.Input;
import org.project.core.Time;
import org.project.utils.Vec2;

public class PlayerShootingController extends Component {
    private float timeToShoot = 0;
    private final float shootInterval;
    public PlayerShootingController(GameObject gameObject, boolean enabled, float shootInterval) {
        super(gameObject, enabled);
        this.shootInterval = shootInterval;
    }


    public PlayerShootingController(GameObject gameObject, float shootInterval) {
        super(gameObject);
        this.shootInterval = shootInterval;
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {
        if(timeToShoot > 0){
            timeToShoot -= Time.TIME_STEP_IN_SECONDS;
            return;
        }

       Vec2 projectileSpeed = null;
        if(Input.isKeyPressed(Input.KEY_UP))
            projectileSpeed = new Vec2(0, 10);
        else if (Input.isKeyPressed(Input.KEY_DOWN))
            projectileSpeed = new Vec2(0, -10);
        else if (Input.isKeyPressed(Input.KEY_LEFT))
            projectileSpeed = new Vec2(-10, 0);
        else if (Input.isKeyPressed(Input.KEY_RIGHT))
            projectileSpeed = new Vec2(10, 0);

        if(projectileSpeed != null){
            GameObject bullet = GameObjectFactory.createProjectile(100, projectileSpeed, getGameObject());
            Game.getCurrentLevel().instantiateGameObject(bullet, getGameObject().getPosition());
            timeToShoot = shootInterval;
        }
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
