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

    public Enemylv1(float iniX, float iniY) {
        enemyLv1Texture = new Texture("enemylv1.png");
        selfSprite = new Sprite(enemyLv1Texture);
        selfSprite.setSize(1, 1);
        selfSprite.setPosition(iniX, iniY);
        initialX = iniX;
        hitbox = new Rectangle(iniX, iniY, 1, 1);
    }

    public void moveWeirdly(float delta) {
        timer += delta;

        float newX = initialX + MathUtils.sin(timer * frequency) * amplitude;

        float newY = selfSprite.getY() - speedY * delta;

        hitbox.setPosition(newX,newY);
        selfSprite.setPosition(newX, newY);
    }
}
