package org.project.core;

import lombok.Getter;
import org.project.generation.Level;
import org.project.generation.LevelGenerator;

@Getter
public class Game {
    private static Game currentGame;
    private final Level currentLevel;

    private Game(){
        currentLevel = LevelGenerator.build(10);
    }
    public static Game loadNewGame(){
        // To-do : The game will ask the user to select : load a saved game or start a new game
        currentGame = new Game();
        return currentGame;
    }
    public static Level getCurrentLevel(){ return currentGame.currentLevel; }

    public void start(){
        currentLevel.init();
    }
    public void update(){
        currentLevel.update();
    }
}
