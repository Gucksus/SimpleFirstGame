package io.github.gucksus.simplefirstgame.entities.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import io.github.gucksus.simplefirstgame.entities.MainShip;
import io.github.gucksus.simplefirstgame.tools.DebugRenderer;
import io.github.gucksus.simplefirstgame.waves.Wave;


public abstract class Level {
    protected boolean isLevelCompleted = false;
    protected boolean debugMode = false;
    protected float lvTimer = 0;
    protected DebugRenderer debugRenderer;
    protected Array<Enemy> activeEnemies;
    protected Array<Wave> waveArray;
    protected boolean isLevelStarted;
    protected float lastDelta;
    protected float worldWidth;
    protected float worldHeight;
    protected MainShip mainShip;
    protected SpriteBatch batch;

    public Level(float worldWidth, float worldHeight, SpriteBatch batch, MainShip mainShip, DebugRenderer debugRenderer) {
        lastDelta = 67;
        activeEnemies = new Array<>();
        waveArray = new Array<>();
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.batch = batch;
        this.mainShip = mainShip;
        this.debugRenderer = debugRenderer;
    }

    public abstract void enemySpawn();

    public abstract void enemySpawnDebug();

    public void drawEnemy() {
        for (Enemy enemy : activeEnemies)
            if (!enemy.isInvisible) {
                enemy.draw();
            }
    }

    public void drawDebug() {
        for (Enemy enemy: activeEnemies) {
            enemy.drawDebug();
        }
    }

    public void update() {
        updateDelta();
        wavesUpdate();
        updateEnemy();
    }

    void updateEnemy() {
        for(Enemy enemy: activeEnemies) {
            enemy.update();
        }
    }

    public void wavesUpdate() {
        for (int i = waveArray.size - 1; i >= 0; i--) {
            Wave wave = waveArray.get(i);
            wave.update();
            if (wave.isDone) {
                waveArray.removeIndex(i);
            }
        }
    }

    public void updateDelta () {
        lastDelta = Gdx.graphics.getDeltaTime();
    }

    /**
     * This method is used to trigger start the level only when libGdx is fully stable.
     */
    public void startLevelIfHaveNotStarted () {
        float delta = Gdx.graphics.getDeltaTime();
        if (!this.isLevelStarted && Math.abs(lastDelta - delta) <= .0001f) {
            if (!debugMode) {
                this.enemySpawn();
            }
            else {
                this.enemySpawnDebug();
            }
            this.isLevelStarted = true;
            System.out.println(delta);
        }
    }
}
