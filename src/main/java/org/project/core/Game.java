package org.project.core;

import lombok.Getter;
import org.project.generation.Level;
import org.project.generation.wavecollapse.GenerationSettings;
import org.project.generation.wavecollapse.LevelGenerator;
import org.project.items.Item;
import org.project.items.Sword;

import java.util.ArrayList;
import java.util.Random;



@Getter
public class Game {
    private static Game currentGame;
    private final Level currentLevel;
    public static final ArrayList<Item> all_items = new ArrayList<>();
    private Game(){
        currentLevel = new LevelGenerator(8, new Random().nextLong()).build();
        // TODO : Load all items from a file (sword is a test item)
        // Just for testing
        for (int i = 0; i < GenerationSettings.ITEM_ROOM_COUNT; i++) {
            all_items.add(new Sword(
                    "Diamond Sword",
                    10,
                    "resources/textures/touchable/sword.png",
                    "resources/textures/stats/items/sword.png",
                    1
            ));
        }

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
