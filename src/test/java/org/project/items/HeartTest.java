package org.project.items;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.stats.PlayerStats;
import org.project.core.Game;

class HeartTest {

    private Heart heart;
    private GameObject gameObject;
    private PlayerStats playerStats;
    private MockedStatic<Game> gameMockedStatic;

    @BeforeEach
    void setUp() {
        gameMockedStatic = Mockito.mockStatic(Game.class);
        heart = new Heart("Health", 1, "physicalPath", "inventoryPath", 20);
        gameObject = Mockito.mock(GameObject.class);
        playerStats = Mockito.mock(PlayerStats.class);
        Mockito.when(gameObject.getComponent(PlayerStats.class)).thenReturn(playerStats);
    }

    @AfterEach
    void tearDown() {
        gameMockedStatic.close();
    }

    @Test
    void useDoesNothing() {
        heart.use();
        Mockito.verifyNoInteractions(gameObject, playerStats);
    }

    @Test
    void updateDoesNothing() {
        heart.update();
        Mockito.verifyNoInteractions(gameObject, playerStats);
    }

    @Test
    void onPickUpDecreasesPlayerSpeedByWeight() {
        Mockito.when(playerStats.getSpeed()).thenReturn(10.f);
        heart.onPickUp(gameObject);
        Mockito.verify(playerStats).setSpeed(9);
    }

    @Test
    void onPickUpIncreasesPlayerHealthByHeartHealth() {
        Mockito.when(playerStats.getHealth()).thenReturn(80);
        heart.onPickUp(gameObject);
        Mockito.verify(playerStats).setHealth(100);
    }
}