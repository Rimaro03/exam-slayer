package org.project.core;

import lombok.Getter;
import org.project.componentsystem.components.colliders.Collider;

import java.util.ArrayList;

@Getter
public class Physics {
    private final ArrayList<Collider> colliders;

    public Physics() {
        colliders = new ArrayList<>();
    }

    public void addCollider(Collider collider) {
        colliders.add(collider);
    }

    public void removeCollider(Collider collider) {
        colliders.remove(collider);
    }

    public void update() {
        ArrayList<Collider> collidersCopy = new ArrayList<>(colliders);
        for (Collider collider : collidersCopy) {
            for (Collider other : collidersCopy) {
                if (collider != other && collider.collidesWith(other)) {
                    collider.onCollide(other);
                }
            }
        }
    }
}
