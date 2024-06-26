package org.project.generation.wavecollapse;

import org.junit.jupiter.api.Test;

class RoomStateTest {

    @Test
    void checkBit() {
        RoomState state1 = new RoomState((byte) 0b0001);
        RoomState state2 = new RoomState((byte) 0b0010);
        RoomState state3 = new RoomState((byte) 0b0011);
        assert (state1.hasDoor(0));
        assert (state2.hasDoor(1));
        assert (state3.hasDoor(0));
        assert (state3.hasDoor(1));
    }

    @Test
    void canConnect() {
        RoomState doorUp = new RoomState((byte) 1);
        RoomState doorRight = new RoomState((byte) 2);
        RoomState doorDown = new RoomState((byte) 4);
        RoomState doorLeft = new RoomState((byte) 8);
        RoomState allDoors = new RoomState((byte) 15);
        RoomState noDoors = new RoomState((byte) 0);

        assert (RoomState.canConnect(doorUp, doorDown, Direction.UP));
        assert (RoomState.canConnect(doorRight, doorLeft, Direction.RIGHT));
        assert (RoomState.canConnect(doorDown, doorUp, Direction.DOWN));
        assert (RoomState.canConnect(doorLeft, doorRight, Direction.LEFT));
        assert (!RoomState.canConnect(doorUp, doorRight, Direction.UP));
        assert (RoomState.canConnect(allDoors, doorDown, Direction.UP));
        assert (!RoomState.canConnect(noDoors, doorDown, Direction.UP));
    }
}