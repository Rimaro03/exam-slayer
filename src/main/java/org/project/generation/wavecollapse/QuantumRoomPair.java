package org.project.generation.wavecollapse;

public class QuantumRoomPair {
    public final QuantumRoom quantumRoomA;
    public final QuantumRoom quantumRoomB;
    public final int directionFromAtoB;
    public QuantumRoomPair(QuantumRoom quantumRoomA, QuantumRoom quantumRoomB, int directionFromAtoB){
        this.quantumRoomA = quantumRoomA;
        this.quantumRoomB = quantumRoomB;
        this.directionFromAtoB = directionFromAtoB;
    }
}
