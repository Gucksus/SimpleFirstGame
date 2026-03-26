package io.github.gucksus.simplefirstgame.waves;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import io.github.gucksus.simplefirstgame.entities.Enemy;


public class Wave {
    Texture popcornEnemyTexture;
    protected Array <Enemy> activeEnemies;
    Array <Enemy> waveEnemies;
    float movingInterval;
    int totalEnemies;

    public Wave(Array<Enemy> activeEnemies, int totalEnemies, float spawnInterval) {
        this.activeEnemies = activeEnemies;
        waveEnemies = new Array<>();
        this.totalEnemies = totalEnemies;
        this.movingInterval = spawnInterval;
        popcornEnemyTexture = new Texture("enemylv1.png");
    }

    public void enemyUpdateRemoval() {
        for (Enemy enemy: waveEnemies) {
            enemy.updateStatus();
        }
        for (int i = waveEnemies.size - 1; i >= 0; --i) {
            if (waveEnemies.get(i).isDead){
                activeEnemies.removeValue(waveEnemies.get(i), true);
                waveEnemies.removeIndex(i);
            }
        }
    }

    public void moveStraight(float startX, float startY, float endX, float endY, float duration, float delta, float interval){
        waveEnemies.first().isMoving = true;
        waveEnemies.first().nextFrameXDifference = (endX - startX) / duration * delta;
        waveEnemies.first().nextFrameYDifference = (endY - startY) / duration * delta;
        for (int i = 1; i < waveEnemies.size; i++) {
            final int idx = i;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    Enemy enemy = waveEnemies.get(idx);
                    enemy.isMoving = true;
                    enemy.nextFrameXDifference = (endX - startX) / duration * delta;
                    enemy.nextFrameYDifference = (endY - startY) / duration * delta;
                }
            }, i * interval);
        }
    }

    public void updateEnemyIsMovingStatus(float delta) {
        for (Enemy enemy: waveEnemies) {
            enemy.updatePosition(delta);
        }
    }
}
