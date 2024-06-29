package org.project.items;

import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.stats.PlayerStats;
import org.project.componentsystem.components.weapons.PlayerShootingController;
import org.project.componentsystem.components.weapons.WeaponType;
import org.project.core.Game;

public class Book extends Item {
    /**
     * Initializes a new Item with the given name and weight
     *
     * @param name          The name of this Item
     * @param weight        The weight of this Item
     * @param physicalPath  The path to the physical representation of this Item (e.g. the path to the image file)
     * @param inventoryPath The path to the inventory representation of this Item (e.g. the path to the image file)
     */
    public Book(String name, int weight, String physicalPath, String inventoryPath) {
        super(name, weight, physicalPath, inventoryPath);
    }

    /**
     * Uses this Item
     */
    @Override
    public void use() {
    }

    /**
     * Update this Item
     */
    @Override
    public void update() {
    }

    /**
     * Called when this Item is picked up
     *
     * @param by The GameObject that picked up this Item
     */
    @Override
    public void onPickUp(GameObject by) {
        PlayerStats playerStats = (PlayerStats) by.getComponent(PlayerStats.class);
        playerStats.setSpeed(playerStats.getSpeed() - getWeight());
        Game.getCurrentLevel().addComponentToGameObject(by, new PlayerShootingController(by, WeaponType.PhysicsBook));
    }
}
