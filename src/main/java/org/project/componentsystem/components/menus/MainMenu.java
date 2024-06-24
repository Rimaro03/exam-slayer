package org.project.componentsystem.components.menus;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.Component;
import org.project.core.Game;
import org.project.core.Input;
import org.project.core.InputListener;
import org.project.core.rendering.Renderer;
import org.project.utils.Vec2;

import java.awt.*;
import java.util.List;

@Log4j2
@Getter
public class MainMenu extends Component implements InputListener {
    private static final Color SELECTED_COLOR = new Color(255, 255, 255);
    private static final Color UNSELECTED_COLOR = new Color(190, 190, 190);

    private boolean loadMenu;
    private boolean loadNewGame;
    private float remainingSleepTime;
    private int selectedMenu;
    private int selectedFile;
    private String saveFileName;
    private List<String> saveFiles;

    public MainMenu(GameObject gameObject, boolean enabled) {
        super(gameObject, enabled);
        loadMenu = false;
        loadNewGame = false;
        remainingSleepTime = 0;
        selectedMenu = 0;
        selectedFile = 0;
    }

    public MainMenu(GameObject gameObject) {
        this(gameObject, true);
    }

    @Override
    public void start() {
        Game.setPaused(true);
        Input.addInputListener(this);

        Game.getCurrentLevel().findGameObject("Player").setEnabled(false);
        saveFiles = Game.getSavingIO().allFiles();
    }

    @Override
    public void update() {
        Renderer.addRectToRenderQueue(getPosition(), new Vec2(100, 100), Color.black, 5, true);

        boolean updated;
        if(loadMenu)
            updated = loadMenuLogic();
        else if(loadNewGame)
            updated = loadNewGameLogic();
        else
            updated = mainMenuLogic();

        remainingSleepTime -= Game.getTime().deltaTime();
        if(updated){
            remainingSleepTime = 0.1f;
        }
    }

    private boolean mainMenuLogic() {
        boolean updated = false;
        if(Input.isKeyPressed(Input.KEY_UP) && remainingSleepTime <= 0){
            selectedMenu = Math.max(0, selectedMenu - 1);
            updated = true;
        }
        else if(Input.isKeyPressed(Input.KEY_DOWN) && remainingSleepTime <= 0){
            selectedMenu = Math.min(1, selectedMenu + 1);
            updated = true;
        }
        else if(Input.isKeyPressed(Input.KEY_ENTER) && remainingSleepTime <= 0){
            switch (selectedMenu){
                case 0:
                    loadMenu = true;
                    break;
                case 1:
                    loadNewGame = true;
                    saveFileName = "";
                    break;
            }
            updated = true;
        }

        Renderer.addTextToRenderQueue(new Vec2(-2, 1), "Load Game", selectedMenu == 0 ? SELECTED_COLOR : UNSELECTED_COLOR, 18, 15);
        Renderer.addTextToRenderQueue(new Vec2(-2, -1), "New Game", selectedMenu == 1 ? SELECTED_COLOR : UNSELECTED_COLOR, 18, 15);

        return updated;
    }
    private boolean loadMenuLogic() {
        boolean updated = false;
        selectedFile = Math.min(selectedFile, saveFiles.size() - 1);
        selectedFile = Math.max(selectedFile, 0);

        if(Input.isKeyPressed(Input.KEY_UP) && remainingSleepTime <= 0){
            selectedFile = Math.max(0, selectedFile - 1);
            updated = true;
        }
        else if(Input.isKeyPressed(Input.KEY_DOWN) && remainingSleepTime <= 0) {
            selectedFile = Math.min(saveFiles.size() - 1, selectedFile + 1);
            updated = true;
        }

        if(Input.isKeyPressed(Input.KEY_ENTER) && remainingSleepTime <= 0){
            String loadFileName = saveFiles.get(selectedFile);
            updated = true;
            Game.setPaused(false);
            log.info("Loading game from: {}", loadFileName);
            Game.load(loadFileName);
        }

        if(Input.isKeyPressed(Input.KEY_BACK_SPACE) && remainingSleepTime <= 0 && !saveFiles.isEmpty()){
            String loadFileName = saveFiles.get(selectedFile);
            Game.getSavingIO().deleteFile(loadFileName);
            updated = true;
            log.info("Deleting game from: {}", loadFileName);
            saveFiles = Game.getSavingIO().allFiles();
        }

        for (int i = 0; i < saveFiles.size(); i++) {
            String saveFile = saveFiles.get(i);
            Renderer.addTextToRenderQueue(
                    new Vec2(-2, (saveFiles.size() / 2 - i) * 1.4f),
                    saveFile.substring(saveFile.lastIndexOf('/') + 1).replace(".esd", ""),
                    i == selectedFile ? SELECTED_COLOR : UNSELECTED_COLOR,
                    18,
                    15
            );
        }

        return updated;
    }
    private boolean loadNewGameLogic() {
        Renderer.addTextToRenderQueue(
                new Vec2(-2, 0),
                saveFileName,
                SELECTED_COLOR,
                18,
                15
        );
        Renderer.addTextToRenderQueue(
                new Vec2(-5, 2),
                "Enter the name of the save file",
                SELECTED_COLOR,
                18,
                15
        );
        if(Input.isKeyPressed(Input.KEY_ENTER) && remainingSleepTime <= 0 && !saveFileName.isEmpty()){
            Game.setPaused(false);
            Game.getSavingIO().setPath("saved/" + saveFileName + ".esd");
            log.info("Saving game to: {}.esd", saveFileName);

            Game.getCurrentLevel().destroyGameObject(this.getGameObject());
        }
        return false;
    }

    @Override
    public void destory() {
        Input.removeInputListener(this);
        Game.getCurrentLevel().findGameObject("Player").setEnabled(true);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onKeyPressed(int keyCode) {
        if(keyCode == Input.KEY_ESCAPE){
            loadMenu = false;
            loadNewGame = false;
            selectedMenu = 0;
            selectedFile = 0;
        }
    }

    @Override
    public void onKeyReleased(int keyCode) {

    }

    @Override
    public void onKeyTyped(char keyChar) {
        if(!loadNewGame)
            return;


        if (keyChar == '\b' && !saveFileName.isEmpty())
            saveFileName = saveFileName.substring(0, saveFileName.length() - 1);
        else if (keyChar != '\b' && keyChar != '\n')
            saveFileName += keyChar;
    }
}
