package org.project.componentsystem.components.bosses;

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
}
