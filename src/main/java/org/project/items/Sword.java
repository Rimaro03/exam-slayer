package org.project.items;


import lombok.extern.log4j.Log4j2;


@Log4j2
public class Sword extends Item{

    // Example of a class that extends Item
    private final int damage;

    /**
     * Initializes a new Item with the given name and weight
     *
     * @param name         The name of this Item
     * @param weight       The weight of this Item
     * @param physicalPath The path to the physical representation of this Item (e.g. the path to the image file)
     * @param invetoryPath The path to the inventory representation of this Item (e.g. the path to the image file)
     * @param damage       The damage of this Sword
     */
    public Sword(String name, int weight, String physicalPath, String invetoryPath, int damage) {
        super(name, weight, physicalPath, invetoryPath);
        this.damage = damage;
    }


    /**
     * Uses this Sword
     */
    @Override
    public void use() {
        log.info("Sword used with damage: {}", damage);
    }
}
