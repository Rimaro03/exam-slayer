package org.project.componentsystem.components.weapons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeaponInfoTest {

    @Test
    void getWeaponDataReturnsCorrectDataForPhysicsBook() {
        WeaponInfo weaponInfo = WeaponInfo.getWeaponData(WeaponType.PhysicsBook);
        assertEquals(10, weaponInfo.damage);
        assertEquals(0.2f, weaponInfo.reloadTime);
        assertEquals(10.0f, weaponInfo.speed);
        assertEquals("resources/textures/touchable/book.png", weaponInfo.imagePath);
        assertEquals(16, weaponInfo.imageWidth);
        assertEquals(16, weaponInfo.imageHeight);
    }

    @Test
    void getWeaponDataReturnsCorrectDataForSword() {
        WeaponInfo weaponInfo = WeaponInfo.getWeaponData(WeaponType.Sword);
        assertEquals(20, weaponInfo.damage);
        assertEquals(0.5f, weaponInfo.reloadTime);
        assertEquals(15.0f, weaponInfo.speed);
        assertEquals("resources/textures/touchable/sword.png", weaponInfo.imagePath);
        assertEquals(16, weaponInfo.imageWidth);
        assertEquals(16, weaponInfo.imageHeight);
    }

    @Test
    void getWeaponDataReturnsCorrectDataForProton() {
        WeaponInfo weaponInfo = WeaponInfo.getWeaponData(WeaponType.Proton);
        assertEquals(30, weaponInfo.damage);
        assertEquals(0.5f, weaponInfo.reloadTime);
        assertEquals(15.0f, weaponInfo.speed);
        assertEquals("resources/textures/bosses/throwable/proton.png", weaponInfo.imagePath);
        assertEquals(16, weaponInfo.imageWidth);
        assertEquals(16, weaponInfo.imageHeight);
    }

    @Test
    void getWeaponDataReturnsCorrectDataForFunction() {
        WeaponInfo weaponInfo = WeaponInfo.getWeaponData(WeaponType.Function);
        assertEquals(40, weaponInfo.damage);
        assertEquals(0.5f, weaponInfo.reloadTime);
        assertEquals(10.0f, weaponInfo.speed);
        assertEquals("resources/textures/bosses/throwable/f.png", weaponInfo.imagePath);
        assertEquals(16, weaponInfo.imageWidth);
        assertEquals(16, weaponInfo.imageHeight);
    }

    @Test
    void getWeaponDataReturnsCorrectDataForNumber() {
        WeaponInfo weaponInfo = WeaponInfo.getWeaponData(WeaponType.Number);

        assertTrue(weaponInfo.damage == 50 || weaponInfo.damage == 30);
        assertEquals(0.5f, weaponInfo.reloadTime);
        assertTrue(weaponInfo.speed == 10.0f || weaponInfo.speed == 15.0f);
        assertTrue(weaponInfo.imagePath.equals("resources/textures/bosses/throwable/0.png") ||
                weaponInfo.imagePath.equals("resources/textures/bosses/throwable/1.png"));
        assertEquals(16, weaponInfo.imageWidth);
        assertEquals(16, weaponInfo.imageHeight);
    }

    @Test
    void getWeaponDataThrowsExceptionForInvalidWeaponType() {
        assertThrows(NullPointerException.class, () -> WeaponInfo.getWeaponData(null));
    }
}