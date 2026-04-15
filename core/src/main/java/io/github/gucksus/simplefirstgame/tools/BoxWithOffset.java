package io.github.gucksus.simplefirstgame.tools;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class BoxWithOffset {
    Rectangle box;
    float pixelLengthX;
    float pixelLengthY;
    float  offsetX;
    float offsetY;

    /**
     * This method creates a box for hitbox/hurtbox.
     *
     * @param iniX         The initial X coordinate.
     * @param iniY         The initial Y coordinate.
     * @param width        The width of the box <b>IN PIXEL</b>.
     * @param height       The height of the box <b>IN PIXEL</b>.
     * @param pixelOffsetX The X offset of the box from the origin of the sprite <b>IN PIXEL</b>.
     * @param pixelOffsetY The Y offset of the box from the origin of the sprite <b>IN PIXEL</b>.
     * @param pixelLengthX The length of one pixel in game meter on the X axis.
     * @param pixelLengthY The length of one pixel in game meter on the Y axis.
     */
    public BoxWithOffset(float iniX, float iniY, int width, int height, int pixelOffsetX, int pixelOffsetY,  float pixelLengthX, float pixelLengthY) {
        this.pixelLengthX = pixelLengthX;
        this.pixelLengthY = pixelLengthY;
        offsetX = pixelLengthX * pixelOffsetX;
        offsetY = pixelLengthY * pixelOffsetY;
        box = new Rectangle(iniX + offsetX, iniY + offsetY, pixelLengthX * width, pixelLengthY * height);
    }

    public void update(float newX, float newY) {
        box.setPosition(newX + offsetX, newY + offsetY);
    }

    public Rectangle getBox() {
        return box;
    }
}
