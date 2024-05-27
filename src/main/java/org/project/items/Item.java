package org.project.items;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.GameObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Log4j2
@Getter @Setter
public abstract class Item {
    private String name;
    private int weight;
    private String physicalPath;
    private String inventoryPath;
    private BufferedImage inventoryImage;
    /**
     * Initializes a new Item with the given name and weight
     * @param name The name of this Item
     * @param weight The weight of this Item
     * @param physicalPath The path to the physical representation of this Item (e.g. the path to the image file)
     * @param inventoryPath The path to the inventory representation of this Item (e.g. the path to the image file)
     */
    public Item(String name, int weight, String physicalPath, String inventoryPath) {
        this.name = name;
        this.weight = weight;
        this.physicalPath = physicalPath;
        this.inventoryPath = inventoryPath;

        try {
            inventoryImage = ImageIO.read(new File(inventoryPath));
        } catch (IOException e) {
            log.error("Failed to load inventory image for item: {} with inventoryPath : {}", name, inventoryPath);
        }
    }

    /**
     * Uses this Item
     */
    public abstract void use();

    /**
     * Update this Item
     */
    public abstract void update();

    /**
     * Called when this Item is picked up
     * @param by The GameObject that picked up this Item
     */
    public abstract void onPickUp(GameObject by);
}
