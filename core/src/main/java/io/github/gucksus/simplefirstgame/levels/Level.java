package io.github.gucksus.simplefirstgame.levels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import io.github.gucksus.simplefirstgame.entities.Enemy;
import io.github.gucksus.simplefirstgame.tools.DebugRenderer;

public abstract class Level {
    public Array <Enemy> enemyArray;
    public boolean isLevelCompleted = false;
    public float lvTimer = 0;
    DebugRenderer debugRenderer;

    public Level() {
        enemyArray = new Array<>();
        debugRenderer = new DebugRenderer();
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

    public void drawEnemyHitboxAndHurtBox(ShapeRenderer shapeRenderer){
        for (Enemy enemy: enemyArray){
            debugRenderer.drawHitbox(enemy.hitbox, shapeRenderer);
            debugRenderer.drawHurtbox(enemy.hurtbox, shapeRenderer);
        }
    }
}
