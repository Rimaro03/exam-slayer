package org.project.componentsystem;

import org.junit.jupiter.api.Test;
import org.project.componentsystem.components.PlayerStats;

class GameObjectFactoryTest {

    @Test
    void createGameObject() {
        GameObject test = GameObjectFactory.createGameObject("Test");
        assert test.getName().equals("Test");
    }

    @Test
    void createPlayer() {
        GameObject player = GameObjectFactory.createPlayer("Player");
        assert player.getName().equals("Player");
        assert ((PlayerStats) player.getComponent(PlayerStats.class)).getStats().getHealth() == 100;
    }

}