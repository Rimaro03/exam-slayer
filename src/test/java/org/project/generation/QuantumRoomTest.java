package org.project.generation;

import org.junit.jupiter.api.Test;
import org.project.generation.wavecollapse.QuantumRoom;
import org.project.generation.wavecollapse.State;

import java.util.Random;

class QuantumRoomTest {

    @Test
    void removeState() {
        QuantumRoom quantumRoom = new QuantumRoom(0, 0);

        quantumRoom.removeState(new State((byte) 0));

        assert(!quantumRoom.getPossibleStates().contains(new State((byte) 0)));
    }

    @Test
    void collapse() {
        QuantumRoom quantumRoom = new QuantumRoom(0, 0);

        quantumRoom.collapse(new Random());

        assert(quantumRoom.isCollapsed());
        assert(quantumRoom.entropy() == 1);
    }

    @Test
    void getState() {
        QuantumRoom quantumRoom = new QuantumRoom(0, 0);


        boolean error = false;

        try {
            quantumRoom.getState();
        } catch (IllegalStateException e) {
            error = true;
        }

        assert (error);

        collapse();

        boolean noError = false;
        try {
            quantumRoom.getState();
        } catch (IllegalStateException e) {
            noError = true;
        }

        assert (noError);
    }

    @Test
    void testCollapse() {
        QuantumRoom quantumRoom = new QuantumRoom(0, 0);
        quantumRoom.collapse(new State((byte) 0));
        assert(quantumRoom.isCollapsed());
        assert(quantumRoom.entropy() == 1);
        assert(quantumRoom.getState().equals(new State((byte) 0)));
    }
}