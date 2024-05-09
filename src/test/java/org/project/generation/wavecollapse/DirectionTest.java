package org.project.generation.wavecollapse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void opposite() {
        assert(Direction.opposite(Direction.UP) == Direction.DOWN);
        assert(Direction.opposite(Direction.RIGHT) == Direction.LEFT);
        assert(Direction.opposite(Direction.DOWN) == Direction.UP);
        assert(Direction.opposite(Direction.LEFT) == Direction.RIGHT);
    }

    @Test
    void x() {
        assert(Direction.x(0, Direction.UP, 1) == 0);
        assert(Direction.x(0, Direction.RIGHT, 1) == 1);
        assert(Direction.x(0, Direction.DOWN, 1) == 0);
        assert(Direction.x(0, Direction.LEFT, 1) == -1);
    }

    @Test
    void y() {
        assert(Direction.y(0, Direction.UP, 1) == 1);
        assert(Direction.y(0, Direction.RIGHT, 1) == 0);
        assert(Direction.y(0, Direction.DOWN, 1) == -1);
        assert(Direction.y(0, Direction.LEFT, 1) == 0);
    }
}