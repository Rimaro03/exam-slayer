package org.project.componentsystem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.project.componentsystem.components.AnimatedSpriteRenderer;
import org.project.componentsystem.components.ItemController;
import org.project.componentsystem.components.Projectile;
import org.project.componentsystem.components.colliders.BoxCollider;
import org.project.componentsystem.components.colliders.DoorCollider;
import org.project.componentsystem.components.colliders.ItemCollider;
import org.project.componentsystem.components.colliders.ProjectileCollider;
import org.project.componentsystem.components.enemies.RoomLocker;
import org.project.componentsystem.components.weapons.WeaponInfo;
import org.project.core.Game;
import org.project.core.Physics;
import org.project.generation.Level;
import org.project.items.Book;
import org.project.items.Item;
import org.project.utils.Vec2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class GameObjectFactoryTest {

    private static MockedStatic<Game> mockedGame;
    @BeforeAll
    static void setUp() {
        mockedGame = Mockito.mockStatic(Game.class);
        Level level = Mockito.mock(Level.class);
        Physics physics = Mockito.mock(Physics.class);

        Mockito.when(level.getPhysicsEngine()).thenReturn(physics);
        mockedGame.when(Game::getCurrentLevel).thenReturn(level);
    }

    @AfterAll
    static void tearDown() {
        mockedGame.close();
    }

    @Test
    void createRoomGameObject_hasExpectedComponents() {
        GameObject room = GameObjectFactory.createRoomGameObject();
        assertInstanceOf(AnimatedSpriteRenderer.class, room.getComponent(AnimatedSpriteRenderer.class));
        assertInstanceOf(BoxCollider.class, room.getComponent(BoxCollider.class));
        assertInstanceOf(RoomLocker.class, room.getComponent(RoomLocker.class));

    }

    @Test
    void createDoorGameObject_hasExpectedComponents() {
        GameObject door = GameObjectFactory.createDoorGameObject(0, new BoxCollider(new GameObject("Test"), new Vec2(1, 1), false, false));
        assertInstanceOf(DoorCollider.class, door.getComponent(DoorCollider.class));
        assertInstanceOf(AnimatedSpriteRenderer.class, door.getComponent(AnimatedSpriteRenderer.class));
    }

    @Test
    void createEnemies_createsExpectedAmount() {
        GameObject[] enemies = GameObjectFactory.createEnemies(5);
        assertEquals(5, enemies.length);
    }

    @Test
    void createBoss_hasExpectedComponents() {
        GameObject boss = GameObjectFactory.createBoss(1);
        assertInstanceOf(BoxCollider.class, boss.getComponent(BoxCollider.class));
        assertInstanceOf(AnimatedSpriteRenderer.class, boss.getComponent(AnimatedSpriteRenderer.class));
    }

    @Test
    void createProjectile_hasExpectedComponents() {
        WeaponInfo weaponInfo = new WeaponInfo(
                1,
                1,
                1,
                "resources/textures/touchable/book.png",
                16,
                16
        );
        GameObject projectile = GameObjectFactory.createProjectile(weaponInfo, new Vec2(1, 1), new GameObject("Test"));
        assertInstanceOf(ProjectileCollider.class, projectile.getComponent(ProjectileCollider.class));
        assertInstanceOf(Projectile.class, projectile.getComponent(Projectile.class));
        assertInstanceOf(AnimatedSpriteRenderer.class, projectile.getComponent(AnimatedSpriteRenderer.class));
    }

    @Test
    void createPhysicalItem_hasExpectedComponents() {

        Item item = new Book("Test", 1, "resources/textures/touchable/book.png", "resources/textures/touchable/book.png");
        GameObject physicalItem = GameObjectFactory.createPhysicalItem(new Vec2(1, 1), item);
        assertInstanceOf(ItemController.class, physicalItem.getComponent(ItemController.class));
        assertInstanceOf(ItemCollider.class, physicalItem.getComponent(ItemCollider.class));
        assertInstanceOf(AnimatedSpriteRenderer.class, physicalItem.getComponent(AnimatedSpriteRenderer.class));
    }
}