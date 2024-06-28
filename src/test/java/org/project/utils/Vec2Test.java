package org.project.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Vec2Test {

    @Test
    void addVectors() {
        Vec2 vec1 = new Vec2(1, 2);
        Vec2 vec2 = new Vec2(3, 4);
        Vec2 result = vec1.add(vec2);
        assertEquals(new Vec2(4, 6), result);
    }

    @Test
    void subtractVectors() {
        Vec2 vec1 = new Vec2(5, 6);
        Vec2 vec2 = new Vec2(3, 2);
        Vec2 result = vec1.subtract(vec2);
        assertEquals(new Vec2(2, 4), result);
    }

    @Test
    void multiplyVectorByScalar() {
        Vec2 vec1 = new Vec2(2, 3);
        Vec2 result = vec1.multiply(2);
        assertEquals(new Vec2(4, 6), result);
    }

    @Test
    void calculateMagnitude() {
        Vec2 vec1 = new Vec2(3, 4);
        float result = vec1.magnitude();
        assertEquals(5, result);
    }

    @Test
    void normalizeVector() {
        Vec2 vec1 = new Vec2(3, 4);
        Vec2 result = vec1.normalized();
        assertEquals(new Vec2(0.6f, 0.8f), result);
    }

    @Test
    void normalizeZeroVector() {
        Vec2 vec1 = new Vec2(0, 0);
        Vec2 result = vec1.normalized();
        assertEquals(new Vec2(0, 0), result);
    }

    @Test
    void equalsSameVectors() {
        Vec2 vec1 = new Vec2(1, 2);
        Vec2 vec2 = new Vec2(1, 2);
        assertEquals(vec1, vec2);
    }

    @Test
    void equalsDifferentVectors() {
        Vec2 vec1 = new Vec2(1, 2);
        Vec2 vec2 = new Vec2(2, 1);
        assertNotEquals(vec1, vec2);
    }
}