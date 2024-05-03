package org.project.componentsystem.items;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Item {
    private int id;
    private String name;
    private int weight;

    /**
     * Initializes a new Item with the given id, name, and weight
     * @param id The unique identifier for this Item
     * @param name The name of this Item
     * @param weight The weight of this Item
     */
    public Item(int id, String name, int weight) {
        this.id = id;
        this.name = name;
        this.weight = weight;
    }

    /**
     * Uses this Item
     */
    public abstract void use();
}
