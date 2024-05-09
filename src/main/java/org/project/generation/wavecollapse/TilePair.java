package org.project.generation.wavecollapse;

public class TilePair {
    public final Tile tileA;
    public final Tile tileB;
    public final int directionFromAtoB;
    public TilePair(Tile tileA, Tile tileB, int directionFromAtoB){
        this.tileA = tileA;
        this.tileB = tileB;
        this.directionFromAtoB = directionFromAtoB;
    }
}
