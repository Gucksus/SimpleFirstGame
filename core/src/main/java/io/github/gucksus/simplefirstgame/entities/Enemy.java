package io.github.gucksus.simplefirstgame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public abstract class Enemy {
    public float health;
    float speedX;
    float speedY;
    float amplitude;
    float frequency;
    float timer = 0;
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

    // This constructor initializes width, height, sprite, initial position and neglect everything else. Therefore,
    // you have to add it in the subclass.
    public Enemy(Texture texture, float iniX, float iniY, float width, float height) {
        this.width = width;
        this.height = height;
        sprite = new Sprite(texture);
        sprite.setSize(width, height);
        sprite.setPosition(iniX, iniY);
        initialX = iniX;
    }

    public void updatePosition(float delta){

    }

    public void updateStatus() {
        if (health <= 0) {
            isDead = true;
        }
    }
}
