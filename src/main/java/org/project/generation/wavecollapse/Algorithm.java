package org.project.generation.wavecollapse;

import org.project.generation.Room;
import java.util.*;

/**
 * Contains functionalities to work with the generation algorithm.
 */
public class Algorithm {
    private Algorithm() { }

    /** @return A map of rooms and their distance from the reference room.
     * @param startRoom The reference room.
     * */
    public static HashMap<Room, Integer> getDistanceMap(Room startRoom){
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

    /** @return A set of rooms that are connected to the specified room.
     * @param startRoom The reference room.
     * */
    public static HashSet<Room> getConnectedRooms(Room startRoom){
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

    /** @return The closest distance from all the listed rooms.
     * @param room The reference room.
     * @param rooms The list of rooms to compare the distance with.
     * */
    public static int distanceFromClosest(Room room, List<Room> rooms){
        if(rooms.isEmpty()) { return Integer.MAX_VALUE; }

        int minDistance = Integer.MAX_VALUE;
        for(Room bossRoom : rooms){
            minDistance = Math.min(minDistance, distance(room, bossRoom));
        }
        return minDistance;
    }
    /**
     * @return The distance between two rooms via a bfs search.
     * @throws RuntimeException if the rooms are not connected.
     * */
    public static int distance(Room a, Room b){
        Map<Room, Integer> distanceMap = Algorithm.getDistanceMap(a);
        if(!distanceMap.containsKey(b)){ throw new RuntimeException("Rooms are not connected"); }
        return distanceMap.get(b);
    }
}
