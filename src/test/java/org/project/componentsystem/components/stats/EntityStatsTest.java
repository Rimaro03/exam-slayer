package org.project.componentsystem.components.stats;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.project.componentsystem.GameObject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityStatsTest {

    @Test
    void entityStatsInitializesCorrectly() {
        GameObject gameObject = Mockito.mock(GameObject.class);
        EntityStats entityStats = new EntityStats(gameObject, true, 100, 10, 50, 1.0f, 5.0f);

        assertEquals(100, entityStats.getHealth());
        assertEquals(10, entityStats.getSpeed());
        assertEquals(50, entityStats.getAttackDamage());
        assertEquals(1.0f, entityStats.getAttackSpeed());
        assertEquals(5.0f, entityStats.getAttackRange());
    }

    @Test
    void entityStatsUpdatesCorrectly() {
        GameObject gameObject = Mockito.mock(GameObject.class);
        EntityStats entityStats = new EntityStats(gameObject, true, 100, 10, 50, 1.0f, 5.0f);

        assertDoesNotThrow(entityStats::update);
    }

}