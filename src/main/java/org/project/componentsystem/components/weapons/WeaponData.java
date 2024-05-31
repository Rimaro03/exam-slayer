package org.project.componentsystem.components.weapons;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class WeaponData {
    private static final WeaponData physicsBookData = new WeaponData(
            10,
            0.1f,
            10.0f,
            "resources/textures/touchable/book.png",
            16,
            16
    );
    private static final WeaponData swordData = new WeaponData(
            20,
            0.12f,
            15.0f,
            "resources/textures/touchable/sword.png",
            16,
            16
    );
    public final int damage;
    public final float reloadTime;
    public final float speed;
    public final String imagePath;
    public final int imageWidth;
    public final int imageHeight;
    public WeaponData(int damage, float reloadTime, float speed, String imagePath, int imageWidth, int imageHeight) {
        this.damage = damage;
        this.reloadTime = reloadTime;
        this.speed = speed;
        this.imagePath = imagePath;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    public static WeaponData getWeaponData(WeaponType type) {
        switch (type) {
            case PhysicsBook:
                return physicsBookData;

            case Sword:
                return swordData;

            default:
                throw new IllegalArgumentException("Weapon type not implemented : " + type);
        }
    }
}
