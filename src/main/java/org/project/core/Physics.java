package org.project.core;

import lombok.Getter;
import org.project.componentsystem.components.colliders.Collider;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

@Getter
public class Physics {
    public static Physics instance;
    private final ArrayList<Collider> colliders;
    private Physics() {
        colliders = new ArrayList<>();
    }

    public static Physics getInstance() {
        if (instance == null) {
            instance = new Physics();
        }
        return instance;
    }
    public static void addCollider(Collider collider) { getInstance().addColliderInternal(collider); }
    public static void removeCollider(Collider collider) { getInstance().removeColliderInternal(collider);}
    public static void update(){
        getInstance().updateInternal();
    }

    private void addColliderInternal(Collider collider){ colliders.add(collider); }
    private void removeColliderInternal(Collider collider){ colliders.remove(collider); }

    private void updateInternal(){
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
