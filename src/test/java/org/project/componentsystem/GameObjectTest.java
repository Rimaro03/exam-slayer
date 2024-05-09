package org.project.componentsystem;

import org.junit.jupiter.api.Test;
import org.project.componentsystem.components.PlayerStats;
import org.project.utils.Vec2;

import static org.junit.jupiter.api.Assertions.*;

class GameObjectTest {

    @Test
    void addComponent() {
        GameObject gameObject = new GameObject("Test");
        gameObject.addComponent(new PlayerStats(gameObject,  100, 10, 5, 5));
        assertNotNull(gameObject.getComponent(PlayerStats.class));
    }

    @Test
    void removeComponent() {
        GameObject gameObject = new GameObject("Test");
        PlayerStats playerStats = new PlayerStats(gameObject,  100, 10, 5, 5);
        gameObject.addComponent(playerStats);
        gameObject.removeComponent(playerStats);
        assertNull(gameObject.getComponent(PlayerStats.class));
    }

    @Test
    void getComponent() {
        GameObject gameObject = new GameObject("Test");
        PlayerStats playerStats = new PlayerStats(gameObject,  100, 10, 5, 5);
        gameObject.addComponent(playerStats);
        assertEquals(playerStats, gameObject.getComponent(PlayerStats.class));
    }

    @Test
    void getName() {
        GameObject gameObject = new GameObject("Test");
        assertEquals("Test", gameObject.getName());
    }

    @Test
    void getComponents() {
        GameObject gameObject = new GameObject("Test");
        PlayerStats playerStats = new PlayerStats(gameObject, 100, 10, 5, 5);
        gameObject.addComponent(playerStats);
        assertEquals(1, gameObject.getComponents().size());
    }

    @Test
    void getPosition() {
        GameObject gameObject = new GameObject("Test");
        assertEquals(0, gameObject.getPosition().getX());
        assertEquals(0, gameObject.getPosition().getY());
    }

    @Test
    void setName() {
        GameObject gameObject = new GameObject("Test");
        gameObject.setName("New Name");
        assertEquals("New Name", gameObject.getName());
    }

    @Test
    void setComponents() {
        GameObject gameObject = new GameObject("Test");
        gameObject.setComponents(null);
        assertNull(gameObject.getComponents());
    }

    @Test
    void setPosition() {
        GameObject gameObject = new GameObject("Test");
        gameObject.setPosition(new Vec2(1, 1));
        assertEquals(1, gameObject.getPosition().getX());
        assertEquals(1, gameObject.getPosition().getY());
    }
}