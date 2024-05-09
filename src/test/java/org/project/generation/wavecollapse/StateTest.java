package org.project.generation.wavecollapse;

import org.junit.jupiter.api.Test;
import org.project.generation.Direction;
import org.project.generation.State;

class StateTest {

    @Test
    void checkBit() {
        State state1 = new State((byte)0b0001);
        State state2 = new State((byte)0b0010);
        State state3 = new State((byte)0b0011);
        assert(state1.hasDoor(0));
        assert(state2.hasDoor(1));
        assert(state3.hasDoor(0));
        assert(state3.hasDoor(1));
    }

    @Test
    void canConnect() {
        State doorUp = new State((byte)1);
        State doorRight = new State((byte)2);
        State doorDown = new State((byte)4);
        State doorLeft = new State((byte)8);
        State allDoors = new State((byte)15);
        State noDoors = new State((byte)0);

        assert(State.canConnect(doorUp, doorDown, Direction.UP));
        assert(State.canConnect(doorRight, doorLeft, Direction.RIGHT));
        assert(State.canConnect(doorDown, doorUp, Direction.DOWN));
        assert(State.canConnect(doorLeft, doorRight, Direction.LEFT));
        assert(!State.canConnect(doorUp, doorRight, Direction.UP));
        assert(State.canConnect(allDoors, doorDown, Direction.UP));
        assert(!State.canConnect(noDoors, doorDown, Direction.UP));
    }
}