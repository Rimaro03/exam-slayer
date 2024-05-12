package org.project.componentsystem;

import org.project.componentsystem.components.AnimatedSpriteRenderer;
import org.project.componentsystem.components.Component;
import org.project.componentsystem.components.PlayerController;
import org.project.componentsystem.components.PlayerStats;
import org.project.componentsystem.components.colliders.BoxCollider;
import org.project.generation.Room;
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
                new PlayerController(player),
                new BoxCollider(player, new Vec2(2, 2), true, true)
        );
    }
    public static GameObject createRoomGameObject(){
        GameObject room = createGameObject("Room");

        return createGameObject(
                room,
                new BoxCollider(room, new Vec2(Room.SIZE, Room.SIZE), false, false)
                /* To-do: add sprite renderer. */
        );
    }
    public static GameObject createDoorGameObject(){
        GameObject door = createGameObject("Door");

        return createGameObject(
                door,
                new BoxCollider(door, new Vec2(2, 2), false, true)
        );
    }
    private static GameObject createGameObject(GameObject obj, Component... components) {
        for (Component component : components) {
            obj.addComponent(component);
        }
        return obj;
    }
}
