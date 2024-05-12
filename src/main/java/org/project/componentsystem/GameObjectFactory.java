package org.project.componentsystem;

import org.project.componentsystem.components.AnimatedSpriteRenderer;
import org.project.componentsystem.components.Component;
import org.project.componentsystem.components.PlayerController;
import org.project.componentsystem.components.PlayerStats;
import org.project.componentsystem.components.colliders.BoxCollider;
import org.project.componentsystem.components.colliders.Collider;
import org.project.componentsystem.components.colliders.DoorCollider;
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
                new BoxCollider(player, new Vec2(1.2f, 2), true, true)
        );
    }
    public static GameObject createRoomGameObject(){
        GameObject room = createGameObject("Room");

        return createGameObject(
                room,
                new AnimatedSpriteRenderer(room, "resources/textures/map/map.png", 256, 256),
                new BoxCollider(room, new Vec2(Room.SIZE - 2.4f, Room.SIZE - 2), false, false)
        );
    }
    public static GameObject createDoorGameObject(int direction, Collider roomCollider){
        GameObject door = createGameObject("Door");
        DoorCollider doorCollider = new DoorCollider(door, new Vec2(2, 2), false, true, direction);
        doorCollider.getIgnoreColliders().add(roomCollider);
        roomCollider.getIgnoreColliders().add(doorCollider);
        return createGameObject(door, doorCollider);
    }
    private static GameObject createGameObject(GameObject obj, Component... components) {
        for (Component component : components) {
            obj.addComponent(component);
        }
        return obj;
    }
}
