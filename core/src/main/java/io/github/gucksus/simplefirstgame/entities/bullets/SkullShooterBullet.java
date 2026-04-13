package io.github.gucksus.simplefirstgame.entities.bullets;

import com.badlogic.gdx.graphics.Texture;
import io.github.gucksus.simplefirstgame.entities.base.EnemyBullet;

public class SkullShooterBullet extends EnemyBullet {
    public SkullShooterBullet(Texture texture, float iniX, float iniY, float width, float height, float shootAngle) {
        super(texture, iniX, iniY, width, height, shootAngle);
        movingType = MovingType.Straight;
        isCircle = true;
    }
}
