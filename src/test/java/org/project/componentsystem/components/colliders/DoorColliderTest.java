package org.project.componentsystem.components.colliders;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.GameObjectFactory;
import org.project.core.Game;
import org.project.core.Physics;
import org.project.generation.Level;
import org.project.utils.Vec2;

import static org.junit.jupiter.api.Assertions.*;

class DoorColliderTest {

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
    void doorColliderInitializesCorrectly() {
        GameObject gameObject = Mockito.mock(GameObject.class);
        Vec2 size = new Vec2(10, 10);
        DoorCollider doorCollider = new DoorCollider(gameObject, size, true, true, 1);

        assertEquals(size, doorCollider.getSize());
        assertTrue(doorCollider.isMovable());
        assertTrue(doorCollider.isInside());
        assertEquals(1, doorCollider.getDirection());
    }

    @Test
    void doorColliderOnCollideWorksCorrectly() {
        GameObject gameObject1 = new GameObject("Door", new Vec2(0, 0));
        GameObject player = GameObjectFactory.createPlayer();
        player.setPosition(new Vec2(0, 0));
        DoorCollider doorCollider1 = new DoorCollider(gameObject1, new Vec2(10, 10), true, true, 1);

        doorCollider1.onCollide((Collider) player.getComponent(BoxCollider.class));

        assertEquals(new Vec2(0, 0), gameObject1.getPosition());
        assertNotEquals(new Vec2(0, 0), player.getPosition());
    }
}