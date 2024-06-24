package org.project.componentsystem.components.weapons;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.project.componentsystem.GameObject;
import org.project.core.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerShootingControllerTest {

    @Test
    void playerShootingControllerShootsCorrectly() {
        MockedStatic<Game> mockedGame = Mockito.mockStatic(Game.class);
        MockedStatic<Time> mockedTime = Mockito.mockStatic(Time.class);
        MockedStatic<Application> mockedApplication = Mockito.mockStatic(Application.class);

        GameObject gameObject = Mockito.mock(GameObject.class);
        PlayerShootingController controller = new PlayerShootingController(gameObject, WeaponType.Sword);
        Time time = Mockito.mock(Time.class);

        mockedApplication.when(Application::getWindow).thenReturn(Mockito.mock(Window.class));
        Mockito.when(Game.getTime()).thenReturn(time);
        Mockito.when(time.deltaTime()).thenReturn(0.1f);

        controller.update();


        assertTrue(controller.isEnabled());

        mockedApplication.close();
        mockedGame.close();
        mockedTime.close();
    }

    @Test
    void playerShootingControllerPausesAndResumesCorrectly() {
        GameObject gameObject = Mockito.mock(GameObject.class);
        PlayerShootingController controller = new PlayerShootingController(gameObject, WeaponType.Sword);

        assertTrue(controller.isEnabled());
        controller.onGamePaused();
        assertFalse(controller.isEnabled());
        controller.onGameResumed();
        assertTrue(controller.isEnabled());
    }
}