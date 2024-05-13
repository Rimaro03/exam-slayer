package org.project.generation.wavecollapse;

import lombok.extern.log4j.Log4j2;
import org.project.core.Debug;
import org.project.generation.Level;
import org.project.generation.Room;

import java.util.*;

@Log4j2
public class LevelGenerator {
    private final int mapSize;
    private final Random rand;
    public LevelGenerator(int mapSize, long seed){
        this.mapSize = mapSize;
        this.rand = new Random(seed);
    }
    /**
     * This method uses the wave collapse algorithm to generate a new level.
     */
    public Level build(){
        Room startRoom = generateRooms();
        Room[] bossRooms = getBossRooms(startRoom);

        System.out.println("Start room : " + startRoom.getX() + " " + startRoom.getY());

        for(Room bossRoom : bossRooms)
            System.out.println("Boss room : " + bossRoom.getX() + " " + bossRoom.getY());

        return new Level(startRoom);
    }
    private Room generateRooms(){
        if(mapSize < 5){ throw new IllegalArgumentException("Map size must be at least 5 : [mapSize=" + mapSize + "]"); }

        QuantumRoom[][] quantumRooms = new QuantumRoom[mapSize][mapSize];


        int startX = 2 + rand.nextInt(mapSize - 4);
        int startY = 2 + rand.nextInt(mapSize - 4);

        try{
            initializeQuantumRooms(quantumRooms);
            collapseBoundaries(quantumRooms);

            collapse(quantumRooms, State.ALL_DOORS, startX, startY);

            QuantumRoom chosenQuantumRoom = getQuantumRoomWithLowestEntropy(quantumRooms, rand);
            while(chosenQuantumRoom != null) {
                collapse(quantumRooms, chosenQuantumRoom.getX(), chosenQuantumRoom.getY());

                chosenQuantumRoom = getQuantumRoomWithLowestEntropy(quantumRooms, rand);
            }
            return floodFill(quantumRooms, startX, startY);

        } catch (GenerationFailedException e) { // there are low cases where the generation can fail
            log.warn("Generation failed [{}], retrying...", e.getMessage());
            return generateRooms();
        }
    }

    /** Returns the room that is the farthest from the start room using a bfs search. */
    private Room[] getBossRooms(Room startRoom){ // TODO : optimize this
        Stack<Room> queue = new Stack<>();
        Set<Room> visited = new HashSet<>();
        ArrayList<Room> allRooms = new ArrayList<>();

        queue.add(startRoom);
        while(!queue.isEmpty()){
            Room currentRoom = queue.pop();

            visited.add(currentRoom);
            allRooms.add(currentRoom);

            for(int direction = 0; direction < 4; direction++){
                Room adjacentRoom = currentRoom.getAdjacentRoom(direction);
                if(adjacentRoom == null || visited.contains(adjacentRoom)) { continue; }

                queue.push(adjacentRoom);
            }
        }

        Room[] bossRooms = new Room[Level.BOSS_ROOM_COUNT];
        for (int i = 0; i < Level.BOSS_ROOM_COUNT; i++) {
            int index = (i + 1) * (allRooms.size() - 1) / Level.BOSS_ROOM_COUNT;
            bossRooms[i] = allRooms.get(index);
        }

        return bossRooms;
    }

    /* -------------- WAVE COLLAPSE FUNCTIONS ---------------*/

    /** Initialize all the quantum rooms to max entropy quantum rooms (max number of possible states). */
    private void initializeQuantumRooms(QuantumRoom[][] quantumRooms){
        for(int x = 0; x < mapSize; x++){
            for(int y = 0; y < mapSize; y++){
                quantumRooms[y][x] = new QuantumRoom(x, y);
            }
        }
    }
    /** Collapse the boundaries of the map to avoid the generation of rooms that are not connected to the map. */
    private void collapseBoundaries(QuantumRoom[][] quantumRooms){
        for (int x = 0; x < mapSize; x++) {
            collapse(quantumRooms, State.NO_DOOR, x, 0);
            collapse(quantumRooms, State.NO_DOOR, x, mapSize - 1);
        }
        for (int y = 0; y < mapSize; y++) {
            collapse(quantumRooms, State.NO_DOOR, 0, y);
            collapse(quantumRooms, State.NO_DOOR, mapSize - 1, y);
        }
    }

    /** Returns the head (start) of a graph of rooms. */
    private Room floodFill(QuantumRoom[][] quantumRooms, int x, int y) throws GenerationFailedException {
        Room[][] roomsCache = new Room[mapSize][mapSize]; // room matrix cache

        Queue<QuantumRoom> quantumRoomQueue = new ArrayDeque<>();
        Set<QuantumRoom> visited = new HashSet<>();

        quantumRoomQueue.add(quantumRooms[y][x]);
        roomsCache[y][x] = new Room(x, y); // create start room

        //BFS to find all connected quantum rooms and fill the room queue
        while(!quantumRoomQueue.isEmpty()){
            QuantumRoom currentQuantumRoom = quantumRoomQueue.poll();
            Room currentRoom = roomsCache[currentQuantumRoom.getY()][currentQuantumRoom.getX()]; // supposed to be already created

            visited.add(currentQuantumRoom);

            for (int direction = 0; direction < 4; direction++) {
                int otherX = Direction.x(currentQuantumRoom.getX(), direction);
                int otherY = Direction.y(currentQuantumRoom.getY(), direction);
                if(otherX < 0 || otherX >= mapSize || otherY < 0 || otherY >= mapSize) { continue; }

                QuantumRoom nextQuantumRoom = quantumRooms[otherY][otherX];
                if(!currentQuantumRoom.getState().hasDoor(direction) || visited.contains(nextQuantumRoom)) { continue; }

                quantumRoomQueue.add(nextQuantumRoom);
                Room nextRoom = new Room(otherX, otherY);
                roomsCache[otherY][otherX] = nextRoom; // add room to cache

                currentRoom.setAdjacentRoom(direction, nextRoom);
                nextRoom.setAdjacentRoom(Direction.opposite(direction), currentRoom);
            }
        }
        if(visited.size() < mapSize * mapSize / 4){ throw new GenerationFailedException("Not enough connected quantum rooms : " + visited.size()); }
        else { log.info("Map generated with {} rooms", visited.size());}

        if(Debug.ENABLED){
            for (int i = 0; i < mapSize; i++) {
                for (int j = 0; j < mapSize; j++) {
                    System.out.print(roomsCache[i][j] == null ? "  " : roomsCache[i][j]);
                }
                System.out.println();
            }
        }

        return roomsCache[y][x];
    }

    /** Returns the quantum room that has less possible states to collapse to. */
    private QuantumRoom getQuantumRoomWithLowestEntropy(QuantumRoom[][] quantumRooms, Random rand){
        ArrayList<QuantumRoom> candidates = new ArrayList<>();
        int lowestEntropy = Integer.MAX_VALUE;
        for(QuantumRoom[] row : quantumRooms){
            for(QuantumRoom quantumRoom : row){
                if(quantumRoom.isCollapsed()) { continue; }
                if(quantumRoom.entropy() < lowestEntropy){
                    lowestEntropy = quantumRoom.entropy();
                    candidates.clear();
                    candidates.add(quantumRoom);
                }
                else if(quantumRoom.entropy() == lowestEntropy){ candidates.add(quantumRoom); }
            }
        }
        if(candidates.isEmpty()){ return null; }
        return candidates.get(rand.nextInt(candidates.size()));
    }


    /** Collapses the quantum room at (x, y) and propagate the changes to all neighbours quantum rooms. */
    private void collapse(QuantumRoom[][] quantumRooms, int x, int y){
        quantumRooms[y][x].collapse(rand);
        propagateWave(quantumRooms, x, y);
    }

    /** Collapses the quantum room at (x, y) to state and propagate the changes to all neighbours quantum rooms. */
    private void collapse(QuantumRoom[][] quantumRooms, State state, int x, int y){
        quantumRooms[y][x].collapse(state);
        propagateWave(quantumRooms, x, y);
    }

    /** From the quantum room at (x, y) this method propagate (like a wave) the changes applied to (x, y) recursively,
     * updating all neighbours quantum room until the changes are not anymore influential and then the wave stops.
     */
    private void propagateWave(QuantumRoom[][] quantumRooms, int x, int y){
        // A is the quantum room at x, y
        // B is the quantum room at x + dx, y + dy
        QuantumRoom quantumRoomA = quantumRooms[y][x];
        QuantumRoom quantumRoomB;
        for (int direction = 0; direction < 4; direction++)
        {
            int bX = Direction.x(x, direction);
            int bY = Direction.y(y, direction);
            if(bX < 0 || bX >= mapSize || bY < 0 || bY >= mapSize) { continue; }

            quantumRoomB = quantumRooms[bY][bX];

            boolean hasChanged = false;
            int i = 0;
            while(i < quantumRoomB.entropy()){
                State stateB = quantumRoomB.getPossibleStates().get(i);
                boolean noMatch = true;
                for(State stateA : quantumRoomA.getPossibleStates()){
                    if(State.canConnect(stateA, stateB, direction)){
                        noMatch = false;
                        break;
                    }
                }
                if(noMatch){ // if no state in A can connect to B, remove stateB from the possible states of B
                    quantumRoomB.removeState(stateB);
                    hasChanged = true;
                }
                else { i++; }
            }

            // if the quantum roomB has not changed the wave has interrupted his propagation
            // in this direction
            if(hasChanged)
                propagateWave(quantumRooms, bX, bY);
        }

    }
    
    /*private void propagateWave(QuantumRoom[][] quantumRooms, int x, int y){
        Queue<QuantumRoomPair> queue = new LinkedList<>();

        // insert all neighbors of the quantum room at (x, y) into the queue
        for (int direction = 0; direction < 4; direction++) {
            int otherX = Direction.x(x, direction);
            int otherY = Direction.y(y, direction);
            if(otherX < 0 || otherX >= mapSize || otherY < 0 || otherY >= mapSize) { continue; }
            queue.add(new QuantumRoomPair(quantumRooms[y][x], quantumRooms[otherY][otherX], direction));
        }

        while (!queue.isEmpty()){
            QuantumRoomPair pair = queue.poll();

            boolean hasChanged = false;
            int i = 0;
            while(i < pair.quantumRoomB.entropy()){
                State stateB = pair.quantumRoomB.getPossibleStates().get(i);
                boolean noMatch = true;
                for(State stateA : pair.quantumRoomA.getPossibleStates()){
                    if(State.canConnect(stateA, stateB, pair.directionFromAtoB)){
                        noMatch = false;
                        break;
                    }
                }
                if(noMatch){ // if no state in A can connect to B, remove stateB from the possible states of B
                    pair.quantumRoomB.removeState(stateB);
                    hasChanged = true;
                }
                else { i++; }
            }

            // if the quantum roomB has not changed the wave has interrupted his propagation
            // in that direction
            if(!hasChanged) { continue; }
            
            for (int direction = 0; direction < 4; direction++) {
                int otherX = Direction.x(pair.quantumRoomB.getX(), direction);
                int otherY = Direction.y(pair.quantumRoomB.getY(), direction);
                if(otherX < 0 || otherX >= quantumRooms.length || otherY < 0 || otherY >= quantumRooms.length) { continue; }
                queue.add(new QuantumRoomPair(quantumRooms[pair.quantumRoomB.getY()][pair.quantumRoomB.getX()], quantumRooms[otherY][otherX], direction));
            }
        }
    }*/
}
