package org.project.utils;

import lombok.Getter;
import org.project.savingsystem.Savable;

@Getter
public class Vec2Int implements Savable<Vec2Int> {
    private int x;
    private int y;

    public Vec2Int() {
        this(0, 0);
    }

    public Vec2Int(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Vec2Int && ((Vec2Int) obj).x == x && ((Vec2Int) obj).y == y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }


    /* ---------------- SAVABLE ------------------*/
    @Override
    public String toSaveString() {
        return x + "," + y;
    }

    @Override
    public Vec2Int fromSaveStringToObject(String s) {
        String[] values = s.split(",");
        x = Integer.parseInt(values[0]);
        y = Integer.parseInt(values[1]);
        return this;
    }
}
