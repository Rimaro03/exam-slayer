package org.project.generation.wavecollapse;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Random;

/**
 * A SuperRoom is a Room tha cam be in multiple states at once, every state is a possible configuration of doors.
 */
@Getter
public class SuperRoom {
    /** The maximum number of possible states : 4 doors means 4x4 possible states. */
    private static final int START_ENTROPY = 1 << 4;
    private final ArrayList<RoomState> possibleStates;
    private boolean collapsed;
    private final int x, y;

    public SuperRoom(int x, int y){
        this.x = x;
        this.y = y;
        collapsed = false;

        possibleStates = new ArrayList<>(START_ENTROPY);
        for(byte i = 0; i < START_ENTROPY; i++){
            possibleStates.add(new RoomState(i));
        }
    }
    /** Removes state from the list of possible states. */
    public void removeState(RoomState state){ possibleStates.remove(state); }
    /** Returns the number of possible states the tile can be in. */
    public int entropy(){ return possibleStates.size(); }

    /**
     * Collapse the tile to a fixed state.
     * The state is chosen randomly based on the weights in GenerationSettings.
     * (if a state has a higher weight, it is more likely to be chosen)
     * @param rand The random number generator to use.
     * @throws GenerationFailedException if the entropy is zero.
     */
    public void collapse(Random rand) throws GenerationFailedException {
        if(entropy() == 0){ throw new GenerationFailedException("Zero entropy!"); }

        ArrayList<RoomState> weightedStates = new ArrayList<>();
        for(RoomState state : possibleStates) {
            for (int i = 0; i < GenerationSettings.WEIGHTS[state.getValue()]; i++)
                weightedStates.add(state);
        }
        int state = rand.nextInt(weightedStates.size());
        collapse(weightedStates.get(state));
    }
    /** Collapse the tile to a fixed state.
     * @param state The state to collapse to.
     * @throws IllegalArgumentException if the state is not possible.
     */
    public void collapse(RoomState state){
        if(!possibleStates.contains(state)) { throw new IllegalArgumentException("State not possible!"); }
        collapsed = true;
        possibleStates.clear();
        possibleStates.add(state);
    }

    /**
     * @return the state at the front of the list of possible states.
     * @throws IllegalStateException if the tile is not collapsed.
     * */
    public RoomState getState() {
        if(!collapsed) { throw new IllegalStateException("Tile not collapsed!"); }
        return possibleStates.get(0);
    }

}

