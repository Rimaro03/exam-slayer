package org.project.componentsystem.components.enemies;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnemyInfoTest {

    @Test
    void getNameReturnsCorrectName() {
        assertEquals("Dx", EnemyInfo.getName(0));
        assertEquals("Dy", EnemyInfo.getName(1));
        assertEquals("Integral", EnemyInfo.getName(2));
        assertEquals("DoubleIntegral", EnemyInfo.getName(3));
        assertEquals("Unknown", EnemyInfo.getName(5));
    }

    @Test
    void getTexturePathReturnsCorrectPath() {
        assertEquals("resources/textures/characters/dx.png", EnemyInfo.getTexturePath(0));
        assertEquals("resources/textures/characters/dy.png", EnemyInfo.getTexturePath(1));
        assertEquals("resources/textures/characters/integral.png", EnemyInfo.getTexturePath(2));
        assertEquals("resources/textures/characters/double_integral.png", EnemyInfo.getTexturePath(3));
        assertEquals("", EnemyInfo.getTexturePath(5));
    }

    @Test
    void getTextureWidthReturnsCorrectWidth() {
        assertEquals(24, EnemyInfo.getTextureWidth(0));
        assertEquals(24, EnemyInfo.getTextureWidth(1));
        assertEquals(16, EnemyInfo.getTextureWidth(2));
        assertEquals(24, EnemyInfo.getTextureWidth(3));
        assertEquals(-1, EnemyInfo.getTextureWidth(5));
    }

    @Test
    void getTextureHeightReturnsCorrectHeight() {
        assertEquals(32, EnemyInfo.getTextureHeight(0));
        assertEquals(32, EnemyInfo.getTextureHeight(1));
        assertEquals(32, EnemyInfo.getTextureHeight(2));
        assertEquals(32, EnemyInfo.getTextureHeight(3));
        assertEquals(-1, EnemyInfo.getTextureHeight(5));
    }

    @Test
    void getRandomEnemyIdReturnsValidId() {
        Random rand = new Random();
        int id = EnemyInfo.getRandomEnemyId(rand);
        assertTrue(id >= 0 && id < 4);
    }
}