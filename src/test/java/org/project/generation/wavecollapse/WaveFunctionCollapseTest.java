package org.project.generation.wavecollapse;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class WaveFunctionCollapseTest {

    @Test
    void createSuperRoomGrid() {

        SuperRoom[][] grid = WaveFunctionCollapse.createSuperRoomGrid(3);
        assertEquals(3, grid.length);
        assertEquals(3, grid[0].length);
        for (SuperRoom[] v : grid) {
            for (SuperRoom superRoom : v) {
                assertEquals(1 << 4, superRoom.entropy());
            }
        }
    }

    @Test
    void collapseBoundaries() {
        SuperRoom[][] grid = WaveFunctionCollapse.createSuperRoomGrid(3);

        WaveFunctionCollapse.collapseBoundaries(grid, 3);

        for (SuperRoom[] v : grid){
            for(SuperRoom superRoom : v){
                if(superRoom.getX() == 0 || superRoom.getY() == 0 || superRoom.getX() == 2 || superRoom.getY() == 2){
                    assertTrue(superRoom.isCollapsed());
                }
            }
        }
    }

    @Test
    void collapseWithRandom() {
        SuperRoom[][] grid = WaveFunctionCollapse.createSuperRoomGrid(3);
        WaveFunctionCollapse.collapse(grid, 1, 1, 3, new Random());

        assertTrue(grid[1][1].isCollapsed());

        boolean hasSomeoneChanged = false;
        for (SuperRoom[] v : grid){
            for(SuperRoom superRoom : v){
                if(superRoom == grid[1][1])
                    continue;

                if(superRoom.entropy() < (1 << 4)){
                    hasSomeoneChanged = true;
                    break;
                }
            }
        }

        assertTrue(hasSomeoneChanged);
    }

    @Test
    void collapseToState() {
        SuperRoom[][] grid = WaveFunctionCollapse.createSuperRoomGrid(3);
        WaveFunctionCollapse.collapse(grid, RoomState.ALL_DOORS, 1, 1, 3);

        assertTrue(grid[1][1].isCollapsed() && grid[1][1].getState() == RoomState.ALL_DOORS);

        boolean hasSomeoneChanged = false;
        for (SuperRoom[] v : grid){
            for(SuperRoom superRoom : v){
                if(superRoom == grid[1][1])
                    continue;

                if(superRoom.entropy() < (1 << 4)){
                    hasSomeoneChanged = true;
                    break;
                }
            }
        }

        assertTrue(hasSomeoneChanged);
    }

    @Test
    void propagateWave() {

        SuperRoom[][] grid = WaveFunctionCollapse.createSuperRoomGrid(3);

        grid[1][1].collapse(RoomState.ALL_DOORS);
        WaveFunctionCollapse.propagateWave(grid, 1, 1, 3);

        boolean hasSomeoneChanged = false;
        for (SuperRoom[] v : grid){
            for(SuperRoom superRoom : v){
                if(superRoom == grid[1][1])
                    continue;

                if(superRoom.entropy() < (1 << 4)){
                    hasSomeoneChanged = true;
                    break;
                }
            }
        }

        assertTrue(hasSomeoneChanged);
    }

    @Test
    void getSuperRoomWithLowestEntropy() {
    }

    @Test
    void floodFill() {
    }
}