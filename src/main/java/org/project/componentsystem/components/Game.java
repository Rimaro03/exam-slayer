package org.project.componentsystem.components;

import lombok.Getter;
import org.project.generation.Level;
import org.project.generation.LevelGenerator;

public class Game {
    @Getter
    private static Game current = null;
    @Getter
    private Level currentLevel;
    private Game() {
        currentLevel = null;
    }
    public static void startNewGame() {
        current = new Game();
        loadGame(current);
    }
    private static void loadGame(Game game) {
        game.currentLevel = LevelGenerator.build(10);
    }
}
