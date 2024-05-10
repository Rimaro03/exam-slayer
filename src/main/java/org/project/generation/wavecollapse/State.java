package org.project.generation.wavecollapse;

import lombok.Getter;

public class State {
    public static final State noDoor = new State((byte)0);
    @Getter
    private final byte value;
    public State(byte value){
        this.value = value;
    }
    public boolean checkBit(int i) { return (value & (1 << i)) != 0; }
    public boolean isNoDoors(){ return value == 0; }
    public static boolean canConnect(State stateA, State stateB, int directionFromAtoB){
        return stateA.checkBit(directionFromAtoB) == stateB.checkBit(Direction.opposite(directionFromAtoB));
    }

    @Override
    public String toString() {
        return "" + ((value & 0b1000) >> 3) + "" + ((value & 0b0100) >> 2) + "" + ((value & 0b0010) >> 1) + "" + (value & 0b0001);
    }
}
