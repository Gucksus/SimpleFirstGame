package io.github.gucksus.simplefirstgame.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import io.github.gucksus.simplefirstgame.entities.base.Enemy;
import io.github.gucksus.simplefirstgame.entities.base.EnemyBullet;
import io.github.gucksus.simplefirstgame.entities.bullets.SkullShooterBullet;

public class SkullShooterEnemy extends Enemy {
    public SkullShooterEnemy(TextureRegion staticTexture, Texture bulletTexture, float iniX, float iniY) {
        super(staticTexture , iniX, iniY, 4, 4);
        health = 5f;
        hitboxOffsetX = width / textureSizeX * 27;
        hitboxOffsetY = height / textureSizeY * 26;
        shootPointOffsetX = width / textureSizeX * 32;
        shootPointOffsetY = height / textureSizeY * 22;
        hurtboxOffsetX = width / textureSizeX * 23;
        hurtboxOffsetY = height / textureSizeY * 25;
        hitbox = new Rectangle(iniX + hitboxOffsetX, iniY + hitboxOffsetY, width / 64f * 10, height / 64 * 16);
        hurtbox = new Rectangle(iniX + hurtboxOffsetX, iniY + hurtboxOffsetY, width / 64f * 18, height / 64 * 20);
        this.bulletTexture = bulletTexture;
        animationInterval = 1;
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
