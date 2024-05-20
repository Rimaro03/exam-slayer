package org.project.componentsystem.components;

import lombok.Getter;
import lombok.Setter;
import org.project.componentsystem.GameObject;
import org.project.items.Item;

@Getter @Setter
public class ItemController extends Component{
    // This class works as a mediator between the Item and the GameObject

    private Item item;
    /**
     * Initializes a new Component with the given GameObject and enabled status
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param enabled    Whether this Component is enabled or
     * @param item The item that this ItemController is attached to
     */
    public ItemController(GameObject gameObject, boolean enabled, Item item) {
        super(gameObject, enabled);
        this.item = item;
    }

    /**
     * Initializes a new Component with the given GameObject
     *
     * @param gameObject The reference to the GameObject that this Component is attached to
     * @param item The item that this ItemController is attached to
     */
    public ItemController(GameObject gameObject, Item item) {
        super(gameObject);
        this.item = item;
    }

    /**
     * Runs when the Component is first created
     * This method is meant to be overridden by subclasses
     */
    @Override
    public void start() {

    }

    /**
     * Runs every frame
     * This method is meant to be overridden by subclasses
     */
    @Override
    public void update() {

    }

    /**
     * Destory the component
     */
    @Override
    public void destory() {

    }

    /**
     * Runs when the Component is enabled
     * This method is meant to be overridden by subclasses
     */
    @Override
    public void onEnable() {

    }

    /**
     * Runs when the Component is disabled
     * This method is meant to be overridden by subclasses
     */
    @Override
    public void onDisable() {

    }
}
