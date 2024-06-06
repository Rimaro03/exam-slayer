package org.project.items;


import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.stats.PlayerStats;
import org.project.componentsystem.components.weapons.PlayerShootingController;
import org.project.componentsystem.components.weapons.WeaponType;
import org.project.core.Game;


@Log4j2
public class Sword extends Item {

    /**
     * Initializes a new Item with the given name and weight
     *
     * @param name         The name of this Item
     * @param weight       The weight of this Item
     * @param physicalPath The path to the physical representation of this Item (e.g. the path to the image file)
     * @param invetoryPath The path to the inventory representation of this Item (e.g. the path to the image file)
     */
    public Sword(String name, int weight, String physicalPath, String invetoryPath) {
        super(name, weight, physicalPath, invetoryPath);
    }


    /**
     * Uses this Sword
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
     * Called when this Sword is picked up
     *
     * @param by The GameObject that picked up this Sword
     */
    @Override
    public void onPickUp(GameObject by) {
        PlayerStats playerStats = (PlayerStats) by.getComponent(PlayerStats.class);
        playerStats.setSpeed(playerStats.getSpeed() - getWeight());
        Game.getCurrentLevel().addComponentToGameObject(by, new PlayerShootingController(by,  WeaponType.Sword));
    }
}
