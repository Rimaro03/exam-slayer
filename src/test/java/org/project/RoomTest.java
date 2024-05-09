package org.project;

import org.junit.jupiter.api.Test;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.GameObjectFactory;
import org.project.generation.Room;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RoomTest {

    @Test
    void addEntity() {
        GameObject player = GameObjectFactory.createPlayer("player");
        Room room = new Room(true, true, true, true);
        room.addEntity(player);
        assertEquals(1, room.getGameObjects().size());
    }

    @Test
    void removeEntity() {
        GameObject player = GameObjectFactory.createPlayer("player");
        Room room = new Room(true, true, true, true);
        room.addEntity(player);
        room.removeEntity(player);
        assertEquals(0, room.getGameObjects().size());
    }

    @Test
    void printEntities() {
        GameObject player = GameObjectFactory.createPlayer("player");
        GameObject player2 = GameObjectFactory.createPlayer("player2");
        Room room = new Room(true, true, true, true);
        room.addEntity(player);
        room.addEntity(player2);
        room.printEntities();
    }

    @Test
    void getEntities() {
        Room room = new Room(true, true, true, true);
        assertNotNull(room.getGameObjects());
    }
}