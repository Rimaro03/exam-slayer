package org.project.componentsystem.components;

import org.project.componentsystem.GameObject;
import org.project.core.Game;
import org.project.core.GameStateListener;
import org.project.core.Input;
import org.project.core.rendering.Renderer;
import org.project.utils.Vec2;

import java.awt.*;

public class EscMenu extends Component implements GameStateListener {
    private static final Color SELECTED_COLOR = new Color(255, 255, 255);
    private static final Color UNSELECTED_COLOR = new Color(190, 190, 190);
    private static final String[] lines = { "Resume", "Save", "Save and exit" };
    private float remainingTimeToSleep;
    private boolean toggled;
    private int selectedLine = 0;

    public EscMenu(GameObject gameObject, boolean enabled) {
        super(gameObject, enabled);
    }

    public EscMenu(GameObject gameObject) {
        this(gameObject, true);
        toggled = false;
        remainingTimeToSleep = 0;
    }

    @Override
    public void start() {
        Game.addGameStateListener(this);
    }

    @Override
    public void update() {
        if(!toggled)
            return;

        boolean updated = false;

        if(Input.isKeyPressed(Input.KEY_UP) && remainingTimeToSleep <= 0){
            selectedLine = Math.max(0, selectedLine - 1);
            updated = true;
        }
        else if(Input.isKeyPressed(Input.KEY_DOWN) && remainingTimeToSleep <= 0) {
            selectedLine = Math.min(lines.length - 1, selectedLine + 1);
            updated = true;
        }

        Renderer.addRectToRenderQueue(new Vec2(-100, 100), new Vec2(400, 400), new Color(0, 0, 0, 0.5f), 2, true);
        for (int i = 0; i < lines.length; i++) {
            Renderer.addTextToRenderQueue(
                    new Vec2(-2, (lines.length / 2 - i) * 1.4f),
                    lines[i],
                    i == selectedLine ? SELECTED_COLOR : UNSELECTED_COLOR,
                    18,
                    3
            );
        }

        if(Input.isKeyPressed(Input.KEY_ENTER) && remainingTimeToSleep <= 0){
            switch (selectedLine){
                case 0:
                    Game.setPaused(false);
                    break;
                case 1:
                    Game.getSavingIO().flush();
                    break;
                case 2:
                    System.exit(0);
                    break;
            }
            updated = true;
        }


        if(updated)
            remainingTimeToSleep = 0.1f;
        remainingTimeToSleep -= Game.getTime().unscaledDeltaTime();
    }

    @Override
    public void destory() {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onGamePaused() {
        toggled = true;
    }

    @Override
    public void onGameResumed() {
        toggled = false;
    }
}
