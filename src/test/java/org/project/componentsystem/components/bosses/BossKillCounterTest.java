package org.project.componentsystem.components.bosses;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.menus.GameOverMenu;
import org.project.core.Game;
import org.project.generation.Level;
import org.project.generation.wavecollapse.GenerationSettings;
import org.project.savingsystem.SavingIO;


class BossKillCounterTest {

    private MockedStatic<Game> game;
    private BossKillCounter bossKillCounter;
    private Level level;

    @BeforeEach
    void setup() {
        game = Mockito.mockStatic(Game.class);
        level = Mockito.mock(Level.class);
        GameObject gameObject = Mockito.mock(GameObject.class);
        game.when(Game::getCurrentLevel).thenReturn(level);
        bossKillCounter = new BossKillCounter(gameObject, true);
    }

    @AfterEach
    void close() {
        game.close();
    }

    @Test
    void bossKillCounterInitializesCorrectly() {
        Assertions.assertTrue(bossKillCounter.isEnabled());
    }

    @Test
    void bossKillCounterAddsKillCorrectly() {
        GameOverMenu gameOverMenu = Mockito.mock(GameOverMenu.class);
        GameObject gameObject = Mockito.mock(GameObject.class);

        Mockito.when(level.findGameObject("GameOverMenu")).thenReturn(gameObject);
        Mockito.when(gameObject.getComponent(GameOverMenu.class)).thenReturn(gameOverMenu);
        Mockito.when(level.findGameObject("Player")).thenReturn(gameObject);
        for (int i = 0; i < GenerationSettings.BOSS_ROOM_COUNT; i++) {
            bossKillCounter.addKill();
        }

        Mockito.verify(gameOverMenu, Mockito.times(1)).enable(true);
    }

    @Test
    void bossKillCounterStartsCorrectly() {
        SavingIO savingIO = Mockito.mock(SavingIO.class);
        Mockito.when(Game.getSavingIO()).thenReturn(savingIO);
        Mockito.when(savingIO.getInt("BossKilled")).thenReturn(5);

        bossKillCounter.start();

        Assertions.assertEquals(5, bossKillCounter.bossKilled);
    }
}