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
        if(mapSize < 3){ throw new IllegalArgumentException("Map size must be at least 3 : [mapSize=" + mapSize + "]"); }

        Room[][] rooms = new Room[mapSize][mapSize];
        Tile[][] tiles = new Tile[mapSize][mapSize];
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
                propagateWave(tiles, chosenTile.getX(), chosenTile.getY(), mapSize);

                chosenTile = getTileWithLowestEntropy(tiles, rand);
            }
            floodFill(tiles, rooms, startX, startY, mapSize);

        } catch (GenerationFailedException e) { // there are low cases where the generation can fail
            log.warn("Generation failed [" + e.getMessage() + "], retrying...");
            return build(mapSize);
        }

        return new Level(rooms, startX, startY);
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

            propagateWave(tiles, x, 0, mapSize);
            propagateWave(tiles, x, mapSize - 1, mapSize);
        }
        for (int y = 0; y < mapSize; y++) {
            tiles[y][0].collapse(noDoors);
            tiles[y][mapSize - 1].collapse(noDoors);

            propagateWave(tiles, 0, y, mapSize);
            propagateWave(tiles, mapSize - 1, y, mapSize);
        }
    }


    /** Creates only the connected rooms to the tile at (x, y).*/
    private static void floodFill(Tile[][] tiles, Room[][] rooms, int x, int y, int mapSize) throws GenerationFailedException {
        Queue<Tile> tileQueue = new ArrayDeque<>();
        Set<Tile> visited = new HashSet<>();

        tileQueue.add(tiles[y][x]);

        //BFS to find all connected tiles and fill the room queue
        while(!tileQueue.isEmpty()){
            Tile current = tileQueue.poll();

            visited.add(current);

            for (int direction = 0; direction < 4; direction++) {
                int otherX = Direction.x(current.getX(), direction);
                int otherY = Direction.y(current.getY(), direction);
                if(otherX < 0 || otherX >= mapSize || otherY < 0 || otherY >= mapSize) { continue; }
                Tile other = tiles[otherY][otherX];
                if(!current.getState().checkBit(direction) || visited.contains(other)) { continue; }

                tileQueue.add(other);
            }
        }
        if(visited.size() < mapSize * mapSize / 4){ throw new GenerationFailedException("Not enough connected tiles : " + visited.size()); }
        else { log.info("Map generated with " + visited.size() + " rooms");}
        //create only one room for each connected tile
        for(Tile tile : visited){
            rooms[tile.getY()][tile.getX()] = new Room(tile.getX(), tile.getY());
        }

        //connect the rooms
        for (int i = 1; i < mapSize - 1; i++) {
            for (int j = 1; j < mapSize - 1; j++) {
                Room current = rooms[i][j];
                if(current == null) { continue; }
                for (int direction = 0; direction < 4; direction++) {
                    if(!tiles[i][j].getState().checkBit(direction)) { continue; }
                    current.setAdjacentRoom(direction, rooms[Direction.y(i, direction)][Direction.x(j, direction)]);
                }
            }
        }
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
    private static void propagateWave(Tile[][] tiles, int x, int y, int mapSize){
        Queue<TilePair> queue = new LinkedList<>();

        // insert all neighbors of the tile at (x, y) into the queue
        for (int direction = 0; direction < 4; direction++) {
            int otherX = Direction.x(x, direction);
            int otherY = Direction.y(y, direction);
            if(otherX < 0 || otherX >= mapSize || otherY < 0 || otherY >= mapSize) { continue; }
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

            // if the tileB has not changed the wave has interrupted his propagation
            // in that direction
            if(!hasChanged) { continue; }
            
            for (int direction = 0; direction < 4; direction++) {
                int otherX = Direction.x(pair.tileB.getX(), direction);
                int otherY = Direction.y(pair.tileB.getY(), direction);
                if(otherX < 0 || otherX >= tiles.length || otherY < 0 || otherY >= tiles.length) { continue; }
                queue.add(new TilePair(tiles[pair.tileB.getY()][pair.tileB.getX()], tiles[otherY][otherX], direction));
            }
        }
    }
}
