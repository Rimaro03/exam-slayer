package org.project.generation;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.stats.EntityStats;
import org.project.core.Application;
import org.project.core.Game;
import org.project.core.Window;
import org.project.savingsystem.SavingIO;
import org.project.utils.Vec2;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {

    @Test
    void loadMapData() {

    }

    @Test
    void saveMapData() {
    }

    @Test
    void destroyAllGameObjects() {
        Room room = new Room(0, 0);
        Level level = new Level(room, null, 0);

        level.getCurrentRoom().addGameObject(new GameObject("Test"));
        level.destroyAllGameObjects();

        assertEquals(0, level.getCurrentRoom().getGameObjects("Test").length);
    }

    @Test
    void changeRoom() {
        // NEED GAME RUNNING
    }

    @Test
    void instantiateGameObject() {
        Room room = new Room(0, 0);
        Level level = new Level(room, null, 0);

        GameObject gameObject = new GameObject("Test");
        level.instantiateGameObject(gameObject, new Vec2(0, 0));

        assertEquals(gameObject, level.getCurrentRoom().getGameObject("Test"));
    }

    @Test
    void findGameObject() {
        Room room = new Room(0, 0);
        Level level = new Level(room, null, 0);

        GameObject gameObject = new GameObject("Test");
        level.getCurrentRoom().addGameObject(gameObject);

        assertEquals(gameObject, level.findGameObject("Test"));
    }

    @Test
    void findGameObjects() {
        Room room = new Room(0, 0);
        Level level = new Level(room, null, 0);

        GameObject gameObject = new GameObject("Test");
        level.getCurrentRoom().addGameObject(gameObject);

        assertEquals(gameObject, level.findGameObjects("Test")[0]);
    }

    @Test
    void destroyGameObject() {
        Room room = new Room(0, 0);
        Level level = new Level(room, null, 0);

        GameObject gameObject = new GameObject("Test");
        level.getCurrentRoom().addGameObject(gameObject);

        level.destroyGameObject(gameObject);

        assertEquals(0, level.getCurrentRoom().getGameObjects("Test").length);
    }

    @Test
    void addComponentToGameObject() {
        Room room = new Room(0, 0);
        Level level = new Level(room, null, 0);

        GameObject gameObject = new GameObject("Test");
        level.getCurrentRoom().addGameObject(gameObject);

        level.addComponentToGameObject(gameObject, new EntityStats(gameObject, 0, 0, 0, 0, 0));

        assertEquals(1, gameObject.getComponents().size());
        assertNotNull(gameObject.getComponent(EntityStats.class));
    }

    @Test
    void init() {
        Room room = new Room(0, 0);
        Level level = new Level(room, null, 0);
        room.setInitType(Room.InitType.Item);

        MockedStatic<Game> game = Mockito.mockStatic(Game.class);
        Window window = Mockito.mock(Window.class);
        SavingIO savingIO = Mockito.mock(SavingIO.class);
        MockedStatic<Application> application = Mockito.mockStatic(Application.class);

        game.when(Game::getCurrentLevel).thenReturn(level);
        game.when(Game::getSavingIO).thenReturn(savingIO);
        application.when(Application::getWindow).thenReturn(window);


        level.init();
        game.close();
        application.close();

        assertTrue(level.getCurrentRoom().isInitialized());
    }

    @Test
    void update() {
        // NEED GAME RUNNING
    }

    @Test
    void popBossId() {
        Level level = new Level(null, null, 0);

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(level.popBossId());
        ids.add(level.popBossId());
        ids.add(level.popBossId());

        assertTrue(ids.contains(0));
        assertTrue(ids.contains(1));
        assertTrue(ids.contains(2));
        assertTrue(level.getBossesIdsQueue().isEmpty());
    }

    @Test
    void popItem() {
        Level level = new Level(null, null, 0);

        assertNotNull(level.popItem());
    }

    @Test
    void getItemByName() {
        Level level = new Level(null, null, 0);

        assertNotNull(level.getItemByName("Pencil"));
    }

    @Test
    void removeItem() {
        Level level = new Level(null, null, 0);

        assertNotNull(level.removeItem(level.getItemByName("Pencil")));
    }
}