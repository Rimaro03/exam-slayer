package org.project.componentsystem.components;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.GameObjectFactory;
import org.project.componentsystem.components.stats.PlayerStats;
import org.project.core.Game;
import org.project.core.Input;
import org.project.core.Physics;
import org.project.core.Time;
import org.project.generation.Level;
import org.project.utils.Vec2;

class PlayerControllerTest {

   @Test
    void playerControllerMovesCorrectly() {
        MockedStatic<Game> mockedGame = Mockito.mockStatic(Game.class);
        MockedStatic<Input> mockedInput = Mockito.mockStatic(Input.class);
        PlayerStats playerStats = Mockito.mock(PlayerStats.class);

        Level level = Mockito.mock(Level.class);
        Physics physics = Mockito.mock(Physics.class);
        Mockito.when(level.getPhysicsEngine()).thenReturn(physics);
        mockedGame.when(Game::getCurrentLevel).thenReturn(level);

        Time mockedTime = Mockito.mock(Time.class);
        GameObject player = GameObjectFactory.createPlayer();
        PlayerController playerController = (PlayerController) player.getComponent(PlayerController.class);

        Mockito.when(playerStats.getSpeed()).thenReturn(5f);
        Mockito.when(Input.isKeyPressed(Input.KEY_A)).thenReturn(true);
        Mockito.when(Game.getTime()).thenReturn(mockedTime);
        Mockito.when(mockedTime.deltaTime()).thenReturn(0.1f);
        playerController.start();
        playerController.update();
        assert player.getPosition().equals(new Vec2(-1.5f, 0));

        mockedGame.close();
        mockedInput.close();
    }

}