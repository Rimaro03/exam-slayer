package org.project.componentsystem.components.stats;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.project.componentsystem.GameObject;
import org.project.items.Item;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStatsTest {

    @Test
    void playerStatsAddsItemCorrectly() {
        GameObject gameObject = Mockito.mock(GameObject.class);
        PlayerStats playerStats = new PlayerStats(gameObject, true, 100, 10);
        Item item = Mockito.mock(Item.class);

        playerStats.addItem(item);

        assertTrue(playerStats.getInventory().containsKey(item));
        assertEquals(1, playerStats.getInventory().get(item));
    }

    @Test
    void playerStatsAddsMultipleItemsCorrectly() {
        GameObject gameObject = Mockito.mock(GameObject.class);
        PlayerStats playerStats = new PlayerStats(gameObject, true, 100, 10);
        Item item = Mockito.mock(Item.class);

        playerStats.addItem(item);
        playerStats.addItem(item);

        assertTrue(playerStats.getInventory().containsKey(item));
        assertEquals(2, playerStats.getInventory().get(item));
    }
}