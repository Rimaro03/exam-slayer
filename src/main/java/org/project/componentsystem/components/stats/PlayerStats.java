package org.project.componentsystem.components.stats;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.menus.GameOverMenu;
import org.project.componentsystem.components.menus.PauseMenu;
import org.project.core.Game;
import org.project.core.rendering.Renderer;
import org.project.items.Heart;
import org.project.items.Item;
import org.project.utils.Vec2;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Setter
@Getter
@Log4j2
/**
 * A component that keeps track of the health and speed of the Player game object.
 */
public class PlayerStats extends Stats {
    private HashMap<Item, Integer> inventory = new HashMap<>();
    private Vec2 position = new Vec2(-12.5f, 7);

    private boolean isStarted = false;

    /**
     * Initializes a new PlayerStats with the given GameObject
     *
     * @param gameObject The reference to the GameObject that this PlayerStats is attached to
     * @param health     The health of this PlayerStats
     * @param speed      The speed of this PlayerStats
     */
    public PlayerStats(GameObject gameObject, int health, int speed) {
        this(gameObject, true, health, speed);
    }

    /**
     * Initializes a new PlayerStats with the given GameObject and enabled status
     *
     * @param gameObject The reference to the GameObject that this PlayerStats is attached to
     * @param enabled    Whether this PlayerStats is enabled or not
     * @param health     The health of this PlayerStats
     * @param speed      The speed of this PlayerStats
     */
    public PlayerStats(GameObject gameObject, boolean enabled, int health, int speed) {
        super(gameObject, enabled, health, speed);
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            Game.setPaused(true);

            getGameObject().getComponent(PauseMenu.class).setEnabled(false);
            GameOverMenu gom = (GameOverMenu) Game.getCurrentLevel().findGameObject("GameOverMenu").getComponent(GameOverMenu.class);
            gom.enable(false);
        }
    }

    @Override
    public void start() {
        if (isStarted) return;
        isStarted = true;
        List<String> items = Game.getSavingIO().getStringList("inventory");

        if (items != null) {
            for (String item : items) {
                Item loadedItem = Game.getCurrentLevel().getItemByName(item);
                if (loadedItem != null) {
                    this.addItem(loadedItem);
                    if (!(loadedItem instanceof Heart)) {
                        loadedItem.onPickUp(getGameObject());
                    }
                }
                Game.getCurrentLevel().removeItem(loadedItem);
            }
        }

        Integer health = Game.getSavingIO().getInt("health");
        if (health != null) {
            this.health = health;
        }
    }

    @Override
    @SneakyThrows
    public void update() {
        speed = Math.max(speed, 5);
        Renderer.addTextToRenderQueue(position, "Health: " + health, Color.WHITE, 10, 2);
        Renderer.addTextToRenderQueue(position.add(new Vec2(0, -.5f)), "Speed: " + speed, Color.WHITE, 10, 2);
        Object[] keys = inventory.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            Item item = (Item) keys[i];
            Renderer.addImageToRenderQueue(
                    position.add(new Vec2(0.5f, -2.5f - i)),
                    item.getInventoryImage(),
                    i
            );
            for (int j = 1; j < inventory.get(item); j++) {
                Renderer.addImageToRenderQueue(
                        position.add(new Vec2(0.5f + j / 2f * .6f, -2.5f - i)),
                        item.getInventoryImage(),
                        i + j
                );
            }
        }


    }

    /**
     * Destory the component
     */
    @Override
    public void destory() {
        ArrayList<String> items = new ArrayList<>();
        for (Item item : this.inventory.keySet()) {
            for (int i = 0; i < this.inventory.get(item); i++) {
                items.add(item.getName());
            }
        }

        Game.getSavingIO().setStringList("inventory", items);
        Game.getSavingIO().setInt("health", health);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public PlayerStats addItem(Item item) {
        if (inventory.containsKey(item)) {
            inventory.put(item, inventory.get(item) + 1);
        } else {
            inventory.put(item, 1);
        }
        return this;
    }

}
