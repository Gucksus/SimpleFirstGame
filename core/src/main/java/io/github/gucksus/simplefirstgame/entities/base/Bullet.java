package io.github.gucksus.simplefirstgame.entities.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * <b>YOU HAVE TO DECLARE THESE VARIABLE IN SUBCLASSES:</b> <i>movingType, isCircle, circleHitBox
 * <b>OR</b> rectangleHitbox, speed.</i>
 */
public class Bullet {
    protected float iniX;
    protected float iniY;
    protected float speed;
    protected float width;
    protected float height;
    float timer;
    protected float damage = 1;
    protected float fireRate;
    protected int maxBulletOnScreen;
    protected Sprite sprite;
    protected Rectangle rectangleHitbox;
    protected Vector2 rectangleHitboxOffset = new Vector2();
    protected Circle circleHitbox;
    protected Vector2 circleHitboxOffset = new Vector2();
    private boolean isCircle;

    protected enum MovingType {
        Straight, Curve, Roundabout
    }

    protected MovingType movingType = MovingType.Straight;
    protected Vector2 direction;
    SpriteBatch batch;

    public Bullet(Texture texture, float iniX, float iniY, float width, float height, float dx,
            float dy, SpriteBatch batch) {
        this.width = width;
        this.height = height;
        this.iniX = iniX;
        this.iniY = iniY;
        sprite = new Sprite(texture);
        sprite.setSize(width, height);
        sprite.setCenterX(iniX);
        sprite.setCenterY(iniY);
        direction = new Vector2();
        direction.set(dx, dy);
        this.batch = batch;
    }

    void updateHitbox() {
        if (isCircle())
            circleHitbox.setPosition(sprite.getX() + circleHitboxOffset.x,
                    sprite.getY() + circleHitboxOffset.y);
        else
            rectangleHitbox.setPosition(sprite.getX() + rectangleHitboxOffset.x,
                    sprite.getY() + rectangleHitboxOffset.y);
    }

    /**
     * This works based on 2D vector.
     * 
     * @param delta The frame lastDelta time.
     */
    public void updateStraight(float delta) {
        timer += delta;
        float distanceMultiplier = speed / direction.len();
        sprite.setCenterX(iniX + direction.x * distanceMultiplier * timer);
        sprite.setCenterY(iniY + direction.y * distanceMultiplier * timer);
    }

    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        switch (movingType) {
            case Straight:
                updateStraight(delta);
                break;
            case Curve:
                throw new Error("Unimplemented.");
            case Roundabout:
                throw new Error("Unimplemented.");
        }
        updateHitbox();
    }

    public void draw() {
        sprite.draw(batch);
    }

    public float getDamage() {
        return damage;
    }

    public float getMaxBulletOnScreen() {
        return maxBulletOnScreen;
    }

    public float getFireRate() {
        return fireRate;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Rectangle getRectangleHitbox() {
        return rectangleHitbox;
    }

    public Circle getCircleHitbox() {
        return circleHitbox;
    }

    public boolean isCircle() {
        return isCircle;

    }

    public void setCircle(boolean isCircle) {
        this.isCircle = isCircle;

    }
}
