package org.project.generation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Random;

@Getter
public class Tile {
    private static final int START_ENTROPY = 1 << 4; // 2^4 = 16 possible states
    private final ArrayList<State> possibleStates;
    private boolean collapsed;
    private final int x, y;

    public Tile(int x, int y){
        this.x = x;
        this.y = y;
        collapsed = false;

        possibleStates = new ArrayList<>(START_ENTROPY);
        for(byte i = 0; i < START_ENTROPY; i++){
            possibleStates.add(new State(i));
        }
    }
    public void removeState(State state){ possibleStates.remove(state); }
    public int entropy(){ return possibleStates.size(); }

    /**
     * Collapse the tile to a single state.
     * @return The state of the tile :
     * bit 0 = up door,
     * bit 1 = down door,
     * bit 2 = left door,
     * bit 3 = right door,
     * if has been returned -1 then the tile is in an invalid state.
     */
    public void collapse() throws GenerationFailedException {
        if(entropy() == 0){ throw new GenerationFailedException("Zero entropy!"); }

        int state = new Random().nextInt(possibleStates.size());
        collapsed = true;
        State chosen = possibleStates.get(state);
        possibleStates.clear();
        possibleStates.add(chosen);
    }
    /** Returns the state at the front of the list of possible states. */
    public State getState() {
        if(!collapsed) { throw new IllegalStateException("Tile not collapsed!"); }
        return possibleStates.get(0);
    }
    public void collapse(State state){
        collapsed = true;
        possibleStates.clear();
        possibleStates.add(state);
    }
}

