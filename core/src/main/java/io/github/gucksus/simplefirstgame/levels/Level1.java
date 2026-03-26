package io.github.gucksus.simplefirstgame.levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import io.github.gucksus.simplefirstgame.entities.PopcornEnemy;
import io.github.gucksus.simplefirstgame.waves.Wave;

public class Level1 extends Level {

    public Level1() {
        super();
        popcornEnemyTexture = new Texture("enemylv1.png");
    }

    public void popcornEnemySpawn(Texture texture ,float iniX, float iniY) {
        activeEnemies.add(new PopcornEnemy(popcornEnemyTexture, iniX, iniY));
    }

    @Override
    public void enemySpawn(float delta, float worldWidth, float worldHeight) {
        waveArray.add(new Wave(activeEnemies, 5, .2f));

        waveArray.first().moveStraight(0, 10, 0, 3, 3, delta);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                waveArray.first().moveStraight(0, 3, 0, 10, 1, delta);
            }
        }, 3f);
    }
}
