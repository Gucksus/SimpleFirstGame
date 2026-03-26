package io.github.gucksus.simplefirstgame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public abstract class Enemy {
    public float health;
    float amplitude;
    float frequency;
    float sinTimer = 0;
    public Sprite sprite;
    float initialX;
    public Rectangle hitbox;
    public Rectangle hurtbox;
    float width;
    float height;
    float hitboxOffsetX;
    float hitboxOffsetY;
    float hurtboxOffsetX;
    float hurtboxOffsetY;
    public boolean isDead = false;
    public boolean isMoving;
    public boolean isInvulnerable;
    public boolean isInvisible;
    public float nextFrameXDifference;
    public float nextFrameYDifference;
    MovingType currentMovingType;
    enum MovingType {Straight, Curve}

    // This constructor initializes width, height, sprite, initial position and neglect everything else. Therefore,
    // you have to add it in the subclass.
    public Enemy(Texture texture, float iniX, float iniY, float width, float height) {
        this.width = width;
        this.height = height;
        sprite = new Sprite(texture);
        sprite.setSize(width, height);
        sprite.setPosition(iniX, iniY);
        initialX = iniX;
        currentMovingType = MovingType.Straight;
    }

    public void updatePosition(float delta) {
        if (!isMoving)
            return;
        switch (currentMovingType) {
            case Straight:
                moveStraight();
            case Curve:
                moveCurve(delta);
        }
    }

    public void moveStraight() {
        sprite.translate(nextFrameXDifference, nextFrameYDifference);
    }

    public void moveCurve(float delta) {
        sinTimer += delta;

        float newX = initialX + MathUtils.sin(sinTimer * frequency) * amplitude;

        float newY = sprite.getY() + nextFrameYDifference;

        hitbox.setPosition(newX + hitboxOffsetX, newY + hitboxOffsetY);
        hurtbox.setPosition(newX + hurtboxOffsetX, newY + hurtboxOffsetY);
        sprite.setPosition(newX, newY);
    }

    public void updateStatus() {
        if (health <= 0) {
            isDead = true;
        }
    }
}
