package org.project.savingsystem;

/**
 * Interface for classes that can be saved to a file
 */
public interface Savable<T> {
    /**
     * Returns a string that must have the format:
     * value1,value2,value3,...,valueN (no spaces)
     * @return the string to save
     */
    String toSaveString();

    /**
     * Returns a Savable object from a string
     * @param s the string to parse
     * @return the Savable object
     */
    T fromSaveStringToObject(String s);
}
