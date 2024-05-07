package org.project;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.GameObject;

import java.util.ArrayList;

@Getter @Setter @Log4j2
public class Room {
    private ArrayList<GameObject> entities = new ArrayList<>();
    private boolean doorUp;
    private boolean doorDown;
    private boolean doorLeft;
    private boolean doorRight;

    public Room(boolean doorUp, boolean doorDown, boolean doorLeft, boolean doorRight) {
        this.doorUp = doorUp;
        this.doorDown = doorDown;
        this.doorLeft = doorLeft;
        this.doorRight = doorRight;
    }

    /**
     * Add an entity to the room
     * @param entity the entity to add
     */
    public void addEntity(GameObject entity) {
        entities.add(entity);
    }

    /**
     * Remove an entity from the room
     * @param entity the entity to remove
     */
    public void removeEntity(GameObject entity) {
        entities.remove(entity);
    }

    /**
     * Print all entities in the room
     */
    public void printEntities() {
        for (GameObject entity : entities) {
            log.info(entity);
        }
    }
}
