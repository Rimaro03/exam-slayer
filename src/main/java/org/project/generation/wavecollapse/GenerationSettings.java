package org.project.generation.wavecollapse;

public class GenerationSettings {
    private static final int NO_DOOR_WEIGHTS = 1;
    private static final int FOUR_DOORS_WEIGHTS = 3;
    private static final int ONE_DOOR_WEIGHTS = 1;
    private static final int TWO_DOORS_WEIGHTS = 12;
    private static final int THREE_DOORS_WEIGHTS = 9;

    public static final int[] WEIGHTS = {
            NO_DOOR_WEIGHTS,     // 0000
            ONE_DOOR_WEIGHTS,    // 0001
            ONE_DOOR_WEIGHTS,    // 0010
            TWO_DOORS_WEIGHTS,   // 0011
            ONE_DOOR_WEIGHTS,    // 0100
            TWO_DOORS_WEIGHTS,   // 0101
            TWO_DOORS_WEIGHTS,   // 0110
            THREE_DOORS_WEIGHTS, // 0111
            ONE_DOOR_WEIGHTS,    // 1000
            TWO_DOORS_WEIGHTS,   // 1001
            TWO_DOORS_WEIGHTS,   // 1010
            THREE_DOORS_WEIGHTS, // 1011
            TWO_DOORS_WEIGHTS,   // 1100
            THREE_DOORS_WEIGHTS, // 1101
            THREE_DOORS_WEIGHTS, // 1110
            FOUR_DOORS_WEIGHTS   // 1111
    };
    public static final int DISTANCE_FROM_START_WEIGHT = 100;
    public static final int DISTANCE_FROM_BOSS_WEIGHT = 50;
}
