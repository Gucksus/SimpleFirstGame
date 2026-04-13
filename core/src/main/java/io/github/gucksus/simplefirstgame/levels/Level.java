package io.github.gucksus.simplefirstgame.levels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import io.github.gucksus.simplefirstgame.entities.MainShip;
import io.github.gucksus.simplefirstgame.entities.base.Enemy;
import io.github.gucksus.simplefirstgame.entities.base.EnemyBullet;
import io.github.gucksus.simplefirstgame.entities.bullets.SkullShooterBullet;
import io.github.gucksus.simplefirstgame.tools.DebugRenderer;
import io.github.gucksus.simplefirstgame.waves.Wave;
import jdk.tools.jmod.Main;

public abstract class Level {
    public boolean isLevelCompleted = false;
    public float lvTimer = 0;
    DebugRenderer debugRenderer;
    public Array<Enemy> activeEnemies;
    public Array<Wave> waveArray;
    public boolean isLevelStarted;
    public float delta = 1;
    public Array<EnemyBullet> enemyBulletArray;

    public Level() {
        debugRenderer = new DebugRenderer();
        activeEnemies = new Array<>();
        waveArray = new Array<>();
    }

    public abstract void enemySpawn(float worldWidth, float worldHeight);

    public void draw(SpriteBatch batch) {
        for (Enemy enemy : activeEnemies)
            if (!enemy.isInvisible) {
                enemy.draw(batch, delta);
            }
    }

    public void drawEnemyHitboxAndHurtBox(ShapeRenderer shapeRenderer) {
        for (Enemy enemy : activeEnemies) {
            debugRenderer.drawHitbox(enemy.hitbox, shapeRenderer);
            debugRenderer.drawHurtbox(enemy.hurtbox, shapeRenderer);
        }
    }

    public void update (float delta, float worldWidth, float worldHeight) {
        updateDelta(delta);
        wavesUpdate(delta, worldWidth, worldHeight);
    }

    public void wavesUpdate(float delta, float worldWidth, float worldHeight) {
        for (int i = waveArray.size - 1; i >= 0; i--) {
            Wave wave = waveArray.get(i);
            if (wave.isDone) {
                waveArray.removeIndex(i);
                continue;
            }
            wave.enemyUpdateRemoval(worldWidth, worldHeight);
            wave.updateEnemyMovingStatus(delta);
            if (wave.waveUpdateRemoval(worldWidth, worldHeight)) {
                waveArray.removeIndex(i);
            }
        }
    }

    public void updateDelta (float delta) {
        this.delta = delta;
    }

    public void startLevelIfHaveNotStarted (float delta, float worldWidth, float worldHeight) {
        if (!this.isLevelStarted && Math.abs(this.delta - delta) <= .001f) {
            this.enemySpawn(worldWidth, worldHeight);
            this.isLevelStarted = true;
        }
    }

    public void addEnemyBulletUpdate (MainShip mainShip) {
        for (Enemy enemy: activeEnemies) {
            if (enemy.shootThisFrame()) {
                enemyBulletArray.add();
            }
        }
    }

    public void dispose() {
        for (Enemy enemy: activeEnemies)
            enemy.dispose();
    }
}
