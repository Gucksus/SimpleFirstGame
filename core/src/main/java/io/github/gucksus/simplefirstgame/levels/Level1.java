package io.github.gucksus.simplefirstgame.levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import io.github.gucksus.simplefirstgame.entities.PopcornEnemy;

public class Level1 extends Level {
    Texture popcornEnemyTexture;
    public float swarmTimerSinceLastSpawn;
    float popcornInterval = .75f;

    public Level1() {
        super();
        popcornEnemyTexture = new Texture("enemylv1.png");
        swarmTimerSinceLastSpawn = popcornInterval;
    }

    public void popcornEnemySpawn(Texture texture ,float iniX, float iniY) {
        enemyArray.add(new PopcornEnemy(texture, iniX, iniY));
    }

    @Override
    public void enemySpawn(float delta, float worldWidth, float worldHeight) {
        swarmTimerSinceLastSpawn += delta;
        if (swarmTimerSinceLastSpawn >= popcornInterval){
            popcornEnemySpawn(popcornEnemyTexture, MathUtils.random(worldWidth / 6f, worldWidth * (1 - 1 / 6f)), worldHeight);
            swarmTimerSinceLastSpawn = 0;
        }
    }
}
