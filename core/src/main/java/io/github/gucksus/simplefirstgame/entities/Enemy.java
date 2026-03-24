package io.github.gucksus.simplefirstgame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import org.w3c.dom.Text;

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

    public Enemy(Texture texture, float iniX, float iniY, float width, float height) {
        this.width = width;
        this.height = height;
        sprite = new Sprite(texture);
        sprite.setSize(width, height);
        sprite.setPosition(iniX, iniY);
        initialX = iniX;
    }

    public void update(float delta) {
        sprite.translateX(-speedX * delta);
        sprite.translateY(speedY * delta);
    }
}
