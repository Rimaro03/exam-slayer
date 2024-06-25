package org.project.componentsystem.components;

import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.stats.PlayerStats;
import org.project.core.Game;
import org.project.core.GameStateListener;
import org.project.core.Input;
import org.project.core.Time;
import org.project.utils.Vec2;

@Log4j2
public class PlayerController extends Component implements GameStateListener {
    AnimatedSpriteRenderer spriteRenderer;
    float animationSpeed = 10.f;

    public PlayerController(GameObject gameObject) {
        super(gameObject);
    }

    public void start() {
        spriteRenderer = (AnimatedSpriteRenderer) getGameObject().getComponent(AnimatedSpriteRenderer.class);
        Game.addGameStateListener(this);
    }

    @Override
    public void update() {
        boolean isMoving = false;
        int animationStep = (int) (Game.getTime().seconds() * animationSpeed) % 4;
        Vec2 delta = new Vec2(0, 0);
        PlayerStats playerStats = (PlayerStats) getGameObject().getComponent(PlayerStats.class);
        float speed = playerStats.getSpeed();
        if (Input.isKeyPressed(Input.KEY_A)) {
            delta = delta.add(new Vec2(-1, 0));
            isMoving = true;

            spriteRenderer.setSheetState(animationStep, 3);
        }
        if (Input.isKeyPressed(Input.KEY_D)) {
            delta = delta.add(new Vec2(1, 0));
            isMoving = true;

            spriteRenderer.setSheetState(animationStep, 1);
        }
        if (Input.isKeyPressed(Input.KEY_W)) {
            delta = delta.add(new Vec2(0, 1));
            isMoving = true;

            spriteRenderer.setSheetState(animationStep, 2);
        }
        if (Input.isKeyPressed(Input.KEY_S)) {
            delta = delta.add(new Vec2(0, -1));
            isMoving = true;

            spriteRenderer.setSheetState(animationStep, 0);
        }

        getGameObject().setPosition(
                getGameObject().getPosition().add(
                        delta.normalized().multiply(speed * Game.getTime().deltaTime())
                )
        );

        if (!isMoving) {
            spriteRenderer.setSheetState(0, 0);
        }
    }

    /**
     * Destory the component
     */
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