package org.project.componentsystem.components.enemies;

import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.Component;
import org.project.componentsystem.components.stats.EntityStats;
import org.project.componentsystem.components.stats.Stats;
import org.project.core.Game;
import org.project.core.GameStateListener;
import org.project.utils.Vec2;

public class EnemyAI extends Component implements GameStateListener {
    private EntityStats stats;
    private GameObject target;
    private float timeToAttack;

    public EnemyAI(GameObject gameObject, boolean enabled) {
        super(gameObject, enabled);
    }

    public EnemyAI(GameObject gameObject) {
        this(gameObject, true);
        timeToAttack = 0;
    }

    @Override
    public void start() {
        stats = (EntityStats) getGameObject().getComponent(EntityStats.class);
        target = Game.getCurrentLevel().findGameObject("Player");

        RoomLocker roomLocker = (RoomLocker) Game.getCurrentLevel().findGameObject("Room").getComponent(RoomLocker.class);
        roomLocker.addEntity(this);

        Game.addGameStateListener(this);
    }

    @Override
    public void update() {
        Vec2 relativeToTarget = target.getPosition().subtract(getGameObject().getPosition());
        getGameObject().setPosition(getGameObject().getPosition().add(relativeToTarget.normalized().multiply(stats.getSpeed() * Game.getTime().deltaTime())));

        timeToAttack -= Game.getTime().deltaTime();
        if(timeToAttack < 0 && relativeToTarget.magnitude() < stats.getAttackRange()) {
            Stats targetStats = (Stats) target.getComponent(Stats.class);
            targetStats.takeDamage(this.stats.getAttackDamage());
            timeToAttack = stats.getAttackSpeed();
        }
    }

    @Override
    public void destory() {
        RoomLocker roomLocker = (RoomLocker) Game.getCurrentLevel().findGameObject("Room").getComponent(RoomLocker.class);
        roomLocker.removeEntity(this);

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
