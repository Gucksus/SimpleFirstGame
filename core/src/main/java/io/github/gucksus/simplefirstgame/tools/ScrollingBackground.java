package io.github.gucksus.simplefirstgame.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ScrollingBackground {
    Texture backgroundAnimationSheet;
    float speed = 3f;
    Animation<TextureRegion> backgroundAnimation;
    float stateTime;
    final int FRAME_NUM = 12;
    final float frameDuration = 0.3f;
    boolean isInAnimation;
    Weather[] weatherType = {
        new Weather("Overcast", .2f, .022f, 2),
        new Weather("Sunny", .5f, .011f, 3),
        new Weather("Windy", .3f, .003f, 1)
    };
    Weather currentWeather;

    BGAnimation[] animationsType = {
        new BGAnimation(1, 5, .67f),
        new BGAnimation(6, 11, .33f)
    };
    BGAnimation currentAnimationType;
    float timer = 0;

    public ScrollingBackground(float worldHeight) {
        backgroundAnimationSheet = new Texture("background_animation_sheet4.png");
        TextureRegion[][] tmp = TextureRegion.split(backgroundAnimationSheet, backgroundAnimationSheet.getWidth() / FRAME_NUM, backgroundAnimationSheet.getHeight());
        TextureRegion[] backgroundTileAnimationFrames = new TextureRegion[FRAME_NUM];
        System.arraycopy(tmp[0], 0, backgroundTileAnimationFrames, 0, FRAME_NUM);
        backgroundAnimation = new Animation<>(frameDuration, backgroundTileAnimationFrames);
        currentWeather = chooseAWeather();
        currentAnimationType = chooseAAnimationType();
    }

    Weather chooseAWeather() {
        double randomNum = Math.random();
        float cumulativeProb = 0;
        for (Weather weather: weatherType) {
            cumulativeProb += weather.probability;
            if (cumulativeProb <= randomNum)
                return weather;
        }
        return weatherType[weatherType.length - 1];
    }

    void checkForAnimationTrigger() {
        double randomNum = Math.random();
        if (!isInAnimation && randomNum <= currentWeather.chanceOfTriggeringBGAnimation && timer > currentWeather.interval) {
            isInAnimation = true;
            currentAnimationType = chooseAAnimationType();
            timer = 0;
            System.out.println(1);
        }
    }

    BGAnimation chooseAAnimationType() {
        double randomNum = Math.random();
        double cumulativeProb = 0;
        for (BGAnimation animation: animationsType) {
            cumulativeProb += animation.probability;
            if (cumulativeProb <= randomNum)
                return animation;
        }
        return animationsType[animationsType.length - 1];
    };

    public void backgroundUpdate(float delta) {
        timer += delta;
        checkForAnimationTrigger();
    }

    public void draw(Batch batch, float delta) {
        TextureRegion currentFrame = backgroundAnimation.getKeyFrame(stateTime, false);
        if (isInAnimation) {
            if (stateTime == 0)
                stateTime = (currentAnimationType.start + 1) * frameDuration;
            else
                stateTime += delta;
            currentFrame = backgroundAnimation.getKeyFrame(stateTime, false);
            if (backgroundAnimation.getKeyFrameIndex(stateTime) == currentAnimationType.end - 1) {
                stateTime = 0;
                isInAnimation = false;
            }
        }
        float width = 2;
        float height = 2;
        for (float i = 0; i < 8; i += width) {
            for (float j = 0; j < 11; j += height) {
                batch.draw(currentFrame, i, j, width, height);
            }
        }
    }

    public void dispose() {
        backgroundAnimationSheet.dispose();
    }
}
