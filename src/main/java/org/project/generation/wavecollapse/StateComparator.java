package org.project.generation.wavecollapse;

import java.util.function.Predicate;

public class StateComparator implements Predicate<State> {
    private final State stateCompared;
    public StateComparator(State state){ stateCompared = state; }

    @Override
    public boolean test(State state) {
        return stateCompared.getValue() == state.getValue();
    }
}
