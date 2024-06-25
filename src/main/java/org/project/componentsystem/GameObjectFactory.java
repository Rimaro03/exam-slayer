package org.project.componentsystem;

import org.project.componentsystem.components.*;
import org.project.componentsystem.components.bosses.BossAI;
import org.project.componentsystem.components.bosses.BossKillCounter;
import org.project.componentsystem.components.bosses.BossesInfo;
import org.project.componentsystem.components.colliders.*;
import org.project.componentsystem.components.enemies.EnemyAI;
import org.project.componentsystem.components.enemies.EnemyInfo;
import org.project.componentsystem.components.enemies.RoomLocker;
import org.project.componentsystem.components.menus.PauseMenu;
import org.project.componentsystem.components.menus.GameOverMenu;
import org.project.componentsystem.components.menus.MainMenu;
import org.project.componentsystem.components.stats.BossStats;
import org.project.componentsystem.components.stats.EntityStats;
import org.project.componentsystem.components.stats.PlayerStats;
import org.project.componentsystem.components.weapons.PlayerShootingController;
import org.project.componentsystem.components.weapons.WeaponInfo;
import org.project.componentsystem.components.weapons.WeaponType;
import org.project.generation.Room;
import org.project.items.Book;
import org.project.items.Item;
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

    public static GameObject createPlayer() {
        GameObject player = createGameObject("Player");

        return createGameObject(
                player,
                new PlayerStats(player, 100, 15)
                        .addItem(new Book(
                                "DefaultPhysicsBook",
                                1,
                                "resources/textures/touchable/book.png",
                                "resources/textures/stats/items/defaultItem.png"
                        )),
                new AnimatedSpriteRenderer(player, "resources/textures/characters/MainCharacter.png", 32, 32, 1),
                new PlayerController(player),
                new PlayerShootingController(player, WeaponType.PhysicsBook),
                new BoxCollider(player, new Vec2(1.2f, 2), true, true),
                new PauseMenu(player),
                new BossKillCounter(player)
        );
    }

    public static GameObject createRoomGameObject() {
        GameObject room = createGameObject("Room");

        return createGameObject(
                room,
                new AnimatedSpriteRenderer(room, "resources/textures/map/room.png", 256, 256, -1),
                new BoxCollider(room, new Vec2(Room.SIZE - 5.f, Room.SIZE - 5.f), false, false),
                new RoomLocker(room)
        );
    }

    public static GameObject createMainMenu(){
        GameObject mainMenu = createGameObject("MainMenu");

        return createGameObject(
                mainMenu,
                new MainMenu(mainMenu)
        );
    }

    public static GameObject createDoorGameObject(int direction, Collider roomCollider) {
        GameObject door = createGameObject("Door");
        DoorCollider doorCollider = new DoorCollider(door, new Vec2(2, 2), false, true, direction);
        AnimatedSpriteRenderer renderer = new AnimatedSpriteRenderer(
                door,
                "resources/textures/map/open_door.png",
                25,
                25,
                0
        );
        renderer.rotate(direction * 90);


        doorCollider.getIgnoreColliders().add(roomCollider);
        roomCollider.getIgnoreColliders().add(doorCollider);
        return createGameObject(door, doorCollider, renderer);
    }

    public static GameObject[] createEnemies(int amount) {
        GameObject[] enemies = new GameObject[amount];
        Random rand = new Random();

        for (int i = 0; i < amount; i++) {
            GameObject enemy = createGameObject("Enemy");
            int enemyId = EnemyInfo.getRandomEnemyId(rand);
            createGameObject(
                    enemy,
                    new AnimatedSpriteRenderer(enemy, EnemyInfo.getTexturePath(enemyId), EnemyInfo.getTextureWidth(enemyId), EnemyInfo.getTextureHeight(enemyId), 0),
                    new BoxCollider(enemy, new Vec2(1f, 1.5f), true, true),
                    new EntityStats(enemy, 50, 3, 5, 2.0f, 1.5f),
                    new EnemyAI(enemy)
            );
            enemy.setPosition(new Vec2((.5f - rand.nextFloat()) * (Room.SIZE - 2), (.5f - rand.nextFloat()) * (Room.SIZE - 2)));
            enemies[i] = enemy;
        }
        return enemies;
    }

    public static GameObject createBoss(int id) {
        System.out.println(id);
        // TODO : Implement boss creation
        GameObject boss = createGameObject(BossesInfo.getName(id));
        return createGameObject(boss,
                new BoxCollider(boss, new Vec2(4, 4), true, true),
                new AnimatedSpriteRenderer(boss, BossesInfo.getTexturePath(id), BossesInfo.BOSS_RESOLUTION, BossesInfo.BOSS_RESOLUTION, 0),
                new BossAI(boss),
                new BossStats(boss, BossesInfo.getHealth(id), BossesInfo.getSpeed(id), BossesInfo.getWeapon(id), BossesInfo.getAttackCooldown(id), BossesInfo.getMoveCooldown(id))
        );
    }

    public static GameObject createProjectile(WeaponInfo weaponInfo, Vec2 direction, GameObject parent) {
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
                new Projectile(projectile, weaponInfo.damage, direction.multiply(weaponInfo.speed)),
                new AnimatedSpriteRenderer(projectile, weaponInfo.imagePath, weaponInfo.imageWidth, weaponInfo.imageHeight, 0)
        );
    }

    public static GameObject createPhysicalItem(Vec2 size, Item item) {
        GameObject physicalItem = createGameObject(item.getName());
        return createGameObject(
                physicalItem,
                new ItemController(physicalItem, item),
                new ItemCollider(physicalItem, size, false, true),
                new AnimatedSpriteRenderer(physicalItem, item.getPhysicalPath(), 16, 16, 0)
        );
    }

    public static GameObject createGameOverMenu(){
        GameObject gameOverMenu = createGameObject("GameOverMenu");

        return createGameObject(
                gameOverMenu,
                new GameOverMenu(gameOverMenu)
        );
    }
}
