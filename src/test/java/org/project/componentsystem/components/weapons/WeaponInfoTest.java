package org.project.componentsystem.components.weapons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void getWeaponDataThrowsExceptionForInvalidWeaponType() {
        assertThrows(NullPointerException.class, () -> WeaponInfo.getWeaponData(null));
    }
}