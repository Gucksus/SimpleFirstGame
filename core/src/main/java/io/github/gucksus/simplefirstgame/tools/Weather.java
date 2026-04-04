package io.github.gucksus.simplefirstgame.tools;

public class Weather {
    public String name;
    public float probability;
    // How windy it is.
    public float chanceOfTriggeringBGAnimation;
    public float interval;

    public Weather(String name, float probability, float chanceOfTriggeringBGAnimation, float interval) {
        this.name = name;
        this.probability = probability;
        this.chanceOfTriggeringBGAnimation = chanceOfTriggeringBGAnimation;
        this.interval = interval;
    }
}
