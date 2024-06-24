package org.project.componentsystem.components;

import lombok.extern.log4j.Log4j2;
import org.project.componentsystem.GameObject;
import org.project.core.rendering.Renderable;
import org.project.core.rendering.Renderer;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Log4j2
public class AnimatedSpriteRenderer extends Component {
    private final int frameWidth;
    private final int frameHeight;
    private BufferedImage spriteSheet;
    private BufferedImage currentFrame;
    private final int priority;

    public AnimatedSpriteRenderer(GameObject gameObject, String spriteSheetPath, int frameWidth, int frameHeight, int priority) {
        super(gameObject);
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.priority = priority;
        try {
            spriteSheet = ImageIO.read(new File(spriteSheetPath));
            this.currentFrame = spriteSheet.getSubimage(0, 0, frameWidth, frameHeight);
        } catch (IOException e) {
            log.error("Failed to load sprite sheet: {}", spriteSheetPath);
        }
        currentFrame = spriteSheet.getSubimage(0, 0, frameWidth, frameHeight);

        if(spriteSheet.getWidth() % frameWidth != 0 || spriteSheet.getHeight() % frameHeight != 0)
            log.error(
                    "The sprite sheet is not a multiple of the frame size! " +
                            "[SpriteSheetWidth={}, SpriteSheetHeight={}, FrameWidth={}, FrameHeight={}]",
                    spriteSheet.getWidth(),
                    spriteSheet.getHeight(),
                    frameWidth,
                    frameHeight
            );
    }


    public void setSheetState(int x, int y) {
        if(x * frameWidth >= spriteSheet.getWidth() || y * frameHeight >= spriteSheet.getHeight() || x < 0 || y < 0)
            throw new IllegalArgumentException("Invalid frame position");

        currentFrame = spriteSheet.getSubimage(x * frameWidth, y * frameHeight, frameWidth, frameHeight);
    }

    public void start() {
    }

    public void update() {
        Renderer.addImageToRenderQueue(getGameObject().getPosition(), currentFrame, priority);
    }

    /**
     * Rotate the current frame
     *
     * @param degrees The degrees to rotate
     */
    public void rotate(int degrees) {
        double radians = Math.toRadians(degrees);
        double locationX = currentFrame.getWidth() / 2.0;
        double locationY = currentFrame.getHeight() / 2.0;
        AffineTransform tx = AffineTransform.getRotateInstance(radians, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        currentFrame = op.filter(currentFrame, null);
    }

    /**
     * Destory the component
     */
    @Override
    public void destory() {
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
