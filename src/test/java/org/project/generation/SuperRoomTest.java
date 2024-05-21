package org.project.generation;

import org.junit.jupiter.api.Test;
import org.project.generation.wavecollapse.SuperRoom;
import org.project.generation.wavecollapse.RoomState;

import java.util.Random;

class SuperRoomTest {

    @Test
    void removeState() {
        SuperRoom superRoom = new SuperRoom(0, 0);

        superRoom.removeState(new RoomState((byte) 0));

        assert(!superRoom.getPossibleStates().contains(new RoomState((byte) 0)));
    }

    @Test
    void collapse() {
        SuperRoom superRoom = new SuperRoom(0, 0);

        superRoom.collapse(new Random());

        assert(superRoom.isCollapsed());
        assert(superRoom.entropy() == 1);
    }

    @Test
    void getState() {
        SuperRoom superRoom = new SuperRoom(0, 0);


        boolean error = false;

        try {
            superRoom.getState();
        } catch (IllegalStateException e) {
            error = true;
        }

        assert (error);

        collapse();

        boolean noError = false;
        try {
            superRoom.getState();
        } catch (IllegalStateException e) {
            noError = true;
        }

        assert (noError);
    }

    @Test
    void testCollapse() {
        SuperRoom superRoom = new SuperRoom(0, 0);
        superRoom.collapse(new RoomState((byte) 0));
        assert(superRoom.isCollapsed());
        assert(superRoom.entropy() == 1);
        assert(superRoom.getState().equals(new RoomState((byte) 0)));
    }
}