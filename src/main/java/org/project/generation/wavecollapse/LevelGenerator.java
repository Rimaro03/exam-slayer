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

    public LevelGenerator(int mapSize, long seed){
        this.mapSize = mapSize;
        this.rand = new Random(seed);

        minRoomCount = mapSize * mapSize / 2;

        log.info("Level generator seed set to {}", seed);

    }
    /**
     * This method uses the wave collapse algorithm to generate a new level.
     */
    public Level build(){

        Room startRoom = generateRooms();
        List<Room> bossRooms = getBossRooms(startRoom);
        setRoomsInitType(startRoom, bossRooms);

        if(Debug.ENABLED) {printMap(startRoom, bossRooms);}

        return new Level(startRoom, bossRooms);
    }

    /* ---------------- BUILD SUB METHODS -------------------- */
    private Room generateRooms(){
        if(mapSize < 5){ throw new IllegalArgumentException("Map size must be at least 5 : [mapSize=" + mapSize + "]"); }

        SuperRoom[][] superRooms = new SuperRoom[mapSize][mapSize];


        int startX = 2 + rand.nextInt(mapSize - 4);
        int startY = 2 + rand.nextInt(mapSize - 4);

        try{
            initializeQuantumRooms(superRooms);
            collapseBoundaries(superRooms);

            collapse(superRooms, State.ALL_DOORS, startX, startY);

            SuperRoom chosenSuperRoom = getQuantumRoomWithLowestEntropy(superRooms, rand);
            while(chosenSuperRoom != null) {
                collapse(superRooms, chosenSuperRoom.getX(), chosenSuperRoom.getY());

                chosenSuperRoom = getQuantumRoomWithLowestEntropy(superRooms, rand);
            }
            return floodFill(superRooms, startX, startY);

        } catch (GenerationFailedException e) { // there are low cases where the generation can fail
            log.warn("Generation failed [{}], retrying...", e.getMessage());
            return generateRooms();
        }
    }
    /** Returns the room that is the farthest from the start room using a bfs search. */
    private ArrayList<Room> getBossRooms(Room startRoom){
        ArrayList<Room> bossRooms = new ArrayList<>(Level.BOSS_ROOM_COUNT);
        Map<Room, Integer> distanceMap = getDistanceMap(startRoom);

        for (int i = 0; i < Level.BOSS_ROOM_COUNT; i++) {
            float maxScore = Float.NEGATIVE_INFINITY;
            Room chosenRoom = null;
            for(Map.Entry<Room, Integer> roomToDistance : distanceMap.entrySet()){
                float currentScore = getBossRoomScore(roomToDistance.getKey(), roomToDistance.getValue(), bossRooms);
                if(currentScore > maxScore){
                    maxScore = currentScore;
                    chosenRoom = roomToDistance.getKey();
                }
            }
            //printBossScoreMap(startRoom, bossRooms);
            bossRooms.add(chosenRoom);
        }

        return bossRooms;
    }
    private void setRoomsInitType(Room startRoom, List<Room> bossRooms){
        Set<Room> allRooms = getConnectedRooms(startRoom);
        for (Room room : allRooms) {
            if(room == startRoom) { room.setInitType(Room.InitType.Start); }
            else if (bossRooms.contains(room)) { room.setInitType(Room.InitType.Boss); }
            else{ room.setInitType(Room.InitType.Normal); }
        }
    }

    /* ------------------- ALGORITHMS ----------------------- */
    /** Returns the closest distance from all the boss rooms.  */
    private int distanceFromClosest(Room room, List<Room> bossRooms){
        if(bossRooms.isEmpty()) { return Integer.MAX_VALUE; }

        int minDistance = Integer.MAX_VALUE;
        for(Room bossRoom : bossRooms){
            minDistance = Math.min(minDistance, distance(room, bossRoom));
        }
        return minDistance;
    }
    /**
     * Returns the distance between two rooms via a bfs search.
     * @throws RuntimeException if the rooms are not connected.
     * */
    private int distance(Room a, Room b){
        Map<Room, Integer> distanceMap = getDistanceMap(a);
        if(!distanceMap.containsKey(b)){ throw new RuntimeException("Rooms are not connected"); }
        return distanceMap.get(b);
    }
    /** Returns the score of a room to be a boss room.
     * @return log(BossDist) * BossWeight + log(StartDist) * StartWeight
     * */
    private float getBossRoomScore(Room room, int distanceFromStart, List<Room> bossRooms){
        double x = (double) distanceFromClosest(room, bossRooms);
        double y = (double) distanceFromStart;

        return (float) (GenerationSettings.DISTANCE_FROM_BOSS_WEIGHT * Math.log(x) + GenerationSettings.DISTANCE_FROM_START_WEIGHT * Math.log(y));
    }
    /** Returns the head (start) of a graph of rooms. */
    private Room floodFill(SuperRoom[][] superRooms, int x, int y) throws GenerationFailedException {
        Queue<SuperRoom> superRoomQueue = new ArrayDeque<>();
        Set<SuperRoom> visited = new HashSet<>();

        superRoomQueue.add(superRooms[y][x]);
        //BFS to find all connected quantum rooms and fill the room queue
        while(!superRoomQueue.isEmpty()){
            SuperRoom currentSuperRoom = superRoomQueue.poll();

            visited.add(currentSuperRoom);

            for (int direction = 0; direction < 4; direction++) {
                int otherX = Direction.x(currentSuperRoom.getX(), direction);
                int otherY = Direction.y(currentSuperRoom.getY(), direction);
                if(otherX < 0 || otherX >= mapSize || otherY < 0 || otherY >= mapSize) { continue; }

                SuperRoom nextSuperRoom = superRooms[otherY][otherX];
                if(!currentSuperRoom.getState().hasDoor(direction) || visited.contains(nextSuperRoom)) { continue; }

                superRoomQueue.add(nextSuperRoom);
            }
        }
        if(visited.size() < minRoomCount){ throw new GenerationFailedException("Not enough connected quantum rooms : " + visited.size()); }
        else { log.info("Map generated with {} rooms", visited.size());}

        Room[][] roomsCache = new Room[mapSize][mapSize]; // room matrix cache
        for (SuperRoom qRoom : visited) { // initialize all the connected rooms
            roomsCache[qRoom.getY()][qRoom.getX()] = new Room(qRoom.getX(), qRoom.getY());
        }

        for(SuperRoom qRoom : visited){ // set all the connections
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

    /** Returns a map of the distance from the start room of all other rooms using Dijkstra's algorithm. */
    private static HashMap<Room, Integer> getDistanceMap(Room startRoom){
        Queue<RoomDistancePair> queue = new PriorityQueue<>();
        HashMap<Room, Integer> distanceMap = new HashMap<>();

        queue.add(new RoomDistancePair(startRoom, 0));
        while(!queue.isEmpty()){
            RoomDistancePair currentRoomDistancePair = queue.poll();

            for (int dir = 0; dir < 4; dir++) {
                Room adjacentRoom = currentRoomDistancePair.room.getAdjacentRoom(dir);
                if(adjacentRoom == null || distanceMap.containsKey(adjacentRoom)) { continue; }

                int newDistance = currentRoomDistancePair.distance + 1;
                queue.add(new RoomDistancePair(adjacentRoom, newDistance));
            }

            distanceMap.put(currentRoomDistancePair.room, currentRoomDistancePair.distance);
        }

        return distanceMap;
    }
    private static HashSet<Room> getConnectedRooms(Room startRoom){
        HashSet<Room> connectedRooms = new HashSet<>();
        Queue<Room> queue = new ArrayDeque<>();
        queue.add(startRoom);

        while(!queue.isEmpty()){
            Room currentRoom = queue.poll();
            connectedRooms.add(currentRoom);

            for (int dir = 0; dir < 4; dir++) {
                Room adjacentRoom = currentRoom.getAdjacentRoom(dir);
                if(adjacentRoom == null || connectedRooms.contains(adjacentRoom)) { continue; }

                queue.add(adjacentRoom);
            }
        }

        return connectedRooms;
    }
    /** Returns the quantum room that has less possible states to collapse to. */
    private SuperRoom getQuantumRoomWithLowestEntropy(SuperRoom[][] superRooms, Random rand){
        ArrayList<SuperRoom> candidates = new ArrayList<>();
        int lowestEntropy = Integer.MAX_VALUE;
        for(SuperRoom[] row : superRooms){
            for(SuperRoom superRoom : row){
                if(superRoom.isCollapsed()) { continue; }
                if(superRoom.entropy() < lowestEntropy){
                    lowestEntropy = superRoom.entropy();
                    candidates.clear();
                    candidates.add(superRoom);
                }
                else if(superRoom.entropy() == lowestEntropy){ candidates.add(superRoom); }
            }
        }
        if(candidates.isEmpty()){ return null; }
        return candidates.get(rand.nextInt(candidates.size()));
    }

    /* ------------------------ DEBUG ------------------------- */
    /** Print the map to the console. (Debug) */
    private void printMap(Room startRoom, List<Room> bossRooms){
        Room[][] rooms = new Room[mapSize][mapSize];
        Queue<Room> queue = new ArrayDeque<>();
        queue.add(startRoom);
        while(!queue.isEmpty()){
            Room currentRoom = queue.poll();

            rooms[currentRoom.getY()][currentRoom.getX()] = currentRoom;
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
                    if(rooms[i][j] == bossRoom){
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
    private void printDistanceMap(Room startRoom){
        Map<Room, Integer> distanceMap = getDistanceMap(startRoom);

        String[][] strMat = new String[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                strMat[i][j] = "  ";
            }
        }
        for(Map.Entry<Room, Integer> entry : distanceMap.entrySet()){
            strMat[entry.getKey().getY()][entry.getKey().getX()] = entry.getValue().toString();
            while (strMat[entry.getKey().getY()][entry.getKey().getX()].length() < 2) {
                strMat[entry.getKey().getY()][entry.getKey().getX()] += " ";
            }
        }

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                System.out.print(strMat[i][j]);
            }
            System.out.println();
        }
    }
    private void printBossScoreMap(Room startRoom, List<Room> bossRooms){
        Map<Room, Integer> distanceMap = getDistanceMap(startRoom);

        String[][] strMat = new String[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                strMat[i][j] = "          ";
            }
        }
        for(Map.Entry<Room, Integer> entry : distanceMap.entrySet()){
            strMat[entry.getKey().getY()][entry.getKey().getX()] = getBossRoomScore(entry.getKey(), entry.getValue(), bossRooms) + "";
            while (strMat[entry.getKey().getY()][entry.getKey().getX()].length() < 10) {
                strMat[entry.getKey().getY()][entry.getKey().getX()] += " ";
            }
        }

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                System.out.print(strMat[i][j]);
            }
            System.out.println();
        }
    }

    /* -------------- WAVE COLLAPSE FUNCTIONS ---------------*/

    /** Initialize all the quantum rooms to max entropy quantum rooms (max number of possible states). */
    private void initializeQuantumRooms(SuperRoom[][] superRooms){
        for(int x = 0; x < mapSize; x++){
            for(int y = 0; y < mapSize; y++){
                superRooms[y][x] = new SuperRoom(x, y);
            }
        }
    }
    /** Collapse the boundaries of the map to avoid the generation of rooms that are not connected to the map. */
    private void collapseBoundaries(SuperRoom[][] superRooms){
        for (int x = 0; x < mapSize; x++) {
            collapse(superRooms, State.NO_DOOR, x, 0);
            collapse(superRooms, State.NO_DOOR, x, mapSize - 1);
        }
        for (int y = 0; y < mapSize; y++) {
            collapse(superRooms, State.NO_DOOR, 0, y);
            collapse(superRooms, State.NO_DOOR, mapSize - 1, y);
        }
    }

    /** Collapses the quantum room at (x, y) and propagate the changes to all neighbours quantum rooms. */
    private void collapse(SuperRoom[][] superRooms, int x, int y){
        superRooms[y][x].collapse(rand);
        propagateWave(superRooms, x, y);
    }

    /** Collapses the quantum room at (x, y) to state and propagate the changes to all neighbours quantum rooms. */
    private void collapse(SuperRoom[][] superRooms, State state, int x, int y){
        superRooms[y][x].collapse(state);
        propagateWave(superRooms, x, y);
    }

    /** From the quantum room at (x, y) this method propagate (like a wave) the changes applied to (x, y) recursively,
     * updating all neighbours quantum room until the changes are not anymore influential and then the wave stops.
     */
    private void propagateWave(SuperRoom[][] superRooms, int x, int y){
        // A is the quantum room at x, y
        // B is the quantum room at x + dx, y + dy
        SuperRoom superRoomA = superRooms[y][x];
        SuperRoom superRoomB;
        for (int direction = 0; direction < 4; direction++)
        {
            int bX = Direction.x(x, direction);
            int bY = Direction.y(y, direction);
            if(bX < 0 || bX >= mapSize || bY < 0 || bY >= mapSize) { continue; }

            superRoomB = superRooms[bY][bX];

            boolean hasChanged = false;
            int i = 0;
            while(i < superRoomB.entropy()){
                State stateB = superRoomB.getPossibleStates().get(i);
                boolean noMatch = true;
                for(State stateA : superRoomA.getPossibleStates()){
                    if(State.canConnect(stateA, stateB, direction)){
                        noMatch = false;
                        break;
                    }
                }
                if(noMatch){ // if no state in A can connect to B, remove stateB from the possible states of B
                    superRoomB.removeState(stateB);
                    hasChanged = true;
                }
                else { i++; }
            }

            // if the quantum roomB has not changed the wave has interrupted his propagation
            // in this direction
            if(hasChanged)
                propagateWave(superRooms, bX, bY);
        }

    }
}
