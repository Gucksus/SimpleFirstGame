package io.github.gucksus.simplefirstgame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public abstract class Bullet {
    float speed;
    float damage;
    float width;
    float height;
    public Sprite sprite;
    public Rectangle hitbox;
    public float bulletFireRate;
    float hitboxOffsetX;

    public Bullet(Texture texture, float iniX, float iniY, float width, float height) {
        this.width = width;
        this.height = height;
        sprite = new Sprite(texture);
        sprite.setSize(width, height);
        sprite.setCenterX(iniX);
        sprite.setY(iniY);
    }

    public void update(float delta){
        sprite.translateY(delta * speed);
        hitbox.setPosition(sprite.getX() + hitboxOffsetX, sprite.getY());
    }
}
