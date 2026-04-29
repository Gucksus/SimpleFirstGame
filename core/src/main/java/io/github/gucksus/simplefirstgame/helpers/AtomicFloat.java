package io.github.gucksus.simplefirstgame.helpers;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicFloat {
    private AtomicInteger shush = new AtomicInteger();

    public AtomicFloat(float value) {
        shush.set(Float.floatToIntBits(value));
    }

    public void set(float value) {
        shush.set(Float.floatToIntBits(value));
    }

    public float get() {
        return Float.intBitsToFloat(shush.get());
    }

}
