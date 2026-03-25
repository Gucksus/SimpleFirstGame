package io.github.gucksus.simplefirstgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;

public class MainShip {
    Texture shipTexture;
    Texture basicBulletTexture;
    public Circle shipHurtbox;
    Sprite shipSprite;
    public Array<Bullet> bulletArray;
    float width;
    float height;
    float shipSpeed = 6f;
    Bullet currentBullet;
    float timerSinceLastShot;
    public short lives = 3;
    public float timerSinceLastDamage;
    public float invulnerableDuration = 1f;
    public boolean isDead = false;

    public MainShip(float centerX, float iniY, float width, float height, float hurtboxRadius) {
        this.width = width;
        this.height = height;
        timerSinceLastDamage = invulnerableDuration;
        shipTexture = new Texture("ShipSprite.png");
        basicBulletTexture = new Texture("bullet_texture.png");
        shipSprite = new Sprite(shipTexture);
        shipSprite.setSize(1, 1);
        shipSprite.setCenterX(centerX);
        shipSprite.setY(iniY);
        shipHurtbox = new Circle(centerX, iniY + height / 2, hurtboxRadius);
        bulletArray = new Array<>();
        currentBullet = new BasicBullet(basicBulletTexture, 69, 69);
    }

    public void update(float delta, float worldWidth, float worldHeight) {
        timerSinceLastShot += delta;
        timerSinceLastDamage += delta;
        input(delta);
        clampLogic(worldWidth, worldHeight);
        // Update hurtbox position for the ship.
        shipHurtbox.setPosition(shipSprite.getX() + width / 2, shipSprite.getY() + height / 2);
        updateBullet(delta, worldHeight);
    }

    private void input(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            shipSprite.translateX(-shipSpeed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            shipSprite.translateX(shipSpeed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            shipSprite.translateY(shipSpeed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            shipSprite.translateY(-shipSpeed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.J)) {
            if (bulletArray.size < currentBullet.maxBulletOnScreen && timerSinceLastShot >= currentBullet.fireRate) { // If the amount of bullet on screen is smaller than max amount, then allow shooting.
                float iniX = shipSprite.getX() + shipSprite.getWidth() / 2;
                float iniY = shipSprite.getY() + shipSprite.getHeight();
                bulletArray.add(new BasicBullet(basicBulletTexture , iniX, iniY));
                timerSinceLastShot = 0;
            }
        }
    }

    private void clampLogic(float worldWidth, float worldHeight) { // Clamp logic for the ship.
        // Here I set so that the ship can go off-screen a quarter of its width.
        shipSprite.setX(MathUtils.clamp(shipSprite.getX(), -(shipSprite.getWidth()/4), worldWidth - shipSprite.getWidth() / 4 * 3));
        shipSprite.setY(MathUtils.clamp(shipSprite.getY(), 0, worldHeight - shipSprite.getHeight()));
    }

    private void updateBullet(float delta, float worldHeight) {
        for (int i = bulletArray.size - 1; i >= 0; i--){
            bulletArray.get(i).update(delta);
        }

        for (int i = bulletArray.size - 1; i >= 0; i--) {
            Sprite currentBulletSprite = bulletArray.get(i).sprite;
            if (currentBulletSprite.getY() > worldHeight) {
                bulletArray.removeIndex(i);
            }
        }
    }

    public void draw(Batch batch) {
        if (!isDead) {
            shipSprite.draw(batch);
        }
        for (Bullet basicBullet : bulletArray) {
            basicBullet.sprite.draw(batch);
        }
    }

    public void drawShipHurtbox(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(shipHurtbox.x, shipHurtbox.y, shipHurtbox.radius, 32);
    }

    public void drawBulletHitbox(ShapeRenderer shapeRenderer) {
        Rectangle currentBulletHitbox = currentBullet.hitbox;
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(currentBulletHitbox.x, currentBulletHitbox.y, currentBulletHitbox.width, currentBulletHitbox.height);
    }
}
