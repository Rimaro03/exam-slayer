package org.project.componentsystem.components.weapons;

import lombok.extern.log4j.Log4j2;

import java.util.Random;

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
    private static final WeaponInfo pencilData = new WeaponInfo(
            5,
            0.1f,
            20.0f,
            "resources/textures/touchable/pencil.png",
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
    private static final WeaponInfo protonData = new WeaponInfo(
            30,
            0.5f,
            15.0f,
            "resources/textures/bosses/throwable/proton.png",
            16,
            16
    );
    private static final WeaponInfo functionData = new WeaponInfo(
            40,
            0.5f,
            10.0f,
            "resources/textures/bosses/throwable/f.png",
            16,
            16
    );
    private static final WeaponInfo zeroData = new WeaponInfo(
            50,
            0.5f,
            10.0f,
            "resources/textures/bosses/throwable/0.png",
            16,
            16
    );
    private static final WeaponInfo oneData = new WeaponInfo(
            30,
            0.5f,
            15.0f,
            "resources/textures/bosses/throwable/1.png",
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
            case Pencil:
                return pencilData;
            case Sword:
                return swordData;
            case Proton:
                return protonData;
            case Function:
                return functionData;
            case Number:
                return new Random().nextBoolean() ? zeroData : oneData;
            default:
                throw new IllegalArgumentException("Weapon type not implemented : " + type);
        }
    }


}
