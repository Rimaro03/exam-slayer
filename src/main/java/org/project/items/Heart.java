package org.project.items;

import lombok.Getter;
import lombok.Setter;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.stats.PlayerStats;

@Getter @Setter
public class Heart extends Item{
    private final int health;
    /**
     * Initializes a new Item with the given name and weight and health value
     * This item is used to increase the health of the player when picked up
     *
     * @param name         The name of this Item
     * @param weight       The weight of this Item
     * @param physicalPath The path to the physical representation of this Item (e.g. the path to the image file)
     * @param invetoryPath The path to the inventory representation of this Item (e.g. the path to the image file)
     * @param health       The health of this Heart
     */
    public Heart(String name, int weight, String physicalPath, String invetoryPath, int health) {
        super(name, weight, physicalPath, invetoryPath);
        this.health = health;
    }

    /**
     * Uses this Item
     */
    @Override
    public void use() {

    }

    /**
     *
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
        playerStats.setHealth(playerStats.getHealth() + health);
    }


}
