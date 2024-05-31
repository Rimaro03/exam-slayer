package org.project.componentsystem;

import org.junit.jupiter.api.Test;
import org.project.componentsystem.components.stats.PlayerStats;

class GameObjectFactoryTest {

    @Test
    void createGameObject() {
        GameObject test = GameObjectFactory.createGameObject("Test");
        assert test.getName().equals("Test");
    }

    @Test
    void createPlayer() {
        GameObject player = GameObjectFactory.createPlayer();
        assert player.getName().equals("Player");
        assert ((PlayerStats) player.getComponent(PlayerStats.class)).getHealth() == 100;
    }

}