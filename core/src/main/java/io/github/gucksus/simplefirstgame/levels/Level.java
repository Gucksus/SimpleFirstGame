package io.github.gucksus.simplefirstgame.levels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import io.github.gucksus.simplefirstgame.entities.Enemy;

public abstract class Level {
    public Array <Enemy> enemyArray;
    public boolean isLevelCompleted = false;
    public float lvTimer = 0;

    public Level() {
        enemyArray = new Array<>();
    }

    public void enemySpawn() {

    }

    public void enemyUpdateRemoval() {
        for (Enemy enemy: enemyArray) {
            enemy.updateStatus();
        }
        for (int i = enemyArray.size - 1; i >= 0; --i) {
            if (enemyArray.get(i).isDead){
                enemyArray.removeIndex(i);
            }
        }
    }

    public void draw(Batch batch) {
        for (Enemy enemy: enemyArray) {
            enemy.sprite.draw(batch);
        }
    }
}
