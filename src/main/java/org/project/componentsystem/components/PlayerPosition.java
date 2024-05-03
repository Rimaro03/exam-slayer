package org.project.componentsystem.components;

import lombok.Getter;
import lombok.Setter;

import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.GameObject;
import org.project.utils.Vec2;

@Getter @Setter @Log4j2
public class PlayerPosition extends Component{
    private Vec2 position;
    /**
     * Initializes a new PlayerPosition with the given GameObject, id, name, and enabled status
     * @param gameObject The reference to the GameObject that this PlayerPosition is attached to
     * @param id The unique identifier for this PlayerPosition
     * @param name The name of this PlayerPosition
     * @param enabled Whether this PlayerPosition is enabled or not
     * @param position The position of the player
     */
    public PlayerPosition(GameObject gameObject, int id, String name, boolean enabled, Vec2 position) {
        super(gameObject, id, name, enabled);
        this.position = position;
    }

    /**
     * Initializes a new PlayerPosition with the given GameObject, id, and name
     * @param gameObject The reference to the GameObject that this PlayerPosition is attached to
     * @param id The unique identifier for this PlayerPosition
     * @param name The name of this PlayerPosition
     * @param position The position of the player
     */
    public PlayerPosition(GameObject gameObject, int id, String name, Vec2 position) {
        super(gameObject, id, name);
        this.position = position;
    }

    @Override
    public void start() {
        log.info("Position component started");
    }

    @Override
    public void update() {
        log.info("Position component updated");
    }
}
