package org.project.componentsystem.components.enemies;

import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.Component;
import org.project.componentsystem.components.colliders.DoorCollider;
import org.project.core.Game;

import java.util.ArrayList;

/**
 * A component that locks the room until all entities are dead.
 */
public class RoomLocker extends Component {

    private ArrayList<Object> entities;
    private ArrayList<DoorCollider> doorColliders;

    public RoomLocker(GameObject gameObject, boolean enabled) {
        super(gameObject, enabled);
    }

    public RoomLocker(GameObject gameObject) {
        this(gameObject, true);
        doorColliders = new ArrayList<>();
        entities = new ArrayList<>();
    }

    public void addEntity(Object entity) {
        entities.add(entity);
    }
    public void removeEntity(Object entity) {
        entities.remove(entity);
    }

    @Override
    public void start() {
        for (GameObject go : Game.getCurrentLevel().findGameObjects("Door")) {
            DoorCollider doorCollider = (DoorCollider) go.getComponent(DoorCollider.class);
            doorColliders.add(doorCollider);
            doorCollider.setEnabled(false);
        }
    }

    @Override
    public void update() {
        if(entities.isEmpty()){
            for (DoorCollider doorCollider : doorColliders) {
                doorCollider.setEnabled(true);
            }
            doorColliders.clear();
            setEnabled(false);
        }
    }

    @Override
    public void destory() {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
