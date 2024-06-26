package org.project.componentsystem.components;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.project.componentsystem.GameObject;
import org.project.core.Game;
import org.project.core.Time;
import org.project.utils.Vec2;

import static org.junit.jupiter.api.Assertions.*;

class ProjectileTest {

    @Test
    void projectileMovesCorrectly() {
        MockedStatic<Game> mockedGame = Mockito.mockStatic(Game.class);
        MockedStatic<Time> mockedTime = Mockito.mockStatic(Time.class);
        GameObject gameObject = new GameObject("projectileTest");
        Vec2 velocity = new Vec2(1, 1);
        Projectile projectile = new Projectile(gameObject, 10, velocity);
        Time time = Mockito.mock(Time.class);

        Mockito.when(Game.getTime()).thenReturn(time);
        Mockito.when(time.deltaTime()).thenReturn(0.1f);

        Vec2 initialPosition = gameObject.getPosition();
        projectile.update();
        Vec2 expectedPosition = initialPosition.add(velocity.multiply(0.1f));

        assertEquals(expectedPosition, gameObject.getPosition());

        mockedGame.close();
        mockedTime.close();
    }

    @Test
    void projectilePausesAndResumesCorrectly() {
        GameObject gameObject = new GameObject("projectileTest");
        Vec2 velocity = new Vec2(1, 1);
        Projectile projectile = new Projectile(gameObject, 10, velocity);

        assertTrue(projectile.isEnabled());
        projectile.onGamePaused();
        assertFalse(projectile.isEnabled());
        projectile.onGameResumed();
        assertTrue(projectile.isEnabled());
    }
}