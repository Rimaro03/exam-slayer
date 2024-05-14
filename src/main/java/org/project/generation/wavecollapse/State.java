package org.project.generation.wavecollapse;

import lombok.Getter;

/** Represent a room doors configuration, the state is coded in a 8bit integer
 * where bit 0 is the top door, bit 1 is the right door, bit 2 is the bottom door
 * and bit 3 is the left door.
 * 0 is door closed, 1 is door open.
 * */
@Getter
public class State {
    public static final State NO_DOOR = new State((byte)0b0000);
    public static final State ALL_DOORS = new State((byte)0b1111);

    private static final int NO_DOOR_WEIGHTS = 1;
    private static final int FOUR_DOORS_WEIGHTS = 6;
    private static final int ONE_DOOR_WEIGHTS = 3;
    private static final int TWO_DOORS_WEIGHTS = 12;
    private static final int THREE_DOORS_WEIGHTS = 9;
    public static final int[] WEIGHTS = {
            NO_DOOR_WEIGHTS, // 0000
            ONE_DOOR_WEIGHTS, // 0001
            ONE_DOOR_WEIGHTS, // 0010
            TWO_DOORS_WEIGHTS, // 0011
            ONE_DOOR_WEIGHTS, // 0100
            TWO_DOORS_WEIGHTS, // 0101
            TWO_DOORS_WEIGHTS, // 0110
            THREE_DOORS_WEIGHTS, // 0111
            ONE_DOOR_WEIGHTS, // 1000
            TWO_DOORS_WEIGHTS, // 1001
            TWO_DOORS_WEIGHTS, // 1010
            THREE_DOORS_WEIGHTS, // 1011
            TWO_DOORS_WEIGHTS, // 1100
            THREE_DOORS_WEIGHTS, // 1101
            THREE_DOORS_WEIGHTS, // 1110
            FOUR_DOORS_WEIGHTS // 1111
    };

    private final byte value;

    public State(byte value){
        this.value = value;
    }

    /** Returns true if the bit at index direction is 1 false if is 0. */
    public boolean hasDoor(int direction) { return (value & (1 << direction)) != 0; }

    /** Returns true if from A to B all states have an open door. */
    public static boolean canConnect(State stateA, State stateB, int directionFromAtoB){
        return stateA.hasDoor(directionFromAtoB) == stateB.hasDoor(Direction.opposite(directionFromAtoB));
    }


    /* ---------------- OBJECT METHODS -----------------*/
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof State){
            return value == ((State) obj).value;
        }
        return false;
    }
    @Override
    public String toString() {
        return  "UP["    + hasDoor(0) + "] " +
                "RIGHT[" + hasDoor(1) + "] " +
                "DOWN["  + hasDoor(2) + "] " +
                "LEFT["  + hasDoor(3) + "]";
    }
}
