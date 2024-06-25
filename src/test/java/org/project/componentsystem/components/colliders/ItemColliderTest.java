package org.project.componentsystem.components.colliders;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.ItemController;
import org.project.componentsystem.components.stats.PlayerStats;
import org.project.core.Game;
import org.project.core.Physics;
import org.project.generation.Level;
import org.project.items.Item;
import org.project.utils.Vec2;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemColliderTest {

    private static MockedStatic<Game> game;

    @BeforeAll
    static void setup() {
        game = Mockito.mockStatic(Game.class);
        Level level = Mockito.mock(Level.class);

        game.when(Game::getCurrentLevel).thenReturn(level);
        Mockito.when(level.getPhysicsEngine()).thenReturn(Mockito.mock(Physics.class));
    }

    @AfterAll
    static void close() {
        game.close();
    }

    @Test
    void itemColliderInitializesCorrectly() {
        GameObject gameObject = Mockito.mock(GameObject.class);
        Vec2 size = new Vec2(10, 10);
        ItemCollider itemCollider = new ItemCollider(gameObject, size, true, true);

        assertEquals(size, itemCollider.getSize());
        assertTrue(itemCollider.isMovable());
        assertTrue(itemCollider.isInside());
    }

    @Test
    void itemColliderOnCollideWorksCorrectly() {
        GameObject gameObject1 = Mockito.mock(GameObject.class);
        GameObject gameObject2 = Mockito.mock(GameObject.class);
        ItemCollider itemCollider1 = new ItemCollider(gameObject1, new Vec2(10, 10), true, true);
        BoxCollider boxCollider2 = new BoxCollider(gameObject2, new Vec2(10, 10), true, true);
        PlayerStats playerStats = Mockito.mock(PlayerStats.class);
        ItemController itemController = Mockito.mock(ItemController.class);
        Item item = Mockito.mock(Item.class);

        Mockito.when(gameObject1.getPosition()).thenReturn(new Vec2(0, 0));
        Mockito.when(gameObject2.getPosition()).thenReturn(new Vec2(10, 10));
        Mockito.when(gameObject2.getName()).thenReturn("Player");
        Mockito.when(gameObject2.getComponent(PlayerStats.class)).thenReturn(playerStats);
        Mockito.when(gameObject1.getComponent(ItemController.class)).thenReturn(itemController);
        Mockito.when(itemController.getItem()).thenReturn(item);
        Mockito.when(playerStats.getInventory()).thenReturn(new HashMap<>());

        itemCollider1.onCollide(boxCollider2);

        assertEquals(1, playerStats.getInventory().get(item).intValue());
    }
}