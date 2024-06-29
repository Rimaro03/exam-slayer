package org.project.generation;

import org.junit.jupiter.api.Test;
import org.project.componentsystem.GameObject;
import org.project.generation.wavecollapse.Direction;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    @Test
    void setAndGetAdjacentRoom() {
        Room room0 = new Room(0, 0);
        Room room1 = new Room(1, 0);

        room0.setAdjacentRoom(Direction.RIGHT, room1);
        room1.setAdjacentRoom(Direction.LEFT, room0);


        assertEquals(room1, room0.getAdjacentRoom(Direction.RIGHT));
        assertEquals(room0, room1.getAdjacentRoom(Direction.LEFT));
    }

    @Test
    void addAndGetGameObject() {
        Room room = new Room(0, 0);
        GameObject gameObject = new GameObject("test");


        room.addGameObject(gameObject);
        assertEquals(gameObject, room.getGameObject("test"));
    }

    @Test
    void getGameObjects() {
        Room room = new Room(0, 0);
        GameObject gameObject0 = new GameObject("test");
        GameObject gameObject1 = new GameObject("test");


        room.addGameObject(gameObject0);
        room.addGameObject(gameObject1);
        assertEquals(2, room.getGameObjects("test").length);
    }

    @Test
    void removeGameObject() {
        Room room = new Room(0, 0);
        GameObject gameObject = new GameObject("test");

        room.addGameObject(gameObject);
        room.removeGameObject(gameObject);

        assertNull(room.getGameObject("test"));
    }

    @Test
    void destroyGameObjects() {
        Room room = new Room(0, 0);
        GameObject gameObject = new GameObject("test");

        room.addGameObject(gameObject);

        room.destroyGameObjects();

        assertNull(room.getGameObject("test"));
    }

    @Test
    void setEnabled() {
        // NEED GAME RUNNING
    }

    @Test
    void init() {
        // NEED GAME RUNNING
    }

    @Test
    void updateGameObjects() {
        // NEED GAME RUNNING
    }
}