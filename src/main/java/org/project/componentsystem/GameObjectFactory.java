package org.project.componentsystem;

import org.project.componentsystem.components.*;
import org.project.componentsystem.components.colliders.*;
import org.project.componentsystem.components.stats.EntityStats;
import org.project.componentsystem.components.stats.PlayerStats;
import org.project.componentsystem.components.weapons.PlayerShootingController;
import org.project.componentsystem.components.weapons.WeaponData;
import org.project.componentsystem.components.weapons.WeaponType;
import org.project.core.Game;
import org.project.generation.Room;
import org.project.items.Book;
import org.project.items.Item;
import org.project.items.Sword;
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
                new PlayerStats(player, 100, 10, 5, 15)
                        .addItem(new Book(
                                "PhysicsBook",
                                1,
                                "resources/textures/touchable/book.png",
                                "resources/textures/stats/items/defaultItem.png"
                        )),
                new AnimatedSpriteRenderer(player, "resources/textures/characters/MainCharacter.png", 32, 32),
                new PlayerController(player),
                new PlayerShootingController(player, 0.2f, WeaponType.PhysicsBook),
                new BoxCollider(player, new Vec2(1.2f, 2), true, true)
        );
    }
    public static GameObject createRoomGameObject(){
        GameObject room = createGameObject("Room");

        return createGameObject(
                room,
                new AnimatedSpriteRenderer(room, "resources/textures/map/empty_map.png", 256, 256),
                new BoxCollider(room, new Vec2(Room.SIZE - 2.4f, Room.SIZE - 2), false, false)
        );
    }
    public static GameObject createDoorGameObject(int direction, Collider roomCollider){
        GameObject door = createGameObject("Door");
        DoorCollider doorCollider = new DoorCollider(door, new Vec2(2, 2), false, true, direction);
        AnimatedSpriteRenderer renderer = new AnimatedSpriteRenderer(
                door,
                "resources/textures/map/open_door.png",
                25,
                25
        );
        renderer.rotate(direction * 90);


        doorCollider.getIgnoreColliders().add(roomCollider);
        roomCollider.getIgnoreColliders().add(doorCollider);
        return createGameObject(door, doorCollider, renderer);
    }
    public static GameObject[] createEnemies(int amount){
        GameObject[] enemies = new GameObject[amount];
        Random rand = new Random();

        for (int i = 0; i < amount; i++) {
            GameObject enemy = createGameObject("Enemy");
            createGameObject(
                    enemy,
                    new AnimatedSpriteRenderer(enemy, "resources/textures/characters/Integral.png", 16, 32),
                    new BoxCollider(enemy, new Vec2(1f, 1.5f), true, true),
                    new EntityStats(enemy, 50, 5, 2, 10)
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

    public static GameObject createProjectile(WeaponData weaponData, Vec2 direction, GameObject parent){
        GameObject projectile = createGameObject("Projectile");
        return createGameObject(
                projectile,
                new ProjectileCollider(
                    projectile,
                    new Vec2(.2f, .2f),
                    true,
                    true,
                    parent
                ),
                new Projectile(projectile, weaponData.damage, direction.multiply(weaponData.speed)),
                new AnimatedSpriteRenderer(projectile, weaponData.imagePath, weaponData.imageWidth, weaponData.imageHeight)
        );
    }
    public static GameObject createPhysicalItem(Vec2 size, Item item){
        GameObject physicalItem = createGameObject(item.getName());
        return createGameObject(
                physicalItem,
                new ItemController(physicalItem, item),
                new ItemCollider(physicalItem, size, false, true),
                new AnimatedSpriteRenderer(physicalItem, item.getPhysicalPath(), 16, 16)
        );
    }
}
