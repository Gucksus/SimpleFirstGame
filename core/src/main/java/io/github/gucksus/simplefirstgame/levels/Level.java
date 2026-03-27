package io.github.gucksus.simplefirstgame.levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import io.github.gucksus.simplefirstgame.entities.Enemy;
import io.github.gucksus.simplefirstgame.tools.DebugRenderer;
import io.github.gucksus.simplefirstgame.waves.Wave;

public abstract class Level {
    public boolean isLevelCompleted = false;
    public float lvTimer = 0;
    DebugRenderer debugRenderer;
    Texture popcornEnemyTexture;
    public Array<Enemy> activeEnemies;
    public Array <Wave> waveArray;

    public Level() {
        debugRenderer = new DebugRenderer();
        activeEnemies = new Array<>();
        waveArray = new Array<>();
    }

    public abstract void enemySpawn(float delta, float worldWidth, float worldHeight);

    public void draw(Batch batch) {
        for (Enemy enemy: activeEnemies) if (!enemy.isInvisible) {
            enemy.sprite.draw(batch);
        }
    }

    public void drawEnemyHitboxAndHurtBox(ShapeRenderer shapeRenderer){
        for (Enemy enemy: activeEnemies){
            debugRenderer.drawHitbox(enemy.hitbox, shapeRenderer);
            debugRenderer.drawHurtbox(enemy.hurtbox, shapeRenderer);
        }
    }

    public void waveUpdate(float delta, float worldWidth, float worldHeight) {
        for (int i = waveArray.size - 1; i >= 0; i--) {
            Wave wave = waveArray.get(i);
            wave.enemyUpdateRemoval(worldWidth, worldHeight);
            wave.updateEnemyMovingStatus(delta);
            removeWaveOutOfTheScreen(wave, worldWidth, worldHeight);
        }
    }

    public void removeWaveOutOfTheScreen(Wave wave, float worldWidth, float worldHeight) {
        boolean isAllOut = true;
        for (Enemy enemy: wave.waveEnemyArray){
            Sprite sprite = enemy.sprite;
            if (!(sprite.getX() < -enemy.width || sprite.getX() > worldWidth || sprite.getY() < -enemy.height || sprite.getY() > worldHeight)) {
                isAllOut = false;
                break;
            }
        }
        if (isAllOut) waveArray.removeValue(wave, true);
    }
}
