package org.project.componentsystem.components.bosses;

import org.project.componentsystem.components.weapons.WeaponType;

public class BossesInfo {
    public static final int BOSS_RESOLUTION = 64;
    public static final int IMPLEMENTED_BOSSES = 3;
    private static final int ATOM_ID = 0;
    private static final int INTEGRAL_ID = 1;
    private static final int MATRIX_ID = 2;

    public static String getName(int id) {
        switch (id) {
            case ATOM_ID:
                return "Atom";
            case INTEGRAL_ID:
                return "Integral";
            case MATRIX_ID:
                return "Matrix";
            default:
                return "Unknown";
        }
    }

    public static String getTexturePath(int id) {
        switch (id) {
            case ATOM_ID:
                return "resources/textures/bosses/atom.png";
            case INTEGRAL_ID:
                return "resources/textures/bosses/integrals.png";
            case MATRIX_ID:
                return "resources/textures/bosses/matrix.png";
            default:
                return "";
        }
    }

    public static float getSpeed(int id) {
        switch (id) {
            case ATOM_ID:
                return 2;
            case INTEGRAL_ID:
                return 1;
            case MATRIX_ID:
                return 1.5f;
            default:
                return 0;
        }
    }

    public static float getMoveCooldown(int id) {
        switch (id) {
            case ATOM_ID:
                return 5;
            case INTEGRAL_ID:
                return 4;
            case MATRIX_ID:
                return 7;
            default:
                return 0;
        }
    }

    public static float getAttackCooldown(int id) {
        switch (id) {
            case ATOM_ID:
                return 1.5f;
            case INTEGRAL_ID:
                return 1.2f;
            case MATRIX_ID:
                return 1;
            default:
                return 0;
        }
    }

    public static WeaponType getWeapon(int id) {
        switch (id) {
            case ATOM_ID:
                return WeaponType.PhysicsBook;
            case INTEGRAL_ID:
                return WeaponType.Sword;
            case MATRIX_ID:
                return WeaponType.PhysicsBook;
            default:
                return WeaponType.PhysicsBook;
        }
    }

    public static int getHealth(int id) {
        switch (id) {
            case ATOM_ID:
                return 200;
            case INTEGRAL_ID:
                return 350;
            case MATRIX_ID:
                return 150;
            default:
                return 0;
        }
    }
}
