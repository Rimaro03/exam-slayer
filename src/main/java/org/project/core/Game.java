package org.project.core;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.components.bosses.BossesInfo;
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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


@Log4j2
@Getter
public class Game implements WindowListener {
    private static Game currentGame;
    private final Level currentLevel;
    private final SavingIO savingIO;

    private final LinkedList<Item> itemsQueue;
    private final LinkedList<Integer> bossesIdsQueue;

    private Game(String saveFilePath) {
        savingIO = new SavingIO(saveFilePath);

        Long levelSeed = savingIO.getLong("LevelSeed");
        if (levelSeed == null)
            levelSeed = new Random().nextLong();

        currentLevel = new LevelGenerator(levelSeed).build();


        /* -------------------- TMP ------------------------*/

        // TODO : Load all items from a file (sword is a test item)
        // Loop is just for testing
        itemsQueue = new LinkedList<>();
        Sword sword = new Sword(
                "DiamondSword",
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
                "PhysicsBook",
                1,
                "resources/textures/touchable/book.png",
                "resources/textures/stats/items/book.png"
        );
        for (int i = 0; i < GenerationSettings.ITEM_ROOM_COUNT * 10; i++) {
            itemsQueue.add(heart);
            itemsQueue.add(sword);
            itemsQueue.add(book);
        }

        Collections.shuffle(itemsQueue);

        bossesIdsQueue = new LinkedList<>();
        List<Integer> savedBossesIds = savingIO.getIntList("BossesIds");

        if (savedBossesIds != null) {
            for (Integer id : savedBossesIds) {
                bossesIdsQueue.add(id);
            }
        } else {
            for (int id = 0; id < BossesInfo.IMPLEMENTED_BOSSES; id++) {
                bossesIdsQueue.add(id);
            }
            Collections.shuffle(bossesIdsQueue);
        }
    }

    public static Game loadNewGame(String saveFilePath) {
        if (saveFilePath == null) {
            throw new IllegalArgumentException("The save file path is null");
        }

        currentGame = new Game(saveFilePath);
        currentGame.currentLevel.loadMapData();

        return currentGame;
    }


    public static SavingIO getSavingIO() {
        return currentGame.savingIO;
    }

    public static Level getCurrentLevel() {
        return currentGame.currentLevel;
    }

    public static Item popItem() {
        return currentGame.itemsQueue.poll();
    }

    public static Item getItemByName(String name) {
        for (Item item : currentGame.itemsQueue) {
            if (item.getName().equals(name))
                return item;
        }
        return null;
    }

    public static Item removeItem(Item item) {
        currentGame.itemsQueue.remove(item);
        return item;
    }

    /* ------------------------- QUEUE METHODS ------------------------- */

    public static Integer popBossId() {
        return currentGame.bossesIdsQueue.poll();
    }

    public void start() {
        currentLevel.init();
    }

    public void update() {
        currentLevel.update();
    }

    private void exit() {
        currentLevel.saveMapData();
        currentLevel.destroyAllGameObjects();

        savingIO.setIntList("BossesIds", bossesIdsQueue);

        savingIO.flush();
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
