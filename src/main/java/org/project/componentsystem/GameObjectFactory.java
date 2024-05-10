package org.project.componentsystem;

import org.project.componentsystem.components.AnimatedSpriteRenderer;
import org.project.componentsystem.components.Component;
import org.project.componentsystem.components.KeyboardController;
import org.project.componentsystem.components.PlayerStats;
import org.project.componentsystem.components.colliders.BoxCollider;
import org.project.componentsystem.components.colliders.CircleCollider;
import org.project.utils.Vec2;

public class GameObjectFactory {
    public static GameObject createGameObject(String name) {
        return new GameObject(name);
    }

    public static GameObject createPlayer(String name) {
        GameObject player = createGameObject(name);

        return createGameObject(
                player,
                new PlayerStats(player, 100, 10, 5, 5),
                new AnimatedSpriteRenderer(player, "resources/textures/characters/MainCharacter.png", 32, 32),
                new KeyboardController(player),
                new CircleCollider(player, 4, true)
        );
    }
    private static GameObject createGameObject(GameObject obj, Component... components) {
        for (Component component : components) {
            obj.addComponent(component);
        }
        return obj;
    }
}
