package io.github.gucksus.simplefirstgame.entities.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import io.github.gucksus.simplefirstgame.entities.MainShip;
import io.github.gucksus.simplefirstgame.tools.BoxWithOffset;
import io.github.gucksus.simplefirstgame.tools.DebugRenderer;
import io.github.gucksus.simplefirstgame.waves.Wave;

/**
 * <b>YOU HAVE TO DECLARE THESE VARIABLE IN SUBCLASSES:</b> <i>health, hitboxOffsetX and hitboxOffsetY, hurtboxOffsetX
 * and hurtboxOffsetY, shootPointOffsetX and shootPointOffsetY, hitbox, hurtbox, bulletTexture, animationIntervalTime, shootAnimationRepeat.</i> <br>
 * <b><i>SET THESE VARIABLES AS 0 IF THE ENEMY DOES NOT HAVE SHOOT OR/AND DEATH ANIMATION: shootAnimationFrameNum, deathAnimationFrameNum.</i></b>
 *
 * **YOU HAVE TO DECLARE THESE VARIABLE IN SUBCLASSES:**
 *
 *
 */
public abstract class Enemy {
    protected float health;
    public Sprite sprite;
    protected Array<BoxWithOffset> hitboxes = new Array<>();
    protected Array<BoxWithOffset> hurtboxes = new Array<>();
    protected float width;
    protected float height;
    protected Array<Vector2> shootPointsOffsets = new Array<>();
    protected Vector2 textureSize;
    protected Vector2 pixelLength;
    Vector2 startPoint = new Vector2();
    Vector2 destination = new Vector2();
    float moveDuration;
    protected float shootFrameInterval = 0.1f;
    protected float deathFrameInterval = 0.1f;
    protected boolean isDead = false;
    public boolean isMoving;
    protected boolean isInvulnerable;
    protected boolean isInvisible;
    protected boolean isHarmless;
    protected boolean shootInThisAnimation;
    protected Texture bulletTexture;
    /**
     * The X difference/distance of each frame compared to the previous frame. So in each frame, this amount is added to make the enemy move. Thus, all enemies move at a constant speed.
     */
    public float nextFrameXDifference;
    /**
     * The Y difference/distance of each frame compared to the previous frame. So in each frame, this amount is added to make the enemy move. Thus, all enemies move at a constant speed.
     */
    public float nextFrameYDifference;
    public float nextFrameAngleDifference;
    public float angle;

    protected Animation<TextureRegion> shootAnimation;
    protected Animation<TextureRegion> deathAnimation;
    protected int shootAnimationFrameNum;
    /**
     * The number of time that the enemy is allowed to shoot.
     */
    protected int shootAnimationRepeat;
    /**
     * Timer for counting the interval between animation.
     */
    float animationIntervalTimer;
    protected float animationInterval;
    protected int deathAnimationFrameNum;
    protected float stateTime;
    enum AnimationType {Static, Shoot, Death}
    AnimationType currentAnimationType = AnimationType.Static;
    protected Array<EnemyBullet> enemyBulletArray = new Array<>();
    SpriteBatch batch;
    DebugRenderer debugRenderer;
    MainShip mainShip;
    float worldWidth;
    float worldHeight;
    public Array<Timer.Task> tasks = new Array<>();
    public Wave wave;
    movingType currentMovingType = movingType.Straight;
    enum movingType {Straight, Circle}

    public Enemy(TextureRegion staticTexture, float iniX, float iniY, float width, float height, float worldWidth, float worldHeight, MainShip mainShip, SpriteBatch batch, DebugRenderer debugRenderer, Wave wave) {
        this.width = width;
        this.height = height;
        textureSize = new Vector2(staticTexture.getRegionWidth(), staticTexture.getRegionHeight());
        pixelLength = new Vector2(width / textureSize.x , height / textureSize.y);
        sprite = new Sprite(staticTexture);
        sprite.setSize(width, height);
        sprite.setPosition(iniX, iniY);
        this.batch = batch;
        this.debugRenderer = debugRenderer;
        this.mainShip = mainShip;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.wave = wave;
    }

    public void initializeShootAnimation(TextureRegion[] shootAnimationFrames) {
        shootAnimation = new Animation<>(shootFrameInterval, shootAnimationFrames);
        this.shootAnimationFrameNum = shootAnimationFrames.length;
        stateTime = 0;
    }

    public void initializeDeathAnimation(TextureRegion[] deathAnimationFrames) {
        deathAnimation = new Animation<>(deathFrameInterval, deathAnimationFrames);
        this.deathAnimationFrameNum = deathAnimationFrames.length;
        stateTime = 0;
    }

    public void update() {
        addEnemyBulletUpdate();
        hitboxUpdate();
        hurtboxUpdate();
        updateStatus();
        bulletUpdate();
        bulletHitboxUpdate();
        moveUpdate();
        updateEnemyHitboxAndHurtboxWhenMoved();
    }

    void addTask(Timer.Task task) {
        Timer.schedule(task, wave.previousDuration + wave.waveEnemyArray.indexOf(this, true) * wave.interval);
        tasks.add(task);
    }

    void moveUpdate() {
        switch (currentMovingType) {
            case Straight:
                moveStraightUpdate();
                break;
            case Circle:
                moveCircleUpdate();
        }
    }

    public void moveCircle(float duration) {
        Vector2 tempCenterPoint = wave.centerPoint;
        Timer.Task task = new Timer.Task() {
            @Override
            public void run() {
                isMoving = true;
                currentMovingType = movingType.Circle;
                moveDuration = duration;
                Vector2 thisToCenter = new Vector2(getCenter().x - tempCenterPoint.x, getCenter().y - tempCenterPoint.y);
                angle = thisToCenter.angleRad();
            }
        };
        addTask(task);
    }

    public void moveStraight(float duration) {
        Vector2 tempStartPoint = new Vector2(wave.startPoint);
        Vector2 tempDestination = new Vector2(wave.destination);
        Timer.Task task = new Timer.Task() {
            @Override
            public void run() {
                isMoving = true;
                currentMovingType = movingType.Straight;
                startPoint = tempStartPoint;
                destination = tempDestination;
                moveDuration = duration;
            }
        };
        addTask(task);
    }

    public void moveCircleUpdate() {
        float delta = Gdx.graphics.getDeltaTime();
        nextFrameAngleDifference = wave.clockwiseMultiplier * (wave.revolutionNum * MathUtils.PI2 / moveDuration * delta);
        if (isMoving) {
            angle += nextFrameAngleDifference;
            Vector2 nextPoint = new Vector2();
            nextPoint.x = wave.centerPoint.x + wave.radius * MathUtils.cos(angle);
            nextPoint.y = wave.centerPoint.y + wave.radius * MathUtils.sin(angle);
            sprite.setCenter(nextPoint.x, nextPoint.y);
        }
    }

    public void moveStraightUpdate() {
        float delta = Gdx.graphics.getDeltaTime();
        nextFrameXDifference = (destination.x - startPoint.x) / moveDuration * delta;
        nextFrameYDifference = (destination.y - startPoint.y) / moveDuration * delta;
        if (isMoving) {
            sprite.translate(nextFrameXDifference, nextFrameYDifference);
        }
    }

    public void updateEnemyHitboxAndHurtboxWhenMoved() {
        for (BoxWithOffset hitbox: hitboxes) {
            hitbox.update(sprite.getX(), sprite.getY());
        }
        for (BoxWithOffset hurtbox: hurtboxes) {
            hurtbox.update(sprite.getX(), sprite.getY());
        }
    }

    public void addEnemyBulletUpdate () {
        EnemyBullet enemyBullet = shoot(mainShip);
        if (enemyBullet != null) {
            enemyBulletArray.add(enemyBullet);
        }
    }

    public void bulletUpdate() {
        for (EnemyBullet enemyBullet: enemyBulletArray) {
            enemyBullet.update();
        }
    }

    public void updateStatus() {
        if (health <= 0 && currentAnimationType != AnimationType.Death) {
            isDead = true;
            isInvulnerable = true;
            isHarmless = true;
            cancelAllTasks();
            triggerDeathAnimation();
        }
    }

    public void triggerShootAnimation() {
        if (shootAnimationFrameNum != 0) {
            currentAnimationType = AnimationType.Shoot;
            stateTime = 0;
        }
    }

    public void triggerDeathAnimation() {
        if (deathAnimationFrameNum != 0) {
            currentAnimationType = AnimationType.Death;
            stateTime = 0;
        }
    }

    public void draw() {
        drawAnimation();
        drawBullet();
    }

    public void drawDebug() {
        drawBulletDebug();
        drawHitbox();
    }

    public void drawAnimation() {
        float delta = Gdx.graphics.getDeltaTime();
        switch (currentAnimationType) {
            case Static:
                sprite.draw(batch);
                // If the number of times that the shoot animation needs to repeat is not 0 then it needs to keep track of intervals.
                if (shootAnimationRepeat != 0 && animationIntervalTimer < animationInterval) {
                    animationIntervalTimer += delta;
                } else if (shootAnimationRepeat != 0 && animationIntervalTimer >= animationInterval) {
                    shootAnimationRepeat--;
                    animationIntervalTimer = 0;
                    shootInThisAnimation = false;
                    triggerShootAnimation();
                }
                break;
            case Shoot:
                if (shootAnimationFrameNum != 0) {
                    stateTime += delta;
                    animationIntervalTimer = 0;
                    TextureRegion currentFrame = shootAnimation.getKeyFrame(stateTime);
                    if (shootAnimation.isAnimationFinished(stateTime)) {
                        currentAnimationType = AnimationType.Static;
                    }
                    batch.draw(currentFrame, sprite.getX(), sprite.getY(), width, height);
                }
                break;
            case Death:
                if (deathAnimationFrameNum != 0) {
                    stateTime += delta;
                    TextureRegion currentFrame = deathAnimation.getKeyFrame(stateTime);
                    batch.draw(currentFrame, sprite.getX(), sprite.getY(), width, height);
                }
        }
    }

    void drawHitbox() {
        for (BoxWithOffset hitbox : hitboxes) {
            debugRenderer.drawHitbox(hitbox.getBox());
        }
        for (BoxWithOffset hurtbox: hurtboxes) {
            debugRenderer.drawHurtbox(hurtbox.getBox());
        }
    }

    void drawBullet() {
        for (EnemyBullet enemyBullet: enemyBulletArray) {
            enemyBullet.sprite.draw(batch);
        }
    }

    void drawBulletDebug() {
        for (EnemyBullet enemyBullet: enemyBulletArray) {
            if (enemyBullet.isCircle) {
                debugRenderer.drawCircleHitbox(enemyBullet.circleHitbox);
            } else {
                debugRenderer.drawHitbox(enemyBullet.rectangleHitbox);
            }
        }
    }

    public boolean isDeathAnimationFinished() {
        if (deathAnimationFrameNum == 0)
            return true;
        else
            return deathAnimation.isAnimationFinished(stateTime);
    }

    /**
     * @return Whether the shoot animation reach the frame where the enemy needs to shoot or not.
     */
    protected abstract boolean shootThisFrame();

    /**
     *
     * @param shootPointX The X coordinate of the shoot point.
     * @param shootPointY The Y coordinate of the shoot point.
     * @param dx The X direction of the vector.
     * @param dy The Y direction of the vector.
     * @return The bullet type of this enemy.
     */
    protected abstract EnemyBullet returnBulletType(float shootPointX, float shootPointY, float dx, float dy);

    /**
     *
     * @param mainShip The ship that needs to be shot.
     * @return A bullet if the conditions are met. Otherwise, it returns null.
     */
    public EnemyBullet shoot(MainShip mainShip) {
        if (shootThisFrame() && !shootInThisAnimation && !isDead && shootAnimationFrameNum != 0) {
            shootInThisAnimation = true;
            for (Vector2 shootPointOffset: shootPointsOffsets) {
                float shootPointX = sprite.getX() + shootPointOffset.x;
                float shootPointY = sprite.getY() + shootPointOffset.y;
                float dx = mainShip.getShipHurtboxCenterX() - shootPointX;
                float dy = mainShip.getShipHurtboxCenterY() - shootPointY;
                return returnBulletType(shootPointX, shootPointY, dx, dy);
            }
        }
        else return null;
        return null;
    }

    void hitboxUpdate() {
        for (BoxWithOffset hitbox: hitboxes) {
            if (Intersector.overlaps(mainShip.shipHurtbox, hitbox.getBox()) && mainShip.timerSinceLastDamage > mainShip.invulnerableDuration && !isHarmless)
                mainShip.takeDamage();
        }
    }

    void hurtboxUpdate() {
        for (BoxWithOffset hurtbox: hurtboxes) {
            for (int i = mainShip.bulletArray.size - 1; i >= 0; i--) {
                Bullet bullet = mainShip.bulletArray.get(i);
                if (Intersector.overlaps(bullet.hitbox, hurtbox.getBox()) && !isInvulnerable) {
                    health -= bullet.getDamage();
                    mainShip.bulletArray.removeIndex(i);
                }
            }
        }
    }

    public void bulletHitboxUpdate() {
        for (EnemyBullet enemyBullet: enemyBulletArray) {
            if (enemyBullet.isCircle) {
                if (Intersector.overlaps(enemyBullet.circleHitbox, mainShip.shipHurtbox)) {
                    mainShip.takeDamage();
                }
            } else {
                if (Intersector.overlaps(mainShip.shipHurtbox, enemyBullet.rectangleHitbox)) {
                    mainShip.takeDamage();
                }
            }
        }
    }

    void cancelAllTasks() {
        for (Timer.Task task: tasks) if (task.isScheduled())
            task.cancel();
    }

    public boolean getIsDead() {
        return isDead;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Vector2 getCenter() {
        return new Vector2(sprite.getX() + width / 2, sprite.getY() + height / 2);
    }
}
