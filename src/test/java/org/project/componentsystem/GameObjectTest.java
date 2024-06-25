package org.project.componentsystem;

import org.junit.jupiter.api.Test;
import org.project.componentsystem.components.Component;
import org.project.componentsystem.components.stats.PlayerStats;
import org.project.utils.Vec2;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GameObjectTest {

    @Test
    void addComponentToGameObject() {
        GameObject gameObject = new GameObject("Test");
        Component component = new PlayerStats(gameObject, 100, 5);
        gameObject.addComponent(component);
        assertEquals(component, gameObject.getComponent(PlayerStats.class));
    }

    @Test
    void removeComponentFromGameObject() {
        GameObject gameObject = new GameObject("Test");
        Component component = new PlayerStats(gameObject, 100, 5);
        gameObject.addComponent(component);
        gameObject.removeComponent(component);
        assertNull(gameObject.getComponent(PlayerStats.class));
    }

    @Test
    void getComponentFromGameObject() {
        GameObject gameObject = new GameObject("Test");
        Component component = new PlayerStats(gameObject, 100, 5);
        gameObject.addComponent(component);
        assertEquals(component, gameObject.getComponent(PlayerStats.class));
    }

    @Test
    void setAndGetNameOfGameObject() {
        GameObject gameObject = new GameObject("Test");
        gameObject.setName("New Name");
        assertEquals("New Name", gameObject.getName());
    }

    @Test
    void setAndGetComponentsOfGameObject() {
        GameObject gameObject = new GameObject("Test");
        ArrayList<Component> components = new ArrayList<>();
        components.add(new PlayerStats(gameObject, 100, 5));
        gameObject.setComponents(components);
        assertEquals(components, gameObject.getComponents());
    }

    @Test
    void setAndGetPositionOfGameObject() {
        GameObject gameObject = new GameObject("Test");
        Vec2 newPosition = new Vec2(1, 1);
        gameObject.setPosition(newPosition);
        assertEquals(newPosition, gameObject.getPosition());
    }
}