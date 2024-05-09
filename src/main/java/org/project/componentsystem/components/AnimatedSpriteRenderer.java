package org.project.componentsystem.components;

import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.GameObject;
import org.project.core.rendering.Renderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Log4j2
public class AnimatedSpriteRenderer extends Component {
    private BufferedImage spriteSheet;
    private BufferedImage currentFrame;
    private final int frameWidth;
    private final int frameHeight;
    public AnimatedSpriteRenderer(GameObject gameObject, String spriteSheetPath, int frameWidth, int frameHeight) {
        super(gameObject);
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        try {
            spriteSheet = ImageIO.read(new File(spriteSheetPath));
            this.currentFrame = spriteSheet.getSubimage(0, 0, frameWidth, frameHeight);
        } catch (IOException e) {
            log.error("Failed to load sprite sheet: {}", spriteSheetPath);
        }
    }


    public void setSheetState(int x, int y) {
        currentFrame = spriteSheet.getSubimage(x * frameWidth, y * frameHeight, frameWidth, frameHeight);
    }

    public void start() {}
    public void update(){
        Renderer.draw(currentFrame, getGameObject().getPosition());
    }

    /**
     * Destory the component
     */
    @Override
    public void destory() {

    }
}
