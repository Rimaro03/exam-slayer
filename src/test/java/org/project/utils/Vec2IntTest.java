package org.project.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Vec2IntTest {

    @Test
    void equalsSameVec2Int() {
        Vec2Int vec1 = new Vec2Int(1, 2);
        Vec2Int vec2 = new Vec2Int(1, 2);
        assertEquals(vec1, vec2);
    }

    @Test
    void equalsDifferentVec2Int() {
        Vec2Int vec1 = new Vec2Int(1, 2);
        Vec2Int vec2 = new Vec2Int(2, 1);
        assertNotEquals(vec1, vec2);
    }

    @Test
    void toStringVec2Int() {
        Vec2Int vec1 = new Vec2Int(1, 2);
        assertEquals("(1, 2)", vec1.toString());
    }

    @Test
    void toSaveStringVec2Int() {
        Vec2Int vec1 = new Vec2Int(1, 2);
        assertEquals("1,2", vec1.toSaveString());
    }

    @Test
    void fromSaveStringToObjectVec2Int() {
        Vec2Int vec1 = new Vec2Int();
        vec1 = vec1.fromSaveStringToObject("3,4");
        assertEquals(new Vec2Int(3, 4), vec1);
    }
}