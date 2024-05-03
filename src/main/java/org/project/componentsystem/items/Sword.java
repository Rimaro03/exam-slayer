package org.project.componentsystem.items;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;


public class Sword extends Item{

    // Example of a class that extends Item

    private static final Logger logger = LogManager.getLogger(Sword.class);
    /**
     * Initializes a new Sword with the given id, name, and weight
     * @param id The unique identifier for this Sword
     * @param name The name of this Sword
     * @param weight The weight of this Sword
     */
    public Sword(AtomicInteger id, String name, int weight) {
        super(id.getAndAdd(1), name, weight);
    }

    /**
     * Uses this Sword
     */
    @Override
    public void use() {
        logger.info("Sword used");
    }
}
