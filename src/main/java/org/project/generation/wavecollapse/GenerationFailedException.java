package org.project.generation.wavecollapse;

/**
 * An exception thrown when the generation of a level fails.
 */
public class GenerationFailedException extends RuntimeException {
    public GenerationFailedException(String message) {
        super(message);
    }
}
