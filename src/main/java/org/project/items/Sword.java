package org.project.componentsystem.items;


import lombok.extern.log4j.Log4j2;

import java.util.concurrent.atomic.AtomicInteger;


@Log4j2
public class Sword extends Item{

    // Example of a class that extends Item

    /**
     * Initializes a new Sword with the given id, name, and weight
     * @param name The name of this Sword
     * @param weight The weight of this Sword
     */
    public Sword(String name, int weight) {
        super(name, weight);
    }

    /**
     * Uses this Sword
     */
    @Override
    public void use() {
        log.info("Sword used");
    }
}
