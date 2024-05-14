package org.project.generation.wavecollapse;

import org.project.generation.Room;
public class RoomDistancePair implements Comparable<RoomDistancePair>{
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
