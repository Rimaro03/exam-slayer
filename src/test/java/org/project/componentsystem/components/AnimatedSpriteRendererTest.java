package org.project.componentsystem.components;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.project.componentsystem.GameObject;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnimatedSpriteRendererTest {

    @Test
    void animatedSpriteRendererSetsSheetStateCorrectly() {
        GameObject gameObject = Mockito.mock(GameObject.class);
        AnimatedSpriteRenderer renderer = new AnimatedSpriteRenderer(
                gameObject,
                "resources/textures/touchable/book.png",
                16,
                16,
                1
        );
        BufferedImage initialFrame = renderer.getCurrentFrame();

        renderer.setSheetState(0, 0);
        BufferedImage updatedFrame = renderer.getCurrentFrame();

        assertNotEquals(initialFrame, updatedFrame);
    }

    @Test
    void animatedSpriteRendererRotatesCorrectly() {
        GameObject gameObject = Mockito.mock(GameObject.class);
        AnimatedSpriteRenderer renderer = new AnimatedSpriteRenderer(
                gameObject,
                "resources/textures/touchable/book.png",
                16,
                16,
                1
        );
        BufferedImage initialFrame = renderer.getCurrentFrame();

        renderer.rotate(90);
        BufferedImage rotatedFrame = renderer.getCurrentFrame();

        assertNotEquals(initialFrame, rotatedFrame);
    }

    @Test
    void animatedSpriteRendererThrowsExceptionForInvalidSheetState() {
        GameObject gameObject = Mockito.mock(GameObject.class);
        AnimatedSpriteRenderer renderer = new AnimatedSpriteRenderer(
                gameObject,
                "resources/textures/touchable/book.png",
                16,
                16,
                1
        );

        assertThrows(IllegalArgumentException.class, () -> renderer.setSheetState(100, 100));
    }
}