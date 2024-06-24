package org.project.componentsystem.components;

import org.project.componentsystem.GameObject;
import org.project.core.Game;
import org.project.core.GameStateListener;
import org.project.core.Input;
import org.project.core.InputListener;
import org.project.core.rendering.Renderer;
import org.project.savingsystem.SavingIO;
import org.project.utils.Vec2;

import java.awt.*;
import java.util.List;

public class EscMenu extends Component implements GameStateListener, InputListener {
    private static final Color SELECTED_COLOR = new Color(255, 255, 255);
    private static final Color UNSELECTED_COLOR = new Color(190, 190, 190);
    private static final String[] lines = { "Resume", "Load", "Save and exit" };
    private float remainingTimeToSleep;
    private String saveFileName;
    private List<String> saveFiles;

    private boolean pauseMenu;
    private boolean loadMenu;
    private boolean loadNewGame;


    private int selectedLine = 0;
    private int selectedFile = 0;

    public EscMenu(GameObject gameObject, boolean enabled) {
        super(gameObject, enabled);
    }

    public EscMenu(GameObject gameObject) {
        this(gameObject, true);
        pauseMenu = false;
        loadMenu = false;
        loadNewGame = false;
        remainingTimeToSleep = 0;
    }

    @Override
    public void start() {
        Game.addGameStateListener(this);
        Input.addInputListener(this);
        saveFiles = Game.getSavingIO().allFiles();
    }

    @Override
    public void update() {
        boolean updated = false;

        if(pauseMenu)
            updated = pauseMenuLogic();
        else if(loadMenu)
            updated = loadMenuLogic();
        else if(loadNewGame)
            updated = loadNewGameLogic();

        if(pauseMenu || loadMenu || loadNewGame)
            Renderer.addRectToRenderQueue(new Vec2(-100, 100), new Vec2(400, 400), new Color(0, 0, 0, 0.5f), 2, true);

        if(updated) {
            remainingTimeToSleep = 0.1f;
        }
        remainingTimeToSleep -= Game.getTime().deltaTime();
    }

    private boolean pauseMenuLogic(){
        boolean updated = false;

        if(Input.isKeyPressed(Input.KEY_UP) && remainingTimeToSleep <= 0){
            selectedLine = Math.max(0, selectedLine - 1);
            updated = true;
        }
        else if(Input.isKeyPressed(Input.KEY_DOWN) && remainingTimeToSleep <= 0) {
            selectedLine = Math.min(lines.length - 1, selectedLine + 1);
            updated = true;
        }

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
                    loadMenu = true;
                    pauseMenu = false;
                    break;
                case 2:
                    Game.exit();
                    System.exit(0);
                    break;
            }
            updated = true;
        }

        return updated;
    }

    private boolean loadMenuLogic(){
        boolean updated = false;

        for (int i = 0; i < saveFiles.size(); i++) {
            String saveFile = saveFiles.get(i);
            Renderer.addTextToRenderQueue(
                    new Vec2(-2, (saveFiles.size() / 2 - i) * 1.4f),
                    saveFile.substring(saveFile.lastIndexOf('/') + 1).replace(".esd", ""),
                    i == selectedFile ? SELECTED_COLOR : UNSELECTED_COLOR,
                    18,
                    3
            );
        }

        if(Input.isKeyPressed(Input.KEY_UP) && remainingTimeToSleep <= 0){
            selectedFile = Math.max(0, selectedFile - 1);
            updated = true;
        }
        else if(Input.isKeyPressed(Input.KEY_DOWN) && remainingTimeToSleep <= 0) {
            selectedFile = Math.min(saveFiles.size() - 1, selectedFile + 1);
            updated = true;
        }

        if(Input.isKeyPressed(Input.KEY_ENTER) && remainingTimeToSleep <= 0){
            String loadFileName = saveFiles.get(selectedFile);
            updated = true;

            Game.load(loadFileName);
            Game.setPaused(false);
        }


        return updated;
    }

    private boolean loadNewGameLogic(){
        Renderer.addTextToRenderQueue(
                new Vec2(-2, 0),
                saveFileName,
                SELECTED_COLOR,
                18,
                3
        );
        Renderer.addTextToRenderQueue(
                new Vec2(-5, 2),
                "Enter the name of the save file",
                SELECTED_COLOR,
                18,
                3
        );
        if(Input.isKeyPressed(Input.KEY_ENTER) && remainingTimeToSleep <= 0){
            Game.save();
            Game.setPaused(false);
            Game.load("saved/" + saveFileName + ".esd");
        }
        return false;
    }

    @Override
    public void destory() {
        Game.removeGameStateListener(this);
        Input.removeInputListener(this);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onGamePaused() {
        pauseMenu = true;
        loadMenu = false;
        loadNewGame = false;
    }

    @Override
    public void onGameResumed() {
        pauseMenu = false;
        loadMenu = false;
        loadNewGame = false;
    }

    @Override
    public void onKeyPressed(int keyCode) {
        if(keyCode == Input.KEY_ESCAPE){
            pauseMenu = false;
            loadMenu = false;
            loadNewGame = false;
            selectedLine = 0;
            selectedFile = 0;

            if(Game.isPaused())
                Game.setPaused(false);
            else
                Game.setPaused(true);
        }
    }

    @Override
    public void onKeyReleased(int keyCode) {

    }

    @Override
    public void onKeyTyped(char keyChar) {
        if(loadNewGame){
            if(keyChar == '\b' && !saveFileName.isEmpty()){
                saveFileName = saveFileName.substring(0, saveFileName.length() - 1);
            }
            else if(keyChar != '\b' && keyChar != '\n'){
                saveFileName += keyChar;
            }
        }
    }
}
