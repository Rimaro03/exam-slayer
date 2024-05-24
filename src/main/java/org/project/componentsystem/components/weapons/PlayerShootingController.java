package org.project.componentsystem.components.weapons;

import lombok.Setter;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.GameObjectFactory;
import org.project.componentsystem.components.Component;
import org.project.core.Game;
import org.project.core.Input;
import org.project.core.Time;
import org.project.utils.Vec2;

public class PlayerShootingController extends Component {
    private float timeToShoot = 0;
    private final float shootInterval;
    @Setter
    private WeaponType weaponType = WeaponType.Sword; // DEBUG

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

       Vec2 projectileDir;
        if(Input.isKeyPressed(Input.KEY_UP))
            projectileDir = new Vec2(0, 1);
        else if (Input.isKeyPressed(Input.KEY_DOWN))
            projectileDir = new Vec2(0, -1);
        else if (Input.isKeyPressed(Input.KEY_LEFT))
            projectileDir = new Vec2(-1, 0);
        else if (Input.isKeyPressed(Input.KEY_RIGHT))
            projectileDir = new Vec2(1, 0);
        else
            return;

        GameObject bullet = GameObjectFactory.createProjectile(WeaponData.getWeaponData(weaponType), projectileDir, getGameObject());
        Game.getCurrentLevel().instantiateGameObject(bullet, getGameObject().getPosition());
        timeToShoot = shootInterval;
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
