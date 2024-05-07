package org.project.componentsystem.items;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Item {
    private String name;
    private int weight;

    /**
     * Initializes a new Item with the given id, name, and weight
     * @param name The name of this Item
     * @param weight The weight of this Item
     */
    public Item(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    /**
     * Uses this Item
     */
    public abstract void use();
}
