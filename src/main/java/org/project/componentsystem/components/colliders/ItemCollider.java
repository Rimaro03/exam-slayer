package org.project.componentsystem.components.colliders;

import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.components.AnimatedSpriteRenderer;
import org.project.componentsystem.components.ItemController;
import org.project.componentsystem.components.stats.PlayerStats;
import org.project.items.Item;
import org.project.utils.Vec2;

import java.util.HashMap;

@Log4j2
public class ItemCollider extends BoxCollider{

    /**
     * Initializes a new Component with the given GameObject, enabled status, size and movable status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param enabled    Whether this Component is enabled or not
     * @param size       The size of the box collider (width, height)
     * @param movable    Whether the collider is movable by others or not
     * @param inside     Whether the collider is inside the box or not
     */
    public ItemCollider(GameObject gameObject, boolean enabled, Vec2 size, boolean movable, boolean inside) {
        super(gameObject, enabled, size, movable, inside);
    }

    /**
     * Initializes a new Component with the given GameObjec, size, movable status and inside status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param size       The size of the box collider (width, height)
     * @param movable    Whether the collider is movable by others or not
     * @param inside     Whether the collider is inside the box or not
     */
    public ItemCollider(GameObject gameObject, Vec2 size, boolean movable, boolean inside) {
        super(gameObject, size, movable, inside);
    }

    @Override
    public void destory() {
        this.getGameObject().getComponent(ItemController.class).destory();
        this.getGameObject().removeComponent(this);
        this.getGameObject().removeComponent(this.getGameObject().getComponent(AnimatedSpriteRenderer.class));
    }
    @Override
    public void onCollide(Collider other) {
        if(other.getGameObject().getName().equals("Player")){
            PlayerStats playerStats = (PlayerStats) other.getGameObject().getComponent(PlayerStats.class);
            Item item = ((ItemController) this.getGameObject().getComponent(ItemController.class)).getItem();
            HashMap<Item, Integer> inventory = playerStats.getInventory();
            inventory.put(item, inventory.getOrDefault(item, 0) + 1);
            log.info("Player picked up item: {}", item.getName());

            item.onPickUp(other.getGameObject());
            this.destory();
        }
    }
}
