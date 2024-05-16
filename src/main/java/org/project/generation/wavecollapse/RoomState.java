package org.project.generation.wavecollapse;

import lombok.Getter;

/** Represent a room doors configuration, the state is coded in a 8bit integer
 * where bit 0 is the top door, bit 1 is the right door, bit 2 is the bottom door
 * and bit 3 is the left door.
 * 0 is door closed, 1 is door open.
 * */
@Getter
public class RoomState {
    public static final RoomState NO_DOOR = new RoomState((byte)0b0000);
    public static final RoomState ALL_DOORS = new RoomState((byte)0b1111);

    private final byte value;

    public RoomState(byte value){
        this.value = value;
    }

    /** Returns true if the bit at index direction is 1 false if is 0. */
    public boolean hasDoor(int direction) { return (value & (1 << direction)) != 0; }

    /** Returns true if from A to B all states have an open door. */
    public static boolean canConnect(RoomState stateA, RoomState stateB, int directionFromAtoB){
        return stateA.hasDoor(directionFromAtoB) == stateB.hasDoor(Direction.opposite(directionFromAtoB));
    }


    /* ---------------- OBJECT METHODS -----------------*/
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof RoomState){
            return value == ((RoomState) obj).value;
        }
        return false;
    }
    @Override
    public String toString() {
        return  "{UP["   + hasDoor(0) + "], " +
                "RIGHT[" + hasDoor(1) + "], " +
                "DOWN["  + hasDoor(2) + "], " +
                "LEFT["  + hasDoor(3) + "]}";
    }
}
