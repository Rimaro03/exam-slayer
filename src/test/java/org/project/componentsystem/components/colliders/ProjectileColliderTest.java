package org.project.componentsystem.components.colliders;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.Projectile;
import org.project.componentsystem.components.stats.Stats;
import org.project.core.Game;
import org.project.core.Physics;
import org.project.generation.Level;
import org.project.utils.Vec2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProjectileColliderTest {

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
    void projectileColliderInitializesCorrectly() {
        GameObject gameObject = Mockito.mock(GameObject.class);
        GameObject parent = Mockito.mock(GameObject.class);
        Vec2 size = new Vec2(10, 10);
        ProjectileCollider projectileCollider = new ProjectileCollider(gameObject, size, true, true, parent);

        assertEquals(size, projectileCollider.getSize());
        assertTrue(projectileCollider.isMovable());
        assertTrue(projectileCollider.isInside());
        assertEquals(gameObject, projectileCollider.getGameObject());
    }

    @Test
    void projectileColliderOnCollideWorksCorrectly() {
        GameObject gameObject1 = Mockito.mock(GameObject.class);
        GameObject gameObject2 = Mockito.mock(GameObject.class);
        GameObject parent = Mockito.mock(GameObject.class);
        ProjectileCollider projectileCollider1 = new ProjectileCollider(gameObject1, new Vec2(10, 10), true, true, parent);
        BoxCollider boxCollider2 = new BoxCollider(gameObject2, new Vec2(10, 10), true, true);
        Stats stats = Mockito.mock(Stats.class);
        Projectile projectile = Mockito.mock(Projectile.class);

        Mockito.when(gameObject1.getPosition()).thenReturn(new Vec2(0, 0));
        Mockito.when(gameObject2.getPosition()).thenReturn(new Vec2(10, 10));
        Mockito.when(gameObject1.getComponent(Projectile.class)).thenReturn(projectile);
        Mockito.when(gameObject2.getComponent(Stats.class)).thenReturn(stats);
        Mockito.when(projectile.getDamage()).thenReturn(10);

        projectileCollider1.onCollide(boxCollider2);

        Mockito.verify(stats, Mockito.times(1)).takeDamage(10);
    }
}