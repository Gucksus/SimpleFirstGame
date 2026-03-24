package io.github.gucksus.simplefirstgame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class Enemylv1 {
    public final float health = 1f;
    public final float speedY = .3f;
    final float amplitude = 2f;
    final float frequency = 2f;
    float timer = 0;
    public Sprite selfSprite;
    float initialX;

    public Enemylv1(Texture texture, float iniX, float iniY) {
        selfSprite = new Sprite(texture);
        selfSprite.setSize(1, 1);
        selfSprite.setPosition(iniX, iniY);
        initialX = iniX;
    }

    public void moveWeirdly(float delta) {
        timer += delta;

        float newX = initialX + MathUtils.sin(timer * frequency) * amplitude;

        float newY = selfSprite.getY() - speedY * delta;

        selfSprite.setPosition(newX, newY);
    }
}
