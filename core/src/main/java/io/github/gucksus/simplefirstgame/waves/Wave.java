package io.github.gucksus.simplefirstgame.waves;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import io.github.gucksus.simplefirstgame.entities.base.Enemy;
import io.github.gucksus.simplefirstgame.entities.base.Level;

public class Wave {
    protected Array<Enemy> activeEnemyArray;
    public Array<Enemy> waveEnemyArray = new Array<>();
    public int totalEnemies;
    /**
     * The interval between updating each enemy in the wave.
     */
    public float interval;
    public boolean isDone;
    public Array<Vector2> path = new Array<>();
    public Vector2 centerPoint;
    Enemy centerEnemy;
    public float radius;
    public float previousDuration;
    float worldWidth;
    float worldHeight;
    public float revolutionNum;
    public float clockwiseMultiplier = -1;
    public Level level;

    /**
     * Create a new wave of enemy.
     *
     * @param activeEnemyArray The array that stored active enemies, this should be passed down by a
     *        level.
     * @param totalEnemies The number of enemy this wave holds.
     * @param interval The interval between updating each enemy in the wave.
     * @param startX The initial X coordinate before a position update.
     * @param startY The initial Y coordinate before a position update.
     */
    public Wave(Array<Enemy> activeEnemyArray, int totalEnemies, float interval, float startX,
            float startY, float worldWidth, float worldHeight, Level level) {
        this.activeEnemyArray = activeEnemyArray;
        this.totalEnemies = totalEnemies;
        this.interval = interval;
        path.add(new Vector2(startX, startY));
        path.add(new Vector2());
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.level = level;
    }

    public void addEnemy(Enemy enemy) {
        waveEnemyArray.add(enemy);
        activeEnemyArray.add(enemy);
    }

    /**
     * This method calls for enemies' status update. If the conditions are met, it will remove
     * enemies.
     */
    public void enemyUpdateRemoval() {
        for (int i = waveEnemyArray.size - 1; i >= 0; --i) {
            Enemy enemy = waveEnemyArray.get(i);
            // If the enemy is dead and finished death animation.
            if (enemy.getIsDead() && enemy.isDeathAnimationFinished()) {
                activeEnemyArray.removeValue(enemy, true);
                waveEnemyArray.removeIndex(i);
            }
        }
    }

    void updateCenterPoint() {
        if (centerEnemy != null) {
            centerPoint = centerEnemy.getCoordinate();
        }
    }

    public void update() {
        enemyUpdateRemoval();
        updateCenterPoint();
    }

    public void moveAllEnemyStraight(float endX, float endY, float duration) {
        // Here the duration is the amount of time it takes for the first enemy to reach the
        // destination.
        path.peek().set(endX, endY);
        for (Enemy enemy : waveEnemyArray) {
            enemy.moveStraight(duration);
        }
        path.first().set(path.peek());
        previousDuration += duration;
    }

    public void moveAllEnemyInCircle(Vector2 center, float revolutionNum, float duration,
            boolean counterClockwise) {
        if (counterClockwise)
            clockwiseMultiplier = 1;
        else
            clockwiseMultiplier = -1;

        centerPoint = center;
        this.revolutionNum = revolutionNum;
        Vector2 firstEnemyToCenter = new Vector2(path.first().x - center.x, path.first().y - center.y);
        radius = firstEnemyToCenter.len();

        for (Enemy enemy : waveEnemyArray) {
            enemy.moveCircle(duration);
        }

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                path.first().set(waveEnemyArray.first().getCoordinate().x,
                        waveEnemyArray.first().getCoordinate().y);
            }
        }, duration);


        previousDuration += duration;
    }

    public void moveAllEnemyInCircle(Enemy center, float revolutionNum, float duration,
            boolean counterClockwise) {
        if (counterClockwise)
            clockwiseMultiplier = 1;
        else
            clockwiseMultiplier = -1;

        centerEnemy = center;
        centerPoint.set(center.getCoordinate().x, center.getCoordinate().y);
        this.revolutionNum = revolutionNum;
        Enemy firstEnemy = waveEnemyArray.first();
        Vector2 firstEnemyToCenter = new Vector2(firstEnemy.getCoordinate().x - centerPoint.x,
                firstEnemy.getCoordinate().y - centerPoint.y);
        radius = firstEnemyToCenter.len();

        for (Enemy enemy : waveEnemyArray) {
            enemy.moveCircle(duration);
        }

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                path.first().set(waveEnemyArray.first().getCoordinate().x,
                        waveEnemyArray.first().getCoordinate().y);
            }
        }, duration);


        previousDuration += duration;
    }

    public void moveAllEnemyCurve(Vector2[] points, float duration) {
        Vector2 tempStartPoint = path.first();
        path.clear();
        path.add(tempStartPoint);
        for (Vector2 point: points) {
            path.add(point);
        }

        for (Enemy enemy: waveEnemyArray) {
            enemy.moveCurve(duration);
        }

        previousDuration += duration;
    }
}
