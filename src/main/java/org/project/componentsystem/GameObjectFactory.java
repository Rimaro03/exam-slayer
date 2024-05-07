package org.project.componentsystem;

import org.project.componentsystem.components.Component;
import org.project.componentsystem.components.PlayerStats;
import org.project.utils.Vec2;

public class GameObjectFactory {
    public static GameObject createGameObject(String name) {
        return new GameObject(name);
    }

    public static GameObject createPlayer(String name) {
        GameObject player = createGameObject(name);
        return createGameObject(
                player,
                new PlayerStats(player, "PlayerStats", 100, 10, 5, 5)
        );
    }
    private static GameObject createGameObject(GameObject obj, Component... components) {
        for (Component component : components) {
            obj.addComponent(component);
        }
        return obj;
    }
}
