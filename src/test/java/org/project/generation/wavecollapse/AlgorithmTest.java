package org.project.generation.wavecollapse;

import org.junit.jupiter.api.Test;
import org.project.generation.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class AlgorithmTest {

    private static Room[] generateRooms(){
        /*
        Generate this pattern:
                       4
                       |
           0 - 1 - 2 - 3
                       |
                       5
         */

        Room room0 = new Room(0, 0);
        Room room1 = new Room(1, 0);
        Room room2 = new Room(2, 0);
        Room room3 = new Room(3, 0);
        Room room4 = new Room(3, 1);
        Room room5 = new Room(3, -1);

        room0.setAdjacentRoom(Direction.RIGHT, room1);
        room1.setAdjacentRoom(Direction.LEFT, room0);

        room1.setAdjacentRoom(Direction.RIGHT, room2);
        room2.setAdjacentRoom(Direction.LEFT, room1);

        room2.setAdjacentRoom(Direction.RIGHT, room3);
        room3.setAdjacentRoom(Direction.LEFT, room2);

        room3.setAdjacentRoom(Direction.UP, room4);
        room4.setAdjacentRoom(Direction.DOWN, room3);

        room3.setAdjacentRoom(Direction.DOWN, room5);
        room5.setAdjacentRoom(Direction.UP, room3);

        return new Room[]{room0, room1, room2, room3, room4, room5};
    }

    @Test
    void getDistanceMap() {
        Room[] rooms = generateRooms();

        HashMap<Room, Integer> distanceMap = Algorithm.getDistanceMap(rooms[0]);

        assertEquals(0, distanceMap.get(rooms[0]));
        assertEquals(1, distanceMap.get(rooms[1]));
        assertEquals(2, distanceMap.get(rooms[2]));
        assertEquals(3, distanceMap.get(rooms[3]));
        assertEquals(4, distanceMap.get(rooms[4]));
        assertEquals(4, distanceMap.get(rooms[5]));
    }

    @Test
    void getConnectedRooms() {
        Room[] rooms = generateRooms();

        HashSet<Room> connectedRooms = Algorithm.getConnectedRooms(rooms[0]);

        for (Room room : rooms) {
            assertTrue(connectedRooms.contains(room));
        }
    }

    @Test
    void getRoomAt() {
        Room[] rooms = generateRooms();

        assertEquals(rooms[4], Algorithm.getRoomAt(3, 1, rooms[0]));
    }

    @Test
    void distanceFromClosest() {
        Room[] rooms = generateRooms();
        ArrayList<Room> checkRooms = new ArrayList<>();
        checkRooms.add(rooms[3]);
        checkRooms.add(rooms[4]);

        assertEquals(3, Algorithm.distanceFromClosest(rooms[0], checkRooms));
    }

    @Test
    void distance() {
        Room[] rooms = generateRooms();

        assertEquals(3, Algorithm.distance(rooms[0], rooms[3]));
    }
}