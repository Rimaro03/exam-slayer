package org.project.savingsystem;

import org.junit.jupiter.api.Test;
import org.project.utils.Vec2Int;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void setVec2IntList() {
        SavingIO savingIO = new SavingIO("saved/saving_test.txt");
        Vec2Int[] v = {new Vec2Int(1, 2), new Vec2Int(3, 4)};
        savingIO.setVec2IntList("testVec2IntList", Arrays.asList(v));
    }

    @Test
    void setStringList() {
        SavingIO savingIO = new SavingIO("saved/saving_test.txt");
        String[] s = {"test1", "test2", "test3"};
        savingIO.setStringList("testStringList", Arrays.asList(s));
    }

    @Test
    void setVec2Int() {
        SavingIO savingIO = new SavingIO("saved/saving_test.txt");
        savingIO.setVec2Int("testVec2Int", new Vec2Int(101, 102));
    }


    @Test
    void getFloat() {
        SavingIO savingIO = new SavingIO("saved/saving_test.txt");
        savingIO.setFloat("testFloat", 1.1f);
        assertEquals(1.1f, savingIO.getFloat("testFloat"));
    }

    @Test
    void getInt() {
        SavingIO savingIO = new SavingIO("saved/saving_test.txt");
        savingIO.setInt("testInt", 100);
        assertEquals(100, savingIO.getInt("testInt"));
    }

    @Test
    void getVec2Int() {
        SavingIO savingIO = new SavingIO("saved/saving_test.txt");
        savingIO.setVec2Int("testVec2Int", new Vec2Int(101, 102));
        assertEquals(new Vec2Int(101, 102), savingIO.getVec2Int("testVec2Int"));
    }

    @Test
    void getVec2IntList() {
        SavingIO savingIO = new SavingIO("saved/saving_test.txt");
        Vec2Int[] v = {new Vec2Int(1, 2), new Vec2Int(3, 4)};
        savingIO.setVec2IntList("testVec2IntList", Arrays.asList(v));
        assertEquals(Arrays.asList(v), savingIO.getVec2IntList("testVec2IntList"));
    }

    @Test
    void getStringList() {
        SavingIO savingIO = new SavingIO("saved/saving_test.txt");
        String[] s = {"test1", "test2", "test3"};
        savingIO.setStringList("testStringList", Arrays.asList(s));
        assertEquals(Arrays.asList(s), savingIO.getStringList("testStringList"));
    }

}