package io.github.gucksus.simplefirstgame.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class DebugRenderer {
    public DebugRenderer() {
    }

    public void drawHitbox(Rectangle hitbox, ShapeRenderer shapeRenderer) { // Draw hitboxes using a shape renderer.
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    public void drawHurtbox(Rectangle hurtbox, ShapeRenderer shapeRenderer) { // Draw hurtbox the same as draw hitbox.
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(hurtbox.x, hurtbox.y, hurtbox.width, hurtbox.height);
    }
}
