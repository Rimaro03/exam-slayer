package org.project.items;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.stats.PlayerStats;
import org.project.componentsystem.components.weapons.PlayerShootingController;
import org.project.core.Game;
import org.project.generation.Level;

class BookTest {

    private Book book;
    private GameObject gameObject;
    private PlayerStats playerStats;
    private MockedStatic<Game> gameMockedStatic;

    @BeforeEach
    void setUp() {
        gameMockedStatic = Mockito.mockStatic(Game.class);
        book = new Book("Physics", 2, "physicalPath", "inventoryPath");
        gameObject = Mockito.mock(GameObject.class);
        playerStats = Mockito.mock(PlayerStats.class);
        Mockito.when(gameObject.getComponent(PlayerStats.class)).thenReturn(playerStats);

        gameMockedStatic.when(Game::getCurrentLevel).thenReturn(Mockito.mock(Level.class));
    }

    @AfterEach
    void tearDown() {
        gameMockedStatic.close();
    }

    @Test
    void useDoesNothing() {
        book.use();
        Mockito.verifyNoInteractions(gameObject, playerStats);
    }

    @Test
    void updateDoesNothing() {
        book.update();
        Mockito.verifyNoInteractions(gameObject, playerStats);
    }

    @Test
    void onPickUpDecreasesPlayerSpeedByWeight() {
        Mockito.when(playerStats.getSpeed()).thenReturn(10.f);
        book.onPickUp(gameObject);
        Mockito.verify(playerStats).setSpeed(8);
    }

    @Test
    void onPickUpAddsPlayerShootingController() {
        book.onPickUp(gameObject);
        Mockito.verify(Game.getCurrentLevel())
                .addComponentToGameObject(
                        Mockito.eq(gameObject),
                        Mockito.any(PlayerShootingController.class)
                );
    }
}