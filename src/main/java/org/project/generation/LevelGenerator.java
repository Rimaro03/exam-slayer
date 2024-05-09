package org.project.generation;

import java.util.*;

import lombok.extern.log4j.Log4j2;
import org.project.generation.wavecollapse.*;

@Log4j2
public class LevelGenerator {
    /**
     * This method uses the wave collapse algorithm to generate a new level.
     */
    public static Level build(int mapSize){
        log.info("Generating level with size: " + mapSize);
        if(mapSize < 3){ throw new IllegalArgumentException("Map size must be at least 3 : [mapSize=" + mapSize + "]"); }

        Tile[][] tiles = new Tile[mapSize][mapSize];
        Room[][] rooms = new Room[mapSize][mapSize];
        Random rand = new Random();
        Tile chosenTile;

        int startX = 1 + rand.nextInt(mapSize - 2);
        int startY = 1 + rand.nextInt(mapSize - 2);

        try{
            initializeTiles(tiles, mapSize);
            collapseBoundaries(tiles, mapSize);

            chosenTile = tiles[startY][startX];
            while(chosenTile != null) {
                chosenTile.collapse();
                propagateWave(tiles, chosenTile.getX(), chosenTile.getY());

                chosenTile = getTileWithLowestEntropy(tiles, rand);
            }
            floodFill(tiles, startX, startY, mapSize);

        } catch (GenerationFailedException e) { // there are low cases where the generation can fail
            log.warn("Generation failed [" + e.getMessage() + "], retrying...");
            return build(mapSize);
        }

        convertTilesToRooms(tiles, rooms, mapSize);
        return new Level(rooms);
    }

    /* -------------- WAVE COLLAPSE FUNCTIONS ---------------*/

    private static void initializeTiles(Tile[][] tiles, int mapSize){
        for(int x = 0; x < mapSize; x++){
            for(int y = 0; y < mapSize; y++){
                tiles[y][x] = new Tile(x, y);
            }
        }
    }
    private static void collapseBoundaries(Tile[][] tiles, int mapSize){
        State noDoors = new State((byte)0);
        for (int x = 0; x < mapSize; x++) {
            tiles[0][x].collapse(noDoors);
            tiles[mapSize - 1][x].collapse(noDoors);

            propagateWave(tiles, x, 0);
            propagateWave(tiles, x, mapSize - 1);
        }
        for (int y = 0; y < mapSize; y++) {
            tiles[y][0].collapse(noDoors);
            tiles[y][mapSize - 1].collapse(noDoors);

            propagateWave(tiles, 0, y);
            propagateWave(tiles, mapSize - 1, y);
        }
    }


    private static void floodFill(Tile[][] tiles, int x, int y, int mapSize) throws GenerationFailedException {
        Queue<Tile> queue = new LinkedList<>();
        Set<Tile> visited = new HashSet<>();
        queue.add(tiles[y][x]);

        // bfs to find all connected tiles
        while (!queue.isEmpty()){
            Tile tile = queue.poll();
            visited.add(tile);

            for (int direction = 0; direction < 4; direction++) {
                int otherX = Direction.x(tile.getX(), direction, 1);
                int otherY = Direction.y(tile.getY(), direction, 1);

                if(otherX < 0 || otherX >= mapSize || otherY < 0 || otherY >= mapSize) { continue; }
                if(!tile.look().checkBit(direction) || visited.contains(tiles[otherY][otherX])) { continue; }

                queue.add(tiles[otherY][otherX]);
            }
        }
        // check if the map has enough connected tiles
        if(visited.size() < mapSize * mapSize / 2){ throw new GenerationFailedException("Low room density, rooms counted = " + visited.size()); }

        // visited now contains all connected tiles to the starting tile
        // collapse all other tiles to an empty room (no doors)
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if(!visited.contains(tiles[i][j])) { tiles[i][j].collapse(State.noDoor); }
            }
        }
    }
    private static void convertTilesToRooms(Tile[][] tiles, Room[][] rooms, int mapSize) {
        for(int x = 0; x < mapSize; x++){
            for(int y = 0; y < mapSize; y++){
                rooms[y][x] = getRoomFromState(tiles[y][x].look());
            }
        }
    }
    private static Room getRoomFromState(State state){
        boolean up = state.checkBit(Direction.UP);
        boolean right = state.checkBit(Direction.RIGHT);
        boolean down = state.checkBit(Direction.DOWN);
        boolean left = state.checkBit(Direction.LEFT);

        return new Room(up, down, left, right);
    }
    private static Tile getTileWithLowestEntropy(Tile[][] tiles, Random rand){
        ArrayList<Tile> candidates = new ArrayList<>();
        int lowestEntropy = Integer.MAX_VALUE;
        for(Tile[] row : tiles){
            for(Tile tile : row){
                if(tile.isCollapsed()) { continue; }
                if(tile.entropy() < lowestEntropy){
                    lowestEntropy = tile.entropy();
                    candidates.clear();
                    candidates.add(tile);
                }
                else if(tile.entropy() == lowestEntropy){ candidates.add(tile); }
            }
        }
        if(candidates.isEmpty()){ return null; }
        return candidates.get(rand.nextInt(candidates.size()));
    }
    private static void propagateWave(Tile[][] tiles, int x, int y){
        Queue<TilePair> queue = new LinkedList<>();

        for (int direction = 0; direction < 4; direction++) {
            int otherX = Direction.x(x, direction, 1);
            int otherY = Direction.y(y, direction, 1);
            if(otherX < 0 || otherX >= tiles.length || otherY < 0 || otherY >= tiles.length) { continue; }
            queue.add(new TilePair(tiles[y][x], tiles[otherY][otherX], direction));
        }

        while (!queue.isEmpty()){
            TilePair pair = queue.poll();

            boolean hasChanged = false;
            int i = 0;
            while(i < pair.tileB.entropy()){
                State stateB = pair.tileB.getPossibleStates().get(i);
                boolean noMatch = true;
                for(State stateA : pair.tileA.getPossibleStates()){
                    if(State.canConnect(stateA, stateB, pair.directionFromAtoB)){
                        noMatch = false;
                        break;
                    }
                }
                if(noMatch){ pair.tileB.removeState(stateB); }
                else { i++; }
            }

            if(!hasChanged) { continue; }
            for (int direction = 0; direction < 4; direction++) {
                int otherX = Direction.x(pair.tileB.getX(), direction, 1);
                int otherY = Direction.y(pair.tileB.getY(), direction, 1);
                if(otherX < 0 || otherX >= tiles.length || otherY < 0 || otherY >= tiles.length) { continue; }
                queue.add(new TilePair(tiles[pair.tileB.getY()][pair.tileB.getX()], tiles[otherY][otherX], direction));
            }
        }
    }
}
