package org.project.generation.wavecollapse;

import lombok.extern.log4j.Log4j2;
import org.project.core.Debug;
import org.project.generation.Level;
import org.project.generation.Room;

import java.util.*;

/*

Generation method:
1. Generate the map using the wave collapse algorithm
    1. Initialize a matrix [mapSize x mapSize] of quantum rooms that initially can be all possible states at once.
    2. Collapse the boundaries of the map to void rooms to avoid the map to generate doors that connect to nowhere.
    3. Choose a start room and collapse it to a 4-door room
    4. iterate until all quantum rooms are collapsed:
        1. Choose the quantum room with the lowest entropy (the quantum room with the lowest number of possible states)
        2. Collapse the quantum room to a random state
        3. Propagate the changes to all neighbours quantum rooms
    5. Flood fill the map from the start room to get the head of the graph of connected rooms (all quantum rooms get converted to actual rooms)
2. Get the boss rooms
    1. Get the room that is the farthest from the start room using a bfs search
    2. Repeat to get the other boss rooms and consider the distance between the boss rooms
*/

@Log4j2
public class LevelGenerator {
    private final int mapSize;
    private final Random rand;
    private final int minRoomCount;
    private final int minBossRoomDistanceFromStart;
    private final int minBossRoomDistanceFromEachOther;

    public LevelGenerator(int mapSize, long seed){
        this.mapSize = mapSize;
        this.rand = new Random(seed);

        minRoomCount = mapSize * mapSize / 2;
        minBossRoomDistanceFromStart = mapSize / 2;
        minBossRoomDistanceFromEachOther = mapSize;

        log.info("Level generator seed set to {}", seed);

    }
    /**
     * This method uses the wave collapse algorithm to generate a new level.
     */
    public Level build(){

        Room startRoom = generateRooms();
        Room[] bossRooms = getBossRooms(startRoom);

        if(Debug.ENABLED) {
            printMap(startRoom, bossRooms);
        }

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
    private Room[] getBossRooms(Room startRoom){
        Room[] bossRooms = new Room[Level.BOSS_ROOM_COUNT];

        Queue<Room> queue = new ArrayDeque<>();
        Map<Room, Integer> visited = new HashMap<>(); // visited keeps track of the distance from the start room

        queue.add(startRoom);
        visited.put(startRoom, 0);

        while(!queue.isEmpty()){
            Room currentRoom = queue.poll();

            for(int direction = 0; direction < 4; direction++){
                Room adjacentRoom = currentRoom.getAdjacentRoom(direction);
                if(adjacentRoom == null) { continue; }

                int distance = visited.get(currentRoom) + 1;

                if(!visited.containsKey(adjacentRoom) || visited.get(adjacentRoom) > distance) {
                    visited.put(adjacentRoom, distance);
                    queue.add(adjacentRoom);
                }

            }
        }

        for (int i = 0; i < Level.BOSS_ROOM_COUNT; i++) {
            int maxDistance = 0;
            for(Map.Entry<Room, Integer> roomToDistance : visited.entrySet()){
                if(roomToDistance.getValue() > maxDistance){
                    boolean isDistantEnoughFromBossRooms = true; // check if the room is distant enough from the other boss rooms
                    for (int j = 0; j < i; j++) {
                        if(distance(bossRooms[j], roomToDistance.getKey()) < minBossRoomDistanceFromEachOther){
                            isDistantEnoughFromBossRooms = false;
                            break;
                        }
                    }
                    if(!isDistantEnoughFromBossRooms) { continue; }

                    maxDistance = roomToDistance.getValue();
                    bossRooms[i] = roomToDistance.getKey();
                }
            }
        }

        return bossRooms;
    }

    /**
     * Returns the distance between two rooms via a bfs search
     * @throws RuntimeException if the rooms are not connected
     * */
    private int distance(Room a, Room b){
        Queue<Room> queue = new ArrayDeque<>();
        Map<Room, Integer> visited = new HashMap<>();

        queue.add(a);
        visited.put(a, 0);
        while(!queue.isEmpty()){
            Room currentRoom = queue.poll();
            if(currentRoom == b) { return visited.get(currentRoom); }

            for(int direction = 0; direction < 4; direction++){
                Room adjacentRoom = currentRoom.getAdjacentRoom(direction);
                if(adjacentRoom == null) { continue; }

                int distance = visited.get(currentRoom) + 1;

                if(!visited.containsKey(adjacentRoom) || visited.get(adjacentRoom) > distance) {
                    visited.put(adjacentRoom, distance);
                    queue.add(adjacentRoom);
                }
            }
        }
        throw new RuntimeException("Rooms are not connected");
    }
    private void printMap(Room startRoom, Room[] bossRooms){
        Room[][] rooms = new Room[mapSize][mapSize];
        Queue<Room> queue = new ArrayDeque<>();
        queue.add(startRoom);
        while(!queue.isEmpty()){
            Room currentRoom = queue.poll();

            rooms[currentRoom.getY()][currentRoom.getX()] = currentRoom;
            //System.out.println("Added : " + currentRoom.getX() + " " + currentRoom.getY());

            for(int direction = 0; direction < 4; direction++){
                Room adjacentRoom = currentRoom.getAdjacentRoom(direction);
                if(adjacentRoom == null || rooms[adjacentRoom.getY()][adjacentRoom.getX()] != null) { continue; }

                queue.add(adjacentRoom);
            }
        }

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if(rooms[i][j] == null) {
                    System.out.print("  ");
                    continue;
                }

                boolean continue_ = false;
                for(Room bossRoom : bossRooms){
                    if(/*rooms[i][j].getX() == bossRoom.getX() && rooms[i][j].getY() == bossRoom.getY()*/ rooms[i][j] == bossRoom){
                        System.out.print("B ");
                        continue_ = true;
                        break;
                    }
                }
                if(continue_) { continue; }

                if(rooms[i][j] == startRoom)
                    System.out.print("S ");
                else
                    System.out.print(rooms[i][j]);
            }
            System.out.println();
        }
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
        Queue<QuantumRoom> quantumRoomQueue = new ArrayDeque<>();
        Set<QuantumRoom> visited = new HashSet<>();

        quantumRoomQueue.add(quantumRooms[y][x]);
        //BFS to find all connected quantum rooms and fill the room queue
        while(!quantumRoomQueue.isEmpty()){
            QuantumRoom currentQuantumRoom = quantumRoomQueue.poll();

            visited.add(currentQuantumRoom);

            for (int direction = 0; direction < 4; direction++) {
                int otherX = Direction.x(currentQuantumRoom.getX(), direction);
                int otherY = Direction.y(currentQuantumRoom.getY(), direction);
                if(otherX < 0 || otherX >= mapSize || otherY < 0 || otherY >= mapSize) { continue; }

                QuantumRoom nextQuantumRoom = quantumRooms[otherY][otherX];
                if(!currentQuantumRoom.getState().hasDoor(direction) || visited.contains(nextQuantumRoom)) { continue; }

                quantumRoomQueue.add(nextQuantumRoom);
            }
        }
        if(visited.size() < minRoomCount){ throw new GenerationFailedException("Not enough connected quantum rooms : " + visited.size()); }
        else { log.info("Map generated with {} rooms", visited.size());}

        Room[][] roomsCache = new Room[mapSize][mapSize]; // room matrix cache
        for (QuantumRoom qRoom : visited) { // initialize all the connected rooms
            roomsCache[qRoom.getY()][qRoom.getX()] = new Room(qRoom.getX(), qRoom.getY());
        }

        for(QuantumRoom qRoom : visited){ // set all the connections
            Room current = roomsCache[qRoom.getY()][qRoom.getX()];
            for (int direction = 0; direction < 4; direction++) {
                if(!qRoom.getState().hasDoor(direction)) { continue; }

                int adjacentX = Direction.x(qRoom.getX(), direction);
                int adjacentY = Direction.y(qRoom.getY(), direction);
                current.setAdjacentRoom(direction, roomsCache[adjacentY][adjacentX]);
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
}
