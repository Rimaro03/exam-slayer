package org.project.componentsystem.components.enemies;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.stats.EntityStats;
import org.project.core.Game;
import org.project.core.Time;
import org.project.generation.Level;
import org.project.utils.Vec2;

import static org.junit.jupiter.api.Assertions.*;

class EnemyAITest {

    @Test
    void enemyAIUpdatesPositionCorrectly() {
        MockedStatic<Game> mockedGame = Mockito.mockStatic(Game.class);
        MockedStatic<Time> mockedTime = Mockito.mockStatic(Time.class);
        Level level = Mockito.mock(Level.class);
        GameObject gameObject = Mockito.mock(GameObject.class);
        GameObject target = Mockito.mock(GameObject.class);
        EnemyAI enemyAI = new EnemyAI(gameObject);
        EntityStats stats = Mockito.mock(EntityStats.class);
        RoomLocker roomLocker = Mockito.mock(RoomLocker.class);
        GameObject room = Mockito.mock(GameObject.class);
        Time time = Mockito.mock(Time.class);

        Mockito.when(gameObject.getComponent(EntityStats.class)).thenReturn(stats);
        Mockito.when(Game.getCurrentLevel()).thenReturn(level);
        Mockito.when(Game.getCurrentLevel().findGameObject("Player")).thenReturn(target);
        Mockito.when(Game.getTime()).thenReturn(time);
        Mockito.when(time.deltaTime()).thenReturn(0.1f);
        Mockito.when(stats.getSpeed()).thenReturn(10.f);
        Mockito.when(target.getPosition()).thenReturn(new Vec2(10, 10));
        Mockito.when(gameObject.getPosition()).thenReturn(new Vec2(0, 0));

        Mockito.when(level.findGameObject("Room")).thenReturn(room);
        Mockito.when(room.getComponent(RoomLocker.class)).thenReturn(roomLocker);

        enemyAI.start();
        enemyAI.update();

        assertEquals(new Vec2(0, 0), gameObject.getPosition());

        mockedGame.close();
        mockedTime.close();
    }

    @Test
    void enemyAIPausesAndResumesCorrectly() {
        GameObject gameObject = Mockito.mock(GameObject.class);
        EnemyAI enemyAI = new EnemyAI(gameObject);

        assertTrue(enemyAI.isEnabled());
        enemyAI.onGamePaused();
        assertFalse(enemyAI.isEnabled());
        enemyAI.onGameResumed();
        assertTrue(enemyAI.isEnabled());
    }
}