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
        List<Room> itemRooms = getItemRooms(startRoom, bossRooms);
        setRoomsInitType(startRoom, bossRooms, itemRooms);

        if(Debug.ENABLED) {printMap(startRoom, bossRooms, itemRooms);}

        return new Level(startRoom, bossRooms);
    }

    /* ---------------- BUILD SUB METHODS -------------------- */
    private Room generateRooms(){
        if(mapSize < 5){ throw new IllegalArgumentException("Map size must be at least 5 : [mapSize=" + mapSize + "]"); }

        int startX = 2 + rand.nextInt(mapSize - 4);
        int startY = 2 + rand.nextInt(mapSize - 4);

        try{
            SuperRoom[][] superRooms = WaveFunctionCollapse.createSuperRoomGrid(mapSize);
            WaveFunctionCollapse.collapseBoundaries(superRooms, mapSize);

            WaveFunctionCollapse.collapse(superRooms, RoomState.ALL_DOORS, startX, startY, mapSize);

            SuperRoom chosenSuperRoom = WaveFunctionCollapse.getSuperRoomWithLowestEntropy(superRooms, rand);
            while(chosenSuperRoom != null) {
                WaveFunctionCollapse.collapse(superRooms, chosenSuperRoom.getX(), chosenSuperRoom.getY(), mapSize, rand);

                chosenSuperRoom = WaveFunctionCollapse.getSuperRoomWithLowestEntropy(superRooms, rand);
            }
            return WaveFunctionCollapse.floodFill(superRooms, startX, startY, mapSize, minRoomCount);

        } catch (GenerationFailedException e) { // there are low cases where the generation can fail
            log.warn("Generation failed [{}], retrying...", e.getMessage());
            return generateRooms();
        }
    }
    /** @return a list of rooms that have been chosen as the best candidates to be a boss room.  */
    private ArrayList<Room> getBossRooms(Room startRoom){
        ArrayList<Room> bossRooms = new ArrayList<>(GenerationSettings.BOSS_ROOM_COUNT);
        Map<Room, Integer> distanceMap = Algorithm.getDistanceMap(startRoom);

        for (int i = 0; i < GenerationSettings.BOSS_ROOM_COUNT; i++) {
            float maxScore = Float.NEGATIVE_INFINITY;
            Room chosenRoom = null;
            for(Map.Entry<Room, Integer> roomToDistance : distanceMap.entrySet()){
                int distFromClosestBoss = Algorithm.distanceFromClosest(roomToDistance.getKey(), bossRooms);
                float currentScore = GenerationSettings.getBossRoomScore(roomToDistance.getValue(), distFromClosestBoss);
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

    private ArrayList<Room> getItemRooms(Room startRoom, List<Room> bossRoom){
        ArrayList<Room> itemRooms = new ArrayList<>(GenerationSettings.ITEM_ROOM_COUNT);
        Map<Room, Integer> distanceMap = Algorithm.getDistanceMap(startRoom);

        for (int i = 0; i < GenerationSettings.ITEM_ROOM_COUNT; i++) {
            float maxScore = Float.NEGATIVE_INFINITY;
            Room chosenRoom = null;
            for(Map.Entry<Room, Integer> roomToDistance : distanceMap.entrySet()){
                int distFromClosestBoss = Algorithm.distanceFromClosest(roomToDistance.getKey(), bossRoom);
                int distFromClosestItem = Algorithm.distanceFromClosest(roomToDistance.getKey(), itemRooms);
                float currentScore = GenerationSettings.getItemRoomScore(roomToDistance.getValue(), distFromClosestBoss, distFromClosestItem);
                if(currentScore > maxScore){
                    maxScore = currentScore;
                    chosenRoom = roomToDistance.getKey();
                }
            }
            itemRooms.add(chosenRoom);
        }

        return itemRooms;
    }
    /** Set the type of initialization of the rooms. {@link Room.InitType} */
    private void setRoomsInitType(Room startRoom, List<Room> bossRooms, List<Room> itemRooms){
        Set<Room> allRooms = Algorithm.getConnectedRooms(startRoom);
        for (Room room : allRooms) {
            if(room == startRoom) { room.setInitType(Room.InitType.Start); }
            else if (bossRooms.contains(room)) { room.setInitType(Room.InitType.Boss); }
            else if (itemRooms.contains(room)) { room.setInitType(Room.InitType.Item); }
            else{ room.setInitType(Room.InitType.Normal); }
        }
    }

    /* ------------------------ DEBUG ------------------------- */

    /** Prints the map to the console. (Debug)
     * @param startRoom The start room of the map.
     * @param bossRooms The list of boss rooms.
     * */
    private void printMap(Room startRoom, List<Room> bossRooms, List<Room> itemRooms){
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

                if(bossRooms.contains(rooms[i][j]))
                    System.out.print("B ");
                else if(itemRooms.contains(rooms[i][j]))
                    System.out.print("I ");
                else if(rooms[i][j] == startRoom)
                    System.out.print("S ");
                else
                    System.out.print(rooms[i][j]);
            }
            System.out.println();
        }
    }

    /** Prints a map of distances from the start room to the console. (Debug)
     * @param startRoom The start room of the map.
     */
    private void printDistanceMap(Room startRoom){
        Map<Room, Integer> distanceMap = Algorithm.getDistanceMap(startRoom);

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
    /** Prints the boss score map to the console. (Debug)
     * @param startRoom The start room of the map.
     * @param bossRooms The list of boss rooms.
     */
    private void printBossScoreMap(Room startRoom, List<Room> bossRooms){
        Map<Room, Integer> distanceMap = Algorithm.getDistanceMap(startRoom);

        String[][] strMat = new String[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                strMat[i][j] = "          ";
            }
        }
        for(Map.Entry<Room, Integer> entry : distanceMap.entrySet()){
            Room cur = entry.getKey();
            int distFromStart = entry.getValue();
            int distFromClosestBoss = Algorithm.distanceFromClosest(cur, bossRooms);

            strMat[cur.getY()][cur.getX()] = GenerationSettings.getBossRoomScore(distFromStart, distFromClosestBoss) + "";
            while (strMat[cur.getY()][cur.getX()].length() < 10) {
                strMat[cur.getY()][cur.getX()] += " ";
            }
        }

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                System.out.print(strMat[i][j]);
            }
            System.out.println();
        }
    }
}
