package org.project.generation.wavecollapse;

/**
 * Contains the global settings for the generation
 * of the level like wights for the different configurations
 * of doors, boss and start room generation settings
 */
public class GenerationSettings {
    public static final int MAP_SIZE = 8;
    public static final int BOSS_ROOM_COUNT = 3;
    public static final int ITEM_ROOM_COUNT = 8;
    private static final int NO_DOOR_WEIGHTS = 1;
    private static final int FOUR_DOORS_WEIGHTS = 3;
    private static final int ONE_DOOR_WEIGHTS = 1;
    private static final int TWO_DOORS_WEIGHTS = 12;
    private static final int THREE_DOORS_WEIGHTS = 9;
    /**
     * The weights for the different configurations of doors.
     * The index of the array is the binary representation of the door configuration.
     */
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
    private static final int DISTANCE_FROM_START_WEIGHT_FOR_BOSS_SCORE = 100;
    private static final int DISTANCE_FROM_BOSS_WEIGHT_FOR_BOSS_SCORE = 50;

    private static final int DISTANCE_FROM_BOSS_WEIGHT_FOR_ITEM_SCORE = 1;
    private static final int DISTANCE_FROM_START_WEIGHT_FOR_ITEM_SCORE = 1;
    private static final int DISTANCE_FROM_ITEM_WEIGHT_FOR_ITEM_SCORE = 1;

    /**
     * Returns the score of a room to be a boss room.
     *
     * @param distFromStart       the distance from the start room.
     * @param distFromClosestBoss the distance from the closest boss room.
     * @return log(BossDist) * BossWeight + log(StartDist) * StartWeight
     */
    public static float getBossRoomScore(int distFromStart, int distFromClosestBoss) {
        return (float) (GenerationSettings.DISTANCE_FROM_BOSS_WEIGHT_FOR_BOSS_SCORE * Math.log(distFromStart) +
                GenerationSettings.DISTANCE_FROM_START_WEIGHT_FOR_BOSS_SCORE * Math.log(distFromClosestBoss));
    }

    public static float getItemRoomScore(int distFromStart, int distFromClosestBoss, int distanceFromClosestItem) {
        return (float) (GenerationSettings.DISTANCE_FROM_START_WEIGHT_FOR_ITEM_SCORE * Math.log(distFromStart) +
                GenerationSettings.DISTANCE_FROM_BOSS_WEIGHT_FOR_ITEM_SCORE * Math.log(distFromClosestBoss) +
                GenerationSettings.DISTANCE_FROM_ITEM_WEIGHT_FOR_ITEM_SCORE * Math.log(distanceFromClosestItem));
    }
}
