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

import java.util.Random;

public class GameObjectFactory {
    public static GameObject createGameObject(String name) {
        return new GameObject(name);
    }
    private static GameObject createGameObject(GameObject obj, Component... components) {
        for (Component component : components) {
            obj.addComponent(component);
        }
        return obj;
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
    public static GameObject createRoomGameObject(int type){
        GameObject room = createGameObject("Room");

        return createGameObject(
                room,
                new AnimatedSpriteRenderer(room, "resources/textures/map/map_" + type + ".png", 256, 256),
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
    public static GameObject[] createEnemies(int amount){
        GameObject[] enemies = new GameObject[amount];
        Random rand = new Random();

        for (int i = 0; i < amount; i++) {
            GameObject enemy = createGameObject("Enemy");
            createGameObject(
                    enemy,
                    //new AnimatedSpriteRenderer(enemy, "resources/textures/characters/Enemy.png", 32, 32),
                    new BoxCollider(enemy, new Vec2(2, 2), true, true)
            );
            enemy.setPosition(new Vec2((.5f - rand.nextFloat()) * (Room.SIZE - 2), (.5f - rand.nextFloat()) * (Room.SIZE - 2)));
            enemies[i] = enemy;
        }
        return enemies;
    }
    public static GameObject createBoss(int id){
        // TODO : Implement boss creation
        GameObject boss = createGameObject("Boss");
        return createGameObject(boss, new BoxCollider(boss, new Vec2(4, 4), true, true));
    }
}
