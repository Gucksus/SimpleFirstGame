package io.github.gucksus.simplefirstgame.entities.bullets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.gucksus.simplefirstgame.entities.base.Bullet;
import io.github.gucksus.simplefirstgame.tools.BoxWithOffset;

public class BasicBullet extends Bullet {
    public BasicBullet(TextureRegion[] idleAnimationFrames, float iniX, float iniY, float dx, float dy,
            SpriteBatch batch) {
        super(idleAnimationFrames, iniX, iniY, .5f, .5f, dx, dy, batch);
        speed = 17f;
        damage = 1f;
        fireRate = .2f;
        rectangleHitbox = new BoxWithOffset(sprite.getX(), sprite.getY(), 4, 9, 5, 0, pixelLength.x, pixelLength.y);
        maxBulletOnScreen = 5;
        movingType = MovingType.Straight;
    }
}
