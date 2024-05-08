package org.project.componentsystem.components;

import org.project.componentsystem.GameObject;
import org.project.core.Input;
import org.project.core.Time;
import org.project.utils.Vec2;

public class KeyboardController extends Component {
    AnimatedSpriteRenderer spriteRenderer;
    float animationSpeed = 10.f;
    public KeyboardController(GameObject gameObject, String name) {
        super(gameObject);
    }

    public void start() {
        spriteRenderer = (AnimatedSpriteRenderer) getGameObject().getComponent(AnimatedSpriteRenderer.class);
    }
    @Override
    public void update() {
        boolean isMoving = false;
        int animationStep = (int)(Time.seconds() * animationSpeed) % 3;

        if(Input.isKeyPressed(Input.KEY_A)) {
            super.getGameObject().setPosition(super.getGameObject().getPosition().add(new Vec2(-3, 0)));
            isMoving = true;

            spriteRenderer.setSheetState(animationStep, 3);
        }
        if(Input.isKeyPressed(Input.KEY_D)) {
            super.getGameObject().setPosition(super.getGameObject().getPosition().add(new Vec2(3, 0)));
            isMoving = true;

            spriteRenderer.setSheetState(animationStep, 1);
        }
        if(Input.isKeyPressed(Input.KEY_S)) {
            super.getGameObject().setPosition(super.getGameObject().getPosition().add(new Vec2(0, 3)));
            isMoving = true;

            spriteRenderer.setSheetState(animationStep, 0);
        }
        if(Input.isKeyPressed(Input.KEY_W)) {
            super.getGameObject().setPosition(super.getGameObject().getPosition().add(new Vec2(0, -3)));
            isMoving = true;

            spriteRenderer.setSheetState(animationStep, 2);
        }

        if(!isMoving) { spriteRenderer.setSheetState(0, 0);}
    }
}