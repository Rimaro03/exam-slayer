package org.project.items;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Item {
    private String name;
    private int weight;
    private String physicalPath;
    private String invetoryPath;
    /**
     * Initializes a new Item with the given name and weight
     * @param name The name of this Item
     * @param weight The weight of this Item
     * @param physicalPath The path to the physical representation of this Item (e.g. the path to the image file)
     * @param invetoryPath The path to the inventory representation of this Item (e.g. the path to the image file)
     */
    public Item(String name, int weight, String physicalPath, String invetoryPath) {
        this.name = name;
        this.weight = weight;
        this.physicalPath = physicalPath;
        this.invetoryPath = invetoryPath;
    }

    /**
     * Uses this Item
     */
    public abstract void use();
    public abstract void update();
}
