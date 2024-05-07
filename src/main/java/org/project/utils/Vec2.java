package org.project.utils;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Vec2 {
    private int x;
    private int y;

    /**
     * Initializes a new Vec2 with the given x and y values
     * @param x The x value
     * @param y The y value
     */
    public Vec2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds another Vec2 to this Vec2
     * @param other The other Vec2 to add
     * @return A new Vec2 that is the sum of this Vec2 and the other Vec2
    */
    public Vec2 add(Vec2 other) {
        return new Vec2(x + other.x, y + other.y);
    }

    /**
     * Subtracts another Vec2 from this Vec2
     * @param other The other Vec2 to subtract
     * @return A new Vec2 that is the difference of this Vec2 and the other Vec2
     */
    public Vec2 subtract(Vec2 other) {
        return new Vec2(x - other.x, y - other.y);
    }

    /**
     * Multiplies this Vec2 by a scalar
     * @param scalar The scalar to multiply by
     * @return A new Vec2 that is the product of this Vec2 and the scalar
     */
    public Vec2 multiply(int scalar) {
        return new Vec2(x * scalar, y * scalar);
    }

    /**
     * Divides this Vec2 by a scalar
     * @param scalar The scalar to divide by
     * @return A new Vec2 that is the quotient of this Vec2 and the scalar
     */
    public Vec2 divide(int scalar) {
        return new Vec2(x / scalar, y / scalar);
    }

    /**
     * Calculates the dot product of this Vec2 and another Vec2
     * @param other The other Vec2 to calculate the dot product with
     * @return The dot product of this Vec2 and the other Vec2
     */
    public int dot(Vec2 other) {
        return x * other.x + y * other.y;
    }

    /**
     * Calculates the magnitude of this Vec2
     * @return The magnitude of this Vec2
     */
    public int magnitude() {
        return (int) Math.sqrt(x * x + y * y);
    }

    /**
     * Normalizes this Vec2
     * @return A new Vec2 that is the normalized version of this Vec2
     */
    public Vec2 normalize() {
        int mag = magnitude();
        return new Vec2(x / mag, y / mag);
    }

    /**
     * Calculates the distance between this Vec2 and another Vec2
     * @param other The other Vec2 to calculate the distance to
     * @return The distance between this Vec2 and the other Vec2
     */
    public int distance(Vec2 other) {
        return subtract(other).magnitude();
    }

    /**
     * Creates a copy of this Vec2
     * @return A new Vec2 that is a copy of this Vec2
     */
    public Vec2 copy() {
        return new Vec2(x, y);
    }

    /**
     * Checks if this Vec2 is equal to another Vec2
     * @param other The other Vec2 to compare to
     * @return True if the two Vets are equal, false otherwise
     */
    public boolean equals(Vec2 other) {
        return x == other.x && y == other.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
