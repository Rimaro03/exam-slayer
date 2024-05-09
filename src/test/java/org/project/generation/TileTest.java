package org.project.generation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    @Test
    void removeState() {
        Tile tile = new Tile(0, 0);

        tile.removeState(new State((byte) 0));

        assert(!tile.getPossibleStates().contains(new State((byte) 0)));
    }

    @Test
    void collapse() {
        Tile tile = new Tile(0, 0);

        tile.collapse();

        assert(tile.isCollapsed());
        assert(tile.entropy() == 1);
    }

    @Test
    void getState() {
        Tile tile = new Tile(0, 0);


        boolean error = false;

        try {
            tile.getState();
        } catch (IllegalStateException e) {
            error = true;
        }

        assert (error);

        collapse();

        boolean noError = false;
        try {
            tile.getState();
        } catch (IllegalStateException e) {
            noError = true;
        }

        assert (noError);
    }

    @Test
    void testCollapse() {
        Tile tile = new Tile(0, 0);
        tile.collapse(new State((byte) 0));
        assert(tile.isCollapsed());
        assert(tile.entropy() == 1);
        assert(tile.getState().equals(new State((byte) 0)));
    }
}