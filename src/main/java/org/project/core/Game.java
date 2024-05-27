package org.project.core;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.project.generation.Level;
import org.project.generation.wavecollapse.GenerationSettings;
import org.project.generation.wavecollapse.LevelGenerator;
import org.project.items.Book;
import org.project.items.Heart;
import org.project.items.Item;
import org.project.items.Sword;
import org.project.savingsystem.SavingIO;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Random;



@Log4j2
@Getter
public class Game implements WindowListener {
    private static Game currentGame;
    private final Level currentLevel;
    private final SavingIO savingIO;

    public static final ArrayList<Item> all_items = new ArrayList<>();

    private Game(String saveFilePath){
        savingIO = new SavingIO(saveFilePath);

        Long levelSeed = savingIO.getLong("LevelSeed");
        if(levelSeed == null)
            levelSeed = new Random().nextLong();

        currentLevel = new LevelGenerator(levelSeed).build();


        /* -------------------- TMP ------------------------*/

        // TODO : Load all items from a file (sword is a test item)
        // Loop is just for testing
        Sword sword = new Sword(
                "Diamond Sword",
                2,
                "resources/textures/touchable/sword.png",
                "resources/textures/stats/items/sword.png"
        );
        Heart heart = new Heart(
                "Heart",
                0,
                "resources/textures/touchable/heart.png",
                "resources/textures/stats/items/heart.png",
                10
        );
        Book book = new Book(
                "Physics Book",
                1,
                "resources/textures/touchable/book.png",
                "resources/textures/stats/items/book.png"
        );
        for (int i = 0; i < GenerationSettings.ITEM_ROOM_COUNT * 10; i++) {
            all_items.add(heart);
            all_items.add(sword);
            all_items.add(book);
        }

        // shuffle the items
        Random random = new Random();
        for (int i = 0; i < all_items.size(); i++) {
            int randomIndexToSwap = random.nextInt(all_items.size());
            Item temp = all_items.get(randomIndexToSwap);
            all_items.set(randomIndexToSwap, all_items.get(i));
            all_items.set(i, temp);
        }

    }
    public static Game loadNewGame(String saveFilePath){
        if(saveFilePath == null){
            throw new IllegalArgumentException("The save file path is null");
        }

        currentGame = new Game(saveFilePath);
        currentGame.currentLevel.updateToSavedData();

        return currentGame;
    }

    public static SavingIO getSavingIO(){ return currentGame.savingIO; }
    public static Level getCurrentLevel(){ return currentGame.currentLevel; }

    public void start(){
        currentLevel.init();
    }
    public void update(){
        currentLevel.update();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        currentLevel.save();
        savingIO.flush();
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
