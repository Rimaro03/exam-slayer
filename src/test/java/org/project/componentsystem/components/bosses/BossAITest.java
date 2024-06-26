package org.project.componentsystem.components.bosses;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.enemies.RoomLocker;
import org.project.componentsystem.components.stats.BossStats;
import org.project.componentsystem.components.weapons.WeaponType;
import org.project.core.Game;
import org.project.core.Physics;
import org.project.core.Time;
import org.project.generation.Level;
import org.project.utils.Vec2;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BossAITest {

    private static MockedStatic<Game> game;
    private static GameObject gameObject;

    @BeforeAll
    static void setup() {
        game = Mockito.mockStatic(Game.class);
        Level level = Mockito.mock(Level.class);
        RoomLocker roomLocker = Mockito.mock(RoomLocker.class);
        gameObject = Mockito.mock(GameObject.class);

        game.when(Game::getCurrentLevel).thenReturn(level);
        game.when(Game::getTime).thenReturn(Mockito.mock(Time.class));
        Mockito.when(level.getPhysicsEngine()).thenReturn(Mockito.mock(Physics.class));
        Mockito.when(level.findGameObject(Mockito.anyString())).thenReturn(gameObject);
        Mockito.when(gameObject.getComponent(RoomLocker.class)).thenReturn(roomLocker);
    }

    @AfterAll
    static void close() {
        game.close();
    }

    @Test
    void bossAIInitializesCorrectly() {
        GameObject gameObject = Mockito.mock(GameObject.class);
        BossAI bossAI = new BossAI(gameObject, true);

        assertTrue(bossAI.isEnabled());
    }

    @Test
    void bossAIUpdatesCorrectly() {
        GameObject gameObject = Mockito.mock(GameObject.class);

        BossAI bossAI = new BossAI(gameObject, true);
        BossStats bossStats = new BossStats(gameObject, 1, 1f, WeaponType.PhysicsBook, 1f, 1f);

        Mockito.when(gameObject.getComponent(BossStats.class)).thenReturn(bossStats);
        Mockito.when(gameObject.getPosition()).thenReturn(new Vec2(0, 0));

        bossAI.start();
        bossAI.update();


        // If no exception is thrown, the test passes
    }

    @Test
    void bossAIDestroysCorrectly() {
        BossAI bossAI = new BossAI(gameObject, true);
        BossKillCounter bossKillCounter = new BossKillCounter(gameObject);

        Mockito.when(gameObject.getComponent(BossKillCounter.class)).thenReturn(bossKillCounter);

        bossAI.destory();

        assert bossKillCounter.bossKilled == 1;
    }
}