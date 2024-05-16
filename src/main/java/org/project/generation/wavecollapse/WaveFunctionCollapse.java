package org.project.generation.wavecollapse;

import lombok.extern.log4j.Log4j2;
import org.project.generation.Room;

import java.util.*;


/**
 * TODO : add description
 */
@Log4j2
public class WaveFunctionCollapse {

    /** Initialize all the quantum rooms to max entropy quantum rooms (max number of possible states).
     * @param mapSize The size of the map.
     * @return A matrix of quantum rooms of the specified size.
     * */
    public static SuperRoom[][] createSuperRoomGrid(int mapSize){
        SuperRoom[][] superRooms = new SuperRoom[mapSize][mapSize];
        for(int x = 0; x < mapSize; x++){
            for(int y = 0; y < mapSize; y++){
                superRooms[y][x] = new SuperRoom(x, y);
            }
        }
        return superRooms;
    }
    /** Collapse the boundaries of the map to avoid the generation of rooms that are not connected to the map.
     * @param superRooms The matrix of super rooms.
     * @param mapSize The size of the map.
     *  */
    public static void collapseBoundaries(SuperRoom[][] superRooms, int mapSize){
        for (int x = 0; x < mapSize; x++) {
            collapse(superRooms, RoomState.NO_DOOR, x, 0, mapSize);
            collapse(superRooms, RoomState.NO_DOOR, x, mapSize - 1, mapSize);
        }
        for (int y = 0; y < mapSize; y++) {
            collapse(superRooms, RoomState.NO_DOOR, 0, y, mapSize);
            collapse(superRooms, RoomState.NO_DOOR, mapSize - 1, y, mapSize);
        }
    }

    /** Collapses the quantum room at (x, y) and propagate the changes to all neighbours quantum rooms.
     * @param superRooms The matrix of super rooms.
     * @param x The x coordinate of the quantum room.
     * @param y The y coordinate of the quantum room.
     * @param mapSize The size of the map.
     * @param rand The random number generator to use.
     * */
    public static void collapse(SuperRoom[][] superRooms, int x, int y, int mapSize, Random rand){
        superRooms[y][x].collapse(rand);
        propagateWave(superRooms, x, y, mapSize);
    }

    /** Collapses the quantum room at (x, y) to state and propagate the changes to all neighbours quantum rooms.
     * @param superRooms The matrix of super rooms.
     * @param state The state to collapse to.
     * @param x The x coordinate of the quantum room.
     * @param y The y coordinate of the quantum room.
     * @param mapSize The size of the map.
     * */
    public static void collapse(SuperRoom[][] superRooms, RoomState state, int x, int y, int mapSize){
        superRooms[y][x].collapse(state);
        propagateWave(superRooms, x, y, mapSize);
    }

    /** From the quantum room at (x, y) this method propagate (like a wave) the changes applied to (x, y) recursively,
     * updating all neighbours quantum room until the changes are not anymore influential and then the wave stops.
     */
    public static void propagateWave(SuperRoom[][] superRooms, int x, int y, int mapSize){
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
                RoomState stateB = superRoomB.getPossibleStates().get(i);
                boolean noMatch = true;
                for(RoomState stateA : superRoomA.getPossibleStates()){
                    if(RoomState.canConnect(stateA, stateB, direction)){
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
                propagateWave(superRooms, bX, bY, mapSize);
        }

    }

    /** @return The super room that has less possible states to collapse to.
     * if multiple super rooms have the same number of possible states, one of them is returned randomly.
     * if no super room is found, null is returned.
     */
    public static SuperRoom getQuantumRoomWithLowestEntropy(SuperRoom[][] superRooms, Random rand){
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

    /**
     * @param superRooms The matrix of super rooms.
     * @param x The x coordinate of the start room.
     * @param y The y coordinate of the start room.
     * @param mapSize The size of the map.
     * @param minRoomCount The minimum number of connected rooms required.
     * @return the head (start) of a graph of rooms.
     * thus all super rooms not connected to the start will be ignored.
     * @throws GenerationFailedException if the number of connected rooms is less
     * than minimum required number of room specified.
     */
    public static Room floodFill(SuperRoom[][] superRooms, int x, int y, int mapSize, int minRoomCount) {
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
}
