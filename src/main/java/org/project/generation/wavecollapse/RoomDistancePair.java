package org.project.generation.wavecollapse;

import org.project.generation.Room;

/**
 * A container of a room and a distance.
 */
public class RoomDistancePair implements Comparable<RoomDistancePair> {
    public final Room room;
    public final int distance;

    public RoomDistancePair(Room room, int distance) {
        this.room = room;
        this.distance = distance;
    }


    @Override
    public int compareTo(RoomDistancePair o) {
        return distance - o.distance;
    }
}
