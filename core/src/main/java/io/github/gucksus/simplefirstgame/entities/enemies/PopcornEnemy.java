package io.github.gucksus.simplefirstgame.entities.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.gucksus.simplefirstgame.entities.MainShip;
import io.github.gucksus.simplefirstgame.entities.base.Enemy;
import io.github.gucksus.simplefirstgame.entities.base.EnemyBullet;
import io.github.gucksus.simplefirstgame.tools.BoxWithOffset;
import io.github.gucksus.simplefirstgame.tools.DebugRenderer;
import io.github.gucksus.simplefirstgame.waves.Wave;

public class PopcornEnemy extends Enemy {
    public PopcornEnemy(TextureRegion staticTexture, float iniX, float iniY, float worldWidth, float worldHeight, MainShip mainShip, SpriteBatch batch, DebugRenderer debugRenderer, Wave wave) {
        super(staticTexture , iniX, iniY, 1, 1, worldWidth, worldHeight, mainShip, batch, debugRenderer, wave);
        health = 1f;
        hitboxes.add(new BoxWithOffset(iniX, iniY, 20, 16, 6, 6, pixelLength.x, pixelLength.y));
        hurtboxes.add(new BoxWithOffset(iniX, iniY, 26, 15, 3, 7, pixelLength.x, pixelLength.y));
        shootAnimationFrameNum = 0;
        deathAnimationFrameNum = 0;
    }

    @Override
    protected EnemyBullet returnBulletType(float shootPointX, float shootPointY, float dx, float dy) {
        return null;
    }

    @Override
    protected boolean shootThisFrame() {return false;}
}
