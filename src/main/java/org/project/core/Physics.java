package org.project.core;

import lombok.Getter;
import org.project.componentsystem.components.colliders.Collider;

import java.util.ArrayList;

/**
 * The Physics class is a singleton class that represents the physics engine of the game.
 * It contains information about the colliders in the game and updates them.
 */
@Getter
public class Physics {
    private final ArrayList<Collider> colliders;

    private boolean colliderRemoved;
    private int indexOfRemove;
    private int amountRemoved;

    public Physics() {
        colliders = new ArrayList<>();
        colliderRemoved = false;
    }

    public void addCollider(Collider collider) {
        colliders.add(collider);
    }

    public void removeCollider(Collider collider) {
        indexOfRemove = colliders.indexOf(collider);
        colliders.remove(indexOfRemove);
        colliderRemoved = true;
    }

    public void update() {
        for (int i = 0; i < colliders.size(); i++) {
            Collider collider = colliders.get(i);
            for (int j = 0; j < colliders.size() && i < colliders.size(); j++) {
                Collider other = colliders.get(j);

                if(i != j && collider.collidesWith(other)){
                    collider.onCollide(other);
                }
            }
        }
    }
}
