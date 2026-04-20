package io.github.gucksus.simplefirstgame.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.gucksus.simplefirstgame.entities.MainShip;
import io.github.gucksus.simplefirstgame.entities.base.Enemy;
import io.github.gucksus.simplefirstgame.entities.base.EnemyBullet;
import io.github.gucksus.simplefirstgame.entities.bullets.SkullShooterBullet;
import io.github.gucksus.simplefirstgame.tools.BoxWithOffset;
import io.github.gucksus.simplefirstgame.tools.DebugRenderer;
import io.github.gucksus.simplefirstgame.waves.Wave;

public class SkullShooterEnemy extends Enemy {
    public SkullShooterEnemy(TextureRegion staticTexture, Texture bulletTexture, float iniX, float iniY, float worldWidth, float worldHeight, MainShip mainShip, SpriteBatch batch, DebugRenderer debugRenderer, Wave wave) {
        super(staticTexture , iniX, iniY, 4, 4, worldWidth, worldHeight, mainShip, batch, debugRenderer, wave);
        health = 6f;
        shootPointsOffsets.add(new Vector2(pixelLength.x * 32, pixelLength.y * 22));
        hitboxes.add(new BoxWithOffset(iniX, iniY, 10, 19, 27, 23, pixelLength.x, pixelLength.y));
        hurtboxes.add(new BoxWithOffset(iniX, iniY, 18, 10, 23, 33, pixelLength.x, pixelLength.y));
        hurtboxes.add(new BoxWithOffset(iniX, iniY, 12, 10, 26, 20, pixelLength.x, pixelLength.y));
        this.bulletTexture = bulletTexture;
        animationInterval = .7f;
        shootFrameInterval = .05f;
        shootAnimationRepeat = 15;
    }



    @Override
    protected EnemyBullet returnBulletType(float shootPointX, float shootPointY, float dx, float dy) {
        return new SkullShooterBullet(bulletTexture, shootPointX, shootPointY, 3, 3, dx, dy);
    }

    @Override
    protected boolean shootThisFrame() {
        return (shootAnimation.getKeyFrameIndex(stateTime) == 6);
    }
}
