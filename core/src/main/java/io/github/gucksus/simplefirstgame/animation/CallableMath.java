package io.github.gucksus.simplefirstgame.animation;

public interface CallableMath<T> {
    /**
     * A method for `AnimationScheduler` to be able to get the value of the selected math class.
     *
     * @param progress A normalized float ranging from 0.0 to 1.0.
     * @return Your math class values.
     */
    public T get(float progress);
}
