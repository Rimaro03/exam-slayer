package org.project.core;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.GameObjectFactory;
import org.project.core.rendering.Renderer;
import org.project.generation.Level;
import org.project.generation.wavecollapse.LevelGenerator;
import org.project.savingsystem.SavingIO;
import org.project.utils.Vec2;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Random;


@Log4j2
@Getter
public class Game implements WindowListener {
    private static Game currentGame;
    private Level currentLevel;
    private SavingIO savingIO;
    private final Time time;

    private boolean paused;
    private final ArrayList<GameStateListener> gameStateListeners;

    private Game() {
        savingIO = new SavingIO();

        gameStateListeners = new ArrayList<>();
        paused = false;

        time = new Time();

        currentLevel = new LevelGenerator(new Random().nextLong()).build();


        /* -------------------- TMP ------------------------*/


    }

    public static Game loadNewGame() {
        currentGame = new Game();
        return currentGame;
    }

    public static void load(String saveFilePath){
        if(currentGame.currentLevel != null)
            currentGame.currentLevel.destroyAllGameObjects();

        currentGame.savingIO.setPath(saveFilePath);

        Long seed = currentGame.savingIO.getLong("LevelSeed");
        if(seed == null)
            seed = new Random().nextLong();

        Renderer.clearRenderQueue();

        currentGame.currentLevel = new LevelGenerator(seed).build();
        currentGame.currentLevel.loadMapData();
        currentGame.currentLevel.init();
    }

    public static Time getTime() {
        return currentGame.time;
    }
    public static SavingIO getSavingIO() {
        return currentGame.savingIO;
    }

    @SneakyThrows
    public static Level getCurrentLevel() {
        return currentGame.currentLevel;
    }



    /* ---------------------- GAME LISTENER METHODS --------------------------*/

    private void updateListeners(){
        if(paused)
            for (GameStateListener listener : gameStateListeners)
                listener.onGamePaused();
        else
            for (GameStateListener listener : gameStateListeners)
                listener.onGameResumed();

    }
    public static boolean isPaused(){ return currentGame.paused; }
    public static void setPaused(boolean paused) {
        if(currentGame.paused == paused)
            return;

        currentGame.paused = paused;
        currentGame.updateListeners();

    }
    public static void addGameStateListener(GameStateListener listener) {
        currentGame.gameStateListeners.add(listener);
    }
    public static void removeGameStateListener(GameStateListener listener) {
        currentGame.gameStateListeners.remove(listener);
    }


    /* ---------------------- GAME METHODS --------------------------*/

    public void start() {
        currentLevel.init();
        currentLevel.instantiateGameObject(GameObjectFactory.createMainMenu(), new Vec2(0, 0));
    }

    public void update() {
        currentLevel.update();
    }

    public static void exit() {
        save();
    }
    public static void save(){
        currentGame.currentLevel.saveMapData();
        currentGame.currentLevel.destroyAllGameObjects();

        currentGame.savingIO.flush();
    }

    /* ---------------------- WINDOW LISTENER METHODS ---------------------- */

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        exit();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
