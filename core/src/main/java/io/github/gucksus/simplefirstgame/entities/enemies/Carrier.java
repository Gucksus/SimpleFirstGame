package io.github.gucksus.simplefirstgame.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import io.github.gucksus.simplefirstgame.entities.MainShip;
import io.github.gucksus.simplefirstgame.entities.base.Bullet;
import io.github.gucksus.simplefirstgame.entities.base.Enemy;
import io.github.gucksus.simplefirstgame.entities.bullets.PowerUp;
import io.github.gucksus.simplefirstgame.tools.BoxWithOffset;
import io.github.gucksus.simplefirstgame.waves.Wave;

public class Carrier extends Enemy {
    float secondsOnScreen = 12;
    float amplitude = worldWidth / 2;

    public Carrier(TextureRegion texture, TextureRegion[] idleFrames, float iniX, float iniY, MainShip mainShip, Wave wave) {
        super(texture, iniX, iniY, 1, 1, mainShip, wave);
        health = 4;
        moveDuration = 2;
        currentMovingType = movingType.Straight;
        currentAnimationType = AnimationType.Idle;
        hurtboxes.add(new BoxWithOffset(iniX, iniY, 16, 21, 8, 6, pixelLength.x, pixelLength.y));
        bulletIdleFrames = idleFrames;
    }

    @Override
    protected Bullet returnBulletType(float shootPointX, float shootPointY, float dx, float dy) {
        return null;
    }

    @Override
    public void drawAnimation() {
        float delta = Gdx.graphics.getDeltaTime();
        switch (currentAnimationType) {
            case Idle:
                stateTime += delta;
                TextureRegion currentFrame = idleAnimation.getKeyFrame(stateTime);

                batch.draw(currentFrame, sprite.getX(), sprite.getY(), width, height);
                if (idleAnimation.isAnimationFinished(stateTime))
                    stateTime = 0;
                break;
            case Death:
                triggerMoveOutOfScreen();
                stateTime += delta;
                TextureRegion currentFrame1 = deathAnimation.getKeyFrame(stateTime);
                
                batch.draw(currentFrame1, sprite.getX(), sprite.getY(), width, height);
                if (idleAnimation.isAnimationFinished(stateTime))
                    stateTime = 0;
                break;
        }
    }

    void triggerMoveOutOfScreen() {
        if (currentMovingType == movingType.Straight) {
            float delta = Gdx.graphics.getDeltaTime();
            moveTimer += delta;

            Vector2 currentPos = getCoordinate();
            Vector2 nextPoint = new Vector2(path.first().x + MathUtils.sin(moveTimer) * amplitude,
                    path.first().y - moveTimer / secondsOnScreen * worldHeight);

            int daausMultiplier;
            if (nextPoint.x - currentPos.x < 0)
                daausMultiplier = -1;
            else
                daausMultiplier = 1;

            Vector2[] tempPath = {currentPos, nextPoint,
                    new Vector2(currentPos.x + daausMultiplier * 3,
                            currentPos.y + (nextPoint.y - currentPos.y) * 2),
                    new Vector2(), new Vector2()};
            if (currentPos.x > worldWidth / 2) {
                tempPath[3].set(-1, 20);
                tempPath[4] = tempPath[3].cpy();
            } else {
                tempPath[3].set(12, 20);
                tempPath[4] = tempPath[3].cpy();
            }
            catmullRomSpline = new CatmullRomSpline<>(tempPath, false);
            currentMovingType = movingType.Curve;
            moveTimer = 0;
        }
    }

    @Override
    protected void moveUpdate() {
        float delta = Gdx.graphics.getDeltaTime();
        switch (currentMovingType) {
            case Straight:
                moveTimer += delta;
                Vector2 nextPoint =
                        new Vector2(path.first().x + MathUtils.sin(moveTimer) * amplitude,
                                path.first().y - moveTimer / secondsOnScreen * worldHeight);
                sprite.setCenter(nextPoint.x, nextPoint.y);
                break;
            case Curve:
                moveTimer += delta;
                if (moveTimer >= moveDuration)
                    currentMovingType = movingType.Still;
                Vector2 nextPoint1 = new Vector2();
                catmullRomSpline.valueAt(nextPoint1, moveTimer / moveDuration);
                sprite.setCenter(nextPoint1.x, nextPoint1.y);
                break;
            case Still:
                break;
        }
    }

    @Override
    protected void takeDamage(float damage) {
        health -= 1;
        bulletHolder.enemyBullets.add(new PowerUp(bulletIdleFrames, sprite.getX(), sprite.getY(), 2, 1, batch));
    }
}
