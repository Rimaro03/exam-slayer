package org.project.componentsystem.entites;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.project.componentsystem.components.PlayerStats;
import org.project.utils.Vec2;

import java.util.concurrent.atomic.AtomicInteger;

class PlayerTest {


    @Test
    public void addComponent() {
        Player player = new Player(new AtomicInteger(0), "player", 20);
        Assertions.assertNotNull(player.getComponent(PlayerStats.class));
    }

    @Test
    void removeComponent() {
        Player player = new Player(new AtomicInteger(0), "player", 20);
        player.removeComponent(player.getComponent(PlayerStats.class));
        Assertions.assertNull(player.getComponent(PlayerStats.class));
    }

    @Test
    void getComponent() {
        Player player = new Player(new AtomicInteger(0), "player", 20);
        PlayerStats stats = (PlayerStats) player.getComponent(PlayerStats.class);
        Assertions.assertNotNull(stats);
        assert stats.getHealth() == 20;
    }

    @Test
    void getPosition() {
        Player player = new Player(new AtomicInteger(0), "player", 20);
        Vec2 position = player.getPosition();
        Assertions.assertNotNull(position);
        assert position.getX() == 0;
    }

    @Test
    void setPosition() {
        Player player = new Player(new AtomicInteger(0), "player", 20);
        player.setPosition(new Vec2(10, 10));
        Vec2 position = player.getPosition();
        Assertions.assertNotNull(position);
        assert position.getX() == 10;
    }

    @Test
    void setHealth() {
        Player player = new Player(new AtomicInteger(0), "player", 20);
        player.setHealth(30);
        assert player.getHealth() == 30;
    }

    @Test
    void getHealth() {
        Player player = new Player(new AtomicInteger(0), "player", 20);
        assert player.getHealth() == 20;
    }

    @Test
    void setInventory() {
        Player player = new Player(new AtomicInteger(0), "player", 20);
        player.setInventory(null);
        Assertions.assertNull(player.getInventory());
    }

    @Test
    void getInventory() {
        Player player = new Player(new AtomicInteger(0), "player", 20);
        assert player.getInventory().isEmpty();
    }

    @Test
    void start() {
        Player player = new Player(new AtomicInteger(0), "player", 20);
        player.start();
    }

    @Test
    void update() {
        Player player = new Player(new AtomicInteger(0), "player", 20);
        player.update();
    }

    @Test
    void getGameObject() {
        Player player = new Player(new AtomicInteger(0), "player", 20);
        Assertions.assertNotNull(player.getGameObject());
    }

    @Test
    void getId() {
        Player player = new Player(new AtomicInteger(20), "player", 20);
        assert player.getId() == 20;
    }

    @Test
    void getName() {
        Player player = new Player(new AtomicInteger(0), "player", 20);
        assert player.getName().equals("player");
    }

    @Test
    void setGameObject() {
        Player player = new Player(new AtomicInteger(0), "player", 20);
        player.setGameObject(null);
        Assertions.assertNull(player.getGameObject());
    }

    @Test
    void setId() {
        Player player = new Player(new AtomicInteger(0), "player", 20);
        player.setId(10);
        assert player.getId() == 10;
    }

    @Test
    void setName() {
        Player player = new Player(new AtomicInteger(0), "player", 20);
        player.setName("newName");
        assert player.getName().equals("newName");
    }
}