package org.project.componentsystem.components;

import org.project.componentsystem.GameObject;
import org.project.core.Input;
import org.project.utils.Vec2;

public class KeyboardController extends Component {
    public KeyboardController(GameObject gameObject, String name) {
        super(gameObject, name);
    }

    @Override
    public void update() {
        Input.getInstance();
        if(Input.isKeyPressed(Input.KEY_W)) {
            super.getGameObject().setPosition(super.getGameObject().getPosition().add(new Vec2(0, -3)));
        }
        if(Input.isKeyPressed(Input.KEY_A)) {
            super.getGameObject().setPosition(super.getGameObject().getPosition().add(new Vec2(-3, 0)));
        }
        if(Input.isKeyPressed(Input.KEY_S)) {
            super.getGameObject().setPosition(super.getGameObject().getPosition().add(new Vec2(0, 3)));
        }
        if(Input.isKeyPressed(Input.KEY_D)) {
            super.getGameObject().setPosition(super.getGameObject().getPosition().add(new Vec2(3, 0)));
        }
    }
}