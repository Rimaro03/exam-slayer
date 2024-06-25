package org.project.componentsystem.components.enemies;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.colliders.DoorCollider;
import org.project.core.Game;
import org.project.generation.Level;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoomLockerTest {
    private static MockedStatic<Game> game;
    private static GameObject gameObject;
    private static DoorCollider doorCollider;

    @BeforeAll
    static void setup() {
        game = Mockito.mockStatic(Game.class);
        gameObject = Mockito.mock(GameObject.class);
        doorCollider = Mockito.mock(DoorCollider.class);

    }

    @AfterAll
    static void close() {
        game.close();
    }

    @Test
    void roomLockerLocksAndUnlocksCorrectly() {
        RoomLocker roomLocker = new RoomLocker(gameObject);
        GameObject door = Mockito.mock(GameObject.class);
        Level level = Mockito.mock(Level.class);
        game.when(Game::getCurrentLevel).thenReturn(level);

        Mockito.when(door.getComponent(DoorCollider.class)).thenReturn(doorCollider);
        Mockito.when(level.findGameObjects("Door")).thenReturn(new GameObject[]{door});

        roomLocker.start();
        assertFalse(doorCollider.isEnabled());

        roomLocker.update();
        assertFalse(roomLocker.isEnabled());
    }

    @Test
    void roomLockerDoesNotUnlockIfEntitiesExist() {
        GameObject gameObject = Mockito.mock(GameObject.class);
        RoomLocker roomLocker = new RoomLocker(gameObject);
        GameObject door = Mockito.mock(GameObject.class);
        DoorCollider doorCollider = Mockito.mock(DoorCollider.class);
        Level level = Mockito.mock(Level.class);

        game.when(Game::getCurrentLevel).thenReturn(level);
        Mockito.when(door.getComponent(DoorCollider.class)).thenReturn(doorCollider);
        Mockito.when(level.findGameObjects("Door")).thenReturn(new GameObject[]{door});
        Mockito.when(door.getComponent(DoorCollider.class)).thenReturn(doorCollider);

        roomLocker.addEntity(new Object());
        roomLocker.start();
        assertFalse(doorCollider.isEnabled());

        roomLocker.update();
        assertFalse(doorCollider.isEnabled());
        assertTrue(roomLocker.isEnabled());
    }
}