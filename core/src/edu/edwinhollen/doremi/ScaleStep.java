package edu.edwinhollen.doremi;

/**
 * Created by Edwin on 11/18/15
 */
public enum ScaleStep {
    WHOLE(2), HALF(1), WHOLEHALF(3), ROOT(0);

    private final int stepSize;

    ScaleStep(int i) {
        this.stepSize = i;
    }

    public int getValue() {
        return stepSize;
    }
}
