package org.project.generation;


import lombok.Getter;
import lombok.Setter;
import org.project.componentsystem.GameObject;
import org.project.componentsystem.GameObjectFactory;
import org.project.componentsystem.components.colliders.BoxCollider;
import org.project.componentsystem.components.colliders.Collider;
import org.project.core.Game;
import org.project.generation.wavecollapse.Direction;
import org.project.generation.wavecollapse.InvalidDirectionException;
import org.project.items.Item;
import org.project.utils.Vec2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * A room is a container of game objects that can be enabled and disabled.
 * It also contains 4 pointers to his neighbour rooms.
 */
public class Room {
    public static final float SIZE = 15.75f;
    private final ArrayList<GameObject> gameObjects;
    private final Room[] adjacentRooms;
    @Getter
    private final int x, y;
    private boolean initialized;
    @Getter
    @Setter
    private boolean cleared;
    @Setter
    private InitType initType;

    public Room(int x, int y) {
        gameObjects = new ArrayList<>();
        adjacentRooms = new Room[4];
        initialized = false;
        cleared = false;
        this.x = x;
        this.y = y;
    }

    /**
     * @param direction The direction of the adjacent room. {@link Direction}
     * @param room      The room to set as adjacent.
     */
    public void setAdjacentRoom(int direction, Room room) {
        if (direction < 0 || direction >= 4) {
            throw new InvalidDirectionException();
        }
        adjacentRooms[direction] = room;
    }

    /**
     * @param direction The direction of the adjacent room. {@link Direction}
     * @return The adjacent room in the specified direction, null if there isn't.
     */
    public Room getAdjacentRoom(int direction) {
        if (direction < 0 || direction >= 4) {
            throw new InvalidDirectionException();
        }
        return adjacentRooms[direction];
    }

    /**
     * @param name The name of the GameObject to get
     * @return The GameObject with the specified name, null if it doesn't exist.
     */
    public GameObject getGameObject(String name) {
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getName().equals(name)) {
                return gameObject;
            }
        }
        return null;
    }
    public GameObject[] getGameObjects(String name) {
        ArrayList<GameObject> list = new ArrayList<>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getName().equals(name)) {
                list.add(gameObject);
            }
        }
        return list.toArray(new GameObject[list.size()]);
    }

    /**
     * Adds a new GameObject to the room and starts it
     *
     * @param gameObject The GameObject to add
     */
    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    /**
     * Removes a GameObject from the room and destroys it
     *
     * @param gameObject The GameObject to remove
     */
    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }


    /**
     * sets the room enabled status to the specified and applies
     * it to all the game objects of the room.
     *
     * @param enabled the status to set the room to.
     */
    public void setEnabled(boolean enabled) {
        for (GameObject go : gameObjects) {
            go.setEnabled(enabled);
        }

        if (!initialized)
            init();
    }

    /**
     * Spawns all the starting objects of the room like walls and doors,
     * plus all the game objects specified by its InitType
     * (boss if is a boss room, player if is the start room, etc...)
     */
    public void init() {
        if (initialized)
            throw new RuntimeException("Room already initialized");

        // Create room collider-sprite game object
        GameObject roomGameObject = GameObjectFactory.createRoomGameObject();
        gameObjects.add(roomGameObject);

        // Create room door collider game objects
        for (int direction = 0; direction < 4; direction++) {
            // direction 0 = up, 1 = right, 2 = down, 3 = left
            if (adjacentRooms[direction] == null) {
                continue;
            }

            GameObject door = GameObjectFactory.createDoorGameObject(direction, (Collider) roomGameObject.getComponent(BoxCollider.class));
            door.setPosition(door.getPosition().add(new Vec2(Direction.x(0, direction) * (SIZE * .5f - 1.5f), Direction.y(0, direction) * (SIZE * 0.5f - 1.5f))));
            gameObjects.add(door);
        }

        // IF YOU NEED TO ADD MORE GAME OBJECTS, ADD THEM HERE!!!
        switch (initType) {
            case Start:
                gameObjects.add(GameObjectFactory.createPlayer());
                break;
            case Boss:
                Integer bossId = Game.getCurrentLevel().popBossId();
                // NULL CHECK IS TEMPORARY UNTIL ALL BOSSES ARE IMPLEMENTED
                gameObjects.add(GameObjectFactory.createBoss(bossId != null ? bossId : 0));
                break;
            case Normal:
                GameObject[] enemies = GameObjectFactory.createEnemies(new Random().nextInt(2) + 4);
                gameObjects.addAll(Arrays.asList(enemies));
            case Item:
                Item item = Game.getCurrentLevel().popItem();
                if (item != null) {
                    GameObject itemGO = GameObjectFactory.createPhysicalItem(
                            new Vec2(1, 1),
                            item
                    );
                    gameObjects.add(itemGO);
                }
                break;

            case Empty:
                break;
            default:
                throw new RuntimeException("Invalid init type");
        }


        for (GameObject go : gameObjects) {
            if(go.getName().equals("Player") && initType != InitType.Start)
                continue;
            go.start();
        }

        initialized = true;
    }

    /**
     * Updates all the game objects of the room
     */
    public void updateGameObjects() {
        for (int i = 0; i < gameObjects.size(); i++) {
            if(gameObjects.get(i).isEnabled())
                gameObjects.get(i).update();
        }
    }

    public void destroyGameObjects() {
        while (!gameObjects.isEmpty()) {
            GameObject go = gameObjects.get(gameObjects.size() - 1);
            gameObjects.remove(go);
            go.destroy();
        }
    }

    public String toString() {
        if (adjacentRooms[0] != null && adjacentRooms[2] != null && adjacentRooms[3] != null && adjacentRooms[1] != null) {
            return "┼─";
        } else if (adjacentRooms[0] != null && adjacentRooms[2] != null && adjacentRooms[3] != null) {
            return "┤ ";
        } else if (adjacentRooms[0] != null && adjacentRooms[2] != null && adjacentRooms[1] != null) {
            return "├─";
        } else if (adjacentRooms[0] != null && adjacentRooms[3] != null && adjacentRooms[1] != null) {
            return "┬─";
        } else if (adjacentRooms[2] != null && adjacentRooms[3] != null && adjacentRooms[1] != null) {
            return "┴─";
        } else if (adjacentRooms[0] != null && adjacentRooms[1] != null) {
            return "┌─";
        } else if (adjacentRooms[0] != null && adjacentRooms[3] != null) {
            return "┐ ";
        } else if (adjacentRooms[2] != null && adjacentRooms[1] != null) {
            return "└─";
        } else if (adjacentRooms[2] != null && adjacentRooms[3] != null) {
            return "┘ ";
        } else if (adjacentRooms[0] != null && adjacentRooms[2] != null) {
            return "│ ";
        } else if (adjacentRooms[3] != null && adjacentRooms[1] != null) {
            return "──";
        } else if (adjacentRooms[0] != null) {
            return "│ ";
        } else if (adjacentRooms[2] != null) {
            return "│ ";
        } else if (adjacentRooms[3] != null) {
            return "──";
        } else if (adjacentRooms[1] != null) {
            return "──";
        } else {
            return "  ";
        }
    }

    /**
     * The type of initialization of the room :
     */
    public enum InitType {
        /**
         * The room is the start room of the level.
         */
        Start,
        /**
         * The room is a boss room.
         */
        Boss,
        /**
         * The room is a normal room with normal enemies.
         */
        Normal,
        /**
         * The room is a room with an item inside.
         */
        Item,
        /**
         * The room is empty a shop inside (Used to load a saved game).
         */
        Empty
    }
}
