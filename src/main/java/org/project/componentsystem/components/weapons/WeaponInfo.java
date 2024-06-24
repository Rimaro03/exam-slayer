package org.project.componentsystem.components.weapons;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class WeaponInfo {
    private static final WeaponInfo physicsBookData = new WeaponInfo(
            10,
            0.2f,
            10.0f,
            "resources/textures/touchable/book.png",
            16,
            16
    );
    private static final WeaponInfo swordData = new WeaponInfo(
            20,
            0.5f,
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
    public WeaponInfo(int damage, float reloadTime, float speed, String imagePath, int imageWidth, int imageHeight) {
        this.damage = damage;
        this.reloadTime = reloadTime;
        this.speed = speed;
        this.imagePath = imagePath;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    public static WeaponInfo getWeaponData(WeaponType type) {
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
