package org.project.core;

import lombok.Getter;
import org.project.componentsystem.components.colliders.Collider;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

@Getter
public class Physics {
    private final ArrayList<Collider> colliders;
    public Physics() {
        colliders = new ArrayList<>();
    }

    public void addCollider(Collider collider){ colliders.add(collider); }
    public void removeCollider(Collider collider){ colliders.remove(collider); }

    public void update(){
        try {
            for (Collider collider : colliders) {
                for (Collider other : colliders) {
                    if (collider != other && collider.collidesWith(other)) {
                        collider.onCollide(other);
                    }
                }
            }
        } catch (ConcurrentModificationException ignore) {
            // Do nothing
        }
    }
}
