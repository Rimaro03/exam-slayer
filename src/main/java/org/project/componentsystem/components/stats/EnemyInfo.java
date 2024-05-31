package org.project.componentsystem.components.stats;

import lombok.extern.log4j.Log4j2;

import java.util.Random;

@Log4j2
public class EnemyInfo {
    private static final int DX_ID = 0;
    private static final int DY_ID = 1;
    private static final int INTEGRAL_ID = 2;
    private static final int DOUBLE_INTEGRAL_ID = 3;
    private static final int IMPLEMENTED_BOSSES = 4;

    public static String getName(int id) {
        switch (id) {
            case DX_ID:
                return "Dx";
            case DY_ID:
                return "Dy";
            case INTEGRAL_ID:
                return "Integral";
            case DOUBLE_INTEGRAL_ID:
                return "DoubleIntegral";
            default:
                invalidIdLog(id);
                return "Unknown";
        }
    }

    public static String getTexturePath(int id) {
        switch (id) {
            case DX_ID:
                return "resources/textures/characters/dx.png";
            case DY_ID:
                return "resources/textures/characters/dy.png";
            case INTEGRAL_ID:
                return "resources/textures/characters/integral.png";
            case DOUBLE_INTEGRAL_ID:
                return "resources/textures/characters/double_integral.png";
            default:
                invalidIdLog(id);
                return "";
        }
    }

    public static int getTextureWidth(int id) {
        switch (id) {
            case DX_ID:
            case DY_ID:
            case DOUBLE_INTEGRAL_ID:
                return 24;
            case INTEGRAL_ID:
                return 16;
            default:
                invalidIdLog(id);
                return -1;
        }
    }

    public static int getTextureHeight(int id) {
        switch (id) {
            case DX_ID:
            case DY_ID:
            case INTEGRAL_ID:
            case DOUBLE_INTEGRAL_ID:
                return 32;
            default:
                invalidIdLog(id);
                return -1;
        }
    }

    public static int getRandomEnemyId(Random rand) {
        return rand.nextInt(IMPLEMENTED_BOSSES);
    }


    private static void invalidIdLog(int id) {
        log.error("Unknown enemy id: {}", id);
    }
}
