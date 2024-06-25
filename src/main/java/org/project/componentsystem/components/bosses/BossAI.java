package org.project.componentsystem.components.bosses;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.GameObjectFactory;
import org.project.componentsystem.components.Component;
import org.project.componentsystem.components.enemies.RoomLocker;
import org.project.componentsystem.components.stats.BossStats;
import org.project.componentsystem.components.stats.EntityStats;
import org.project.componentsystem.components.weapons.WeaponInfo;
import org.project.componentsystem.components.weapons.WeaponType;
import org.project.core.GameStateListener;
import org.project.core.Game;
import org.project.generation.Room;
import org.project.generation.wavecollapse.Direction;
import org.project.utils.Vec2;

import java.util.Random;

public class BossAI extends Component implements GameStateListener {
    private float attackCooldown;
    private float moveCooldown;
    private Vec2 targetPosition;
    private BossStats stats;

    public BossAI(GameObject gameObject, boolean enabled) {
        super(gameObject, enabled);
        attackCooldown = 0;
        moveCooldown = 0;
    }

    public BossAI(GameObject gameObject) {
        this(gameObject, true);
    }

    @Override
    public void start() {
        stats = (BossStats) getGameObject().getComponent(BossStats.class);
        Game.addGameStateListener(this);

        RoomLocker roomLocker = (RoomLocker) Game.getCurrentLevel().findGameObject("Room").getComponent(RoomLocker.class);
        roomLocker.addEntity(this);
    }

    @Override
    public void update() {
        if(moveCooldown <= 0){
            Random rand = new Random();
            targetPosition = new Vec2((rand.nextFloat() * 2 - 1) * Room.SIZE, (rand.nextFloat() * 2 - 1) * Room.SIZE);
            moveCooldown = stats.getMoveCooldown();
        }
        if(attackCooldown <= 0){
            for (int i = 0; i < 4; i++) {
                Vec2 dir = new Vec2(Direction.x(0, i), Direction.y(0, i));
                GameObject projectile = GameObjectFactory.createProjectile(WeaponInfo.getWeaponData(stats.getWeapon()), dir, getGameObject());
                Game.getCurrentLevel().instantiateGameObject(projectile, getPosition());
            }
            attackCooldown = stats.getAttackCooldown();
        }

        attackCooldown -= Game.getTime().deltaTime();
        moveCooldown -= Game.getTime().deltaTime();
        Vec2 direction = targetPosition.subtract(getGameObject().getPosition()).normalized();
        getGameObject().setPosition(getGameObject().getPosition().add(direction.multiply(stats.getSpeed() * Game.getTime().deltaTime())));
    }

    @Override
    public void destory() {
        Game.removeGameStateListener(this);

        RoomLocker roomLocker = (RoomLocker) Game.getCurrentLevel().findGameObject("Room").getComponent(RoomLocker.class);
        roomLocker.removeEntity(this);

        BossKillCounter bossKillCounter = (BossKillCounter) Game.getCurrentLevel().findGameObject("Player").getComponent(BossKillCounter.class);
        bossKillCounter.addKill();
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
