package org.project.generation.wavecollapse;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Random;

public class Tile {
    private final int DOOR_COUNT = 4;
    @Getter
    private final ArrayList<State> possibleStates;
    @Getter
    private boolean collapsed;
    @Getter
    private final int x, y;
    public Tile(int x, int y){
        this.x = x;
        this.y = y;
        collapsed = false;

        int startEntropy = 1 << DOOR_COUNT;
        possibleStates = new ArrayList<>(startEntropy); // 2^4 possible states
        for(byte i = 0; i < startEntropy; i++){
            possibleStates.add(new State(i));
        }
    }
    public void removeState(State state){ possibleStates.removeIf(new StateComparator(state)); }
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
    public State look() { return possibleStates.get(0); }
    public void collapse(State state){
        collapsed = true;
        possibleStates.clear();
        possibleStates.add(state);
    }
}

