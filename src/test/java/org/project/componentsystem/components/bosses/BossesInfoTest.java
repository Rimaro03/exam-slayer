package org.project.componentsystem.components.bosses;

import org.junit.jupiter.api.Test;
import org.project.componentsystem.components.weapons.WeaponType;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BossesInfoTest {

    @Test
    void getNameReturnsCorrectName() {
        assertEquals("Atom", BossesInfo.getName(0));
        assertEquals("Integral", BossesInfo.getName(1));
        assertEquals("Matrix", BossesInfo.getName(2));
        assertEquals("Unknown", BossesInfo.getName(3));
    }

    @Test
    void getTexturePathReturnsCorrectPath() {
        assertEquals("resources/textures/bosses/atom.png", BossesInfo.getTexturePath(0));
        assertEquals("resources/textures/bosses/integrals.png", BossesInfo.getTexturePath(1));
        assertEquals("resources/textures/bosses/matrix.png", BossesInfo.getTexturePath(2));
        assertEquals("", BossesInfo.getTexturePath(3));
    }

    @Test
    void getSpeedReturnsCorrectSpeed() {
        assertEquals(2, BossesInfo.getSpeed(0));
        assertEquals(1, BossesInfo.getSpeed(1));
        assertEquals(1.5f, BossesInfo.getSpeed(2));
        assertEquals(0, BossesInfo.getSpeed(3));
    }

    @Test
    void getMoveCooldownReturnsCorrectCooldown() {
        assertEquals(5, BossesInfo.getMoveCooldown(0));
        assertEquals(4, BossesInfo.getMoveCooldown(1));
        assertEquals(7, BossesInfo.getMoveCooldown(2));
        assertEquals(0, BossesInfo.getMoveCooldown(3));
    }

    @Test
    void getAttackCooldownReturnsCorrectCooldown() {
        assertEquals(1.5f, BossesInfo.getAttackCooldown(0));
        assertEquals(1.2f, BossesInfo.getAttackCooldown(1));
        assertEquals(1, BossesInfo.getAttackCooldown(2));
        assertEquals(0, BossesInfo.getAttackCooldown(3));
    }

    @Test
    void getWeaponReturnsCorrectWeapon() {
        assertEquals(WeaponType.PhysicsBook, BossesInfo.getWeapon(0));
        assertEquals(WeaponType.Sword, BossesInfo.getWeapon(1));
        assertEquals(WeaponType.PhysicsBook, BossesInfo.getWeapon(2));
        assertEquals(WeaponType.PhysicsBook, BossesInfo.getWeapon(3));
    }

    @Test
    void getHealthReturnsCorrectHealth() {
        assertEquals(200, BossesInfo.getHealth(0));
        assertEquals(350, BossesInfo.getHealth(1));
        assertEquals(150, BossesInfo.getHealth(2));
        assertEquals(0, BossesInfo.getHealth(3));
    }
}