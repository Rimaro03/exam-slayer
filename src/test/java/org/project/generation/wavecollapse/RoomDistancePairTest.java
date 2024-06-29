package org.project.generation.wavecollapse;

import org.junit.jupiter.api.Test;
import org.project.generation.Room;

import static org.junit.jupiter.api.Assertions.*;

class RoomDistancePairTest {
    @Test
    public void CompareToTest() {
        RoomDistancePair pair1 = new RoomDistancePair(new Room(0, 0), 1);
        RoomDistancePair pair2 = new RoomDistancePair(new Room(0, 0), 2);

        assertEquals(-1, pair1.compareTo(pair2));
    }
}