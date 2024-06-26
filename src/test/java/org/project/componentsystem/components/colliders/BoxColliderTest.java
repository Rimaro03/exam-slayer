package org.project.componentsystem.components.colliders;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.project.componentsystem.GameObject;
import org.project.core.Game;
import org.project.core.Physics;
import org.project.generation.Level;
import org.project.utils.Vec2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoxColliderTest {

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
    void boxColliderInitializesCorrectly() {
        GameObject gameObject = Mockito.mock(GameObject.class);
        Vec2 size = new Vec2(10, 10);
        BoxCollider boxCollider = new BoxCollider(gameObject, size, true, true);

        assertEquals(size, boxCollider.getSize());
        assertTrue(boxCollider.isMovable());
        assertTrue(boxCollider.isInside());
    }

    @Test
    void boxColliderRepulsionWorksCorrectly() {
        GameObject gameObject1 = new GameObject("Test", new Vec2(10, 10));
        GameObject gameObject2 = new GameObject("Test", new Vec2(10, 10));
        BoxCollider boxCollider1 = new BoxCollider(gameObject1, new Vec2(10, 10), true, true);
        BoxCollider boxCollider2 = new BoxCollider(gameObject2, new Vec2(10, 10), true, true);

        BoxCollider.repulsion(boxCollider1, boxCollider2, 0.1f, 0.1f, 1f);

        assertEquals(new Vec2(10.1f, 10.1f), gameObject1.getPosition());
        assertEquals(new Vec2(9.9f, 9.9f), gameObject2.getPosition());
    }

    @Test
    void boxColliderOnCollideWorksCorrectly() {
        GameObject gameObject1 = Mockito.mock(GameObject.class);
        GameObject gameObject2 = Mockito.mock(GameObject.class);
        BoxCollider boxCollider1 = new BoxCollider(gameObject1, new Vec2(10, 10), true, true);
        BoxCollider boxCollider2 = new BoxCollider(gameObject2, new Vec2(10, 10), true, true);

        Mockito.when(gameObject1.getPosition()).thenReturn(new Vec2(0, 0));
        Mockito.when(gameObject2.getPosition()).thenReturn(new Vec2(10, 10));

        boxCollider1.onCollide(boxCollider2);

        assertEquals(new Vec2(0, 0), gameObject1.getPosition());
        assertEquals(new Vec2(10, 10), gameObject2.getPosition());
    }
}