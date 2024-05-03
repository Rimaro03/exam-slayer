package org.project.componentsystem.components;

import lombok.Getter;
import lombok.Setter;
import org.project.componentsystem.GameObject;

import java.util.HashMap;

@Setter @Getter
public class PlayerStats extends Component{
    private int health;
    private HashMap<Object, Integer> inventory;

    public PlayerStats(GameObject gameObject, int id, String name) {
        super(gameObject, id, name);
    }

    public PlayerStats(GameObject gameObject, int id, String name, boolean enabled) {
        super(gameObject, id, name, enabled);
    }

    public void start() {
        System.out.println("Stats component started");
    }

    public void update() {
        System.out.println("Stats component updated");
    }
}
