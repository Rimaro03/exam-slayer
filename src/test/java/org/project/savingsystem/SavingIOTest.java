package org.project.savingsystem;

import org.junit.jupiter.api.Test;
import org.project.utils.Vec2Int;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SavingIOTest {

    @Test
    void setFloat() {
        SavingIO savingIO = new SavingIO("saved/saving_test.txt");
        savingIO.setFloat("testFloat", 1.1f);
    }
    @Test
    void setInt() {
        SavingIO savingIO = new SavingIO("saved/saving_test.txt");
        savingIO.setInt("testInt", 100);

    }

    @Test
    void setVec2IntList(){
        SavingIO savingIO = new SavingIO("saved/saving_test.txt");
        Vec2Int[] v = { new Vec2Int(1, 2), new Vec2Int(3, 4) };
        savingIO.setVec2IntList("testVec2IntList", Arrays.asList(v));
    }

    @Test
    void setVec2Int() {
        SavingIO savingIO = new SavingIO("saved/saving_test.txt");
        savingIO.setVec2Int("testVec2Int", new Vec2Int(101, 102));
    }

    /*
    @Test
    void getFloat() {
        SavingIO savingIO = new SavingIO("saved/saving_test.txt");
        assertEquals(1.1f, savingIO.getFloat("testFloat"));
    }

    @Test
    void getInt() {
        SavingIO savingIO = new SavingIO("saved/saving_test.txt");
        assertEquals(100, savingIO.getInt("testInt"));
    }

    @Test
    void getVec2Int() {
        SavingIO savingIO = new SavingIO("saved/saving_test.txt");
        assertEquals(new Vec2Int(101, 102), savingIO.getVec2Int("testVec2Int"));
    }

    @Test
    void getVec2IntList(){
        SavingIO savingIO = new SavingIO("saved/saving_test.txt");
        Vec2Int[] v = { new Vec2Int(1, 2), new Vec2Int(3, 4) };
        assertEquals(Arrays.asList(v), savingIO.getVec2IntList("testVec2IntList"));
    }*/

}