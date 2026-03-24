package io.github.gucksus.simplefirstgame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Enemylv1 {
    public final float health = 1f;
    public final float speedY = .3f;
    final float amplitude = 2f;
    final float frequency = 2f;
    float timer = 0;
    Texture enemyLv1Texture;
    public Sprite selfSprite;
    float initialX;
    public Rectangle hitbox;
    public Rectangle hurtbox;
    float width = 1;
    float height = 1;

    public Enemylv1(float iniX, float iniY) {
        enemyLv1Texture = new Texture("enemylv1.png");
        selfSprite = new Sprite(enemyLv1Texture);
        selfSprite.setSize(width, height);
        selfSprite.setPosition(iniX, iniY);
        initialX = iniX;
        hitbox = new Rectangle(iniX + width / 32 * 7, iniY + height / 32 * 8, width / 32 * 18f, height / 32 * 14);
        hurtbox = new Rectangle(iniX + width / 32 * 3, iniY + height / 32 * 7, width / 32 * 26, height / 32 * 15);
    }

    public void moveWeirdly(float delta) {
        timer += delta;

        float newX = initialX + MathUtils.sin(timer * frequency) * amplitude;

        float newY = selfSprite.getY() - speedY * delta;

        hitbox.setPosition(newX + width / 32 * 7, newY + height / 32 * 8);
        hurtbox.setPosition(newX + width / 32 * 3, newY + height / 32 * 7);
        selfSprite.setPosition(newX, newY);
    }
}
