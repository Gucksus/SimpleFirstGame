package io.github.gucksus.simplefirstgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.gucksus.simplefirstgame.entities.Bulletlv1;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Core extends ApplicationAdapter {
    // Declare variables.
    Texture backgroundTexture;
    Texture shipTexture;
    Texture bulletlv1Texture;
    private SpriteBatch batch;
    Sprite shipSprite;
    Sprite backgroundSprite;
    FitViewport viewport;
    Array <Bulletlv1> bulletlv1Array;

    @Override
    public void create() {
        // Adds texture.
        backgroundTexture = new Texture("background.png");
        shipTexture = new Texture("ShipSprite.png");
        bulletlv1Texture = new Texture("bullet_texture.png");

        // Initialize sprites.
        shipSprite = new Sprite(shipTexture);
        shipSprite.setSize(1, 1);
        shipSprite.setCenterX(4);
        backgroundSprite = new Sprite(backgroundTexture);
        // Height is 22 because the background is supposed to be twice the size to create infinity effect.
        backgroundSprite.setSize(8, 22);

        // Initialize sprite batch and viewport.
        batch = new SpriteBatch();
        viewport = new FitViewport(8,11);
        bulletlv1Array = new Array<>();
    }

    @Override
    public void resize(int width, int height) {
        // CenterCamera must be true to set origin at the bottom left.
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        input();
        clampLogic();
        bulletLogic();
        draw();
    }

    private void input() {
        float speed = 6f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            shipSprite.translateX(-speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            shipSprite.translateX(speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            shipSprite.translateY(speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            shipSprite.translateY(-speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.J)) {
            bulletSpawn();
        }
    }

    private void bulletSpawn() {
        // Maybe this line will be buggy.
        float iniX = shipSprite.getX() + shipSprite.getWidth() / 2;
        float iniY = shipSprite.getY() + shipSprite.getHeight();
        bulletlv1Array.add(new Bulletlv1(bulletlv1Texture, iniX, iniY));
    }

    private void bulletLogic() {
        for (Bulletlv1 bullet: bulletlv1Array) {
            bullet.update(Gdx.graphics.getDeltaTime());
        }
    }

    private void clampLogic() {
        float worldHeight = viewport.getWorldHeight();
        float worldWidth = viewport.getWorldWidth();

        shipSprite.setX(MathUtils.clamp(shipSprite.getX(), -(shipSprite.getWidth()/4), worldWidth - shipSprite.getWidth() / 4 * 3));
        shipSprite.setY(MathUtils.clamp(shipSprite.getY(), 0, worldHeight - shipSprite.getHeight()));
    }

    private void draw() {
        // Clear the screen and get ready for the next frame.
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        // Draw background and ship.
        backgroundSprite.draw(batch);
        shipSprite.draw(batch);

        for (Bulletlv1 bulletlv1: bulletlv1Array) {
            bulletlv1.bulletSelfSprite.draw(batch);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
