package org.project.componentsystem;

import lombok.Getter;
import org.project.componentsystem.components.colliders.Collider;

import java.util.ArrayList;

@Getter
public class Physics {
    public static ArrayList<Collider> colliders = new ArrayList<>();
    private static Physics instance;

    private Physics() {

    }

    public static Physics getInstance() {
        if(instance == null) {
            instance = new Physics();
        }
        return instance;
    }

    public static void addCollider(Collider collider) {
        colliders.add(collider);
    }

    public static void removeCollider(Collider collider) {
        colliders.remove(collider);
    }

    public static void update(){
        for (Collider collider : colliders) {
            for (Collider other : colliders) {
                if (collider != other && collider.collidesWith(other)) {
                    collider.onCollide(other);
                }
            }
        }
    }
}
