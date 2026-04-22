package io.github.gucksus.simplefirstgame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.gucksus.simplefirstgame.tools.DebugRenderer;

public class Constants {
    public float worldWidth;
    public float worldHeight;
    public SpriteBatch batch;
    public DebugRenderer debugRenderer;
    public boolean debugMode;

    public Constants(float worldWidth, float worldHeight, SpriteBatch batch,
            DebugRenderer debugRenderer, boolean debugMode) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.batch = batch;
        this.debugRenderer = debugRenderer;
        this.debugMode = debugMode;
    }
}
