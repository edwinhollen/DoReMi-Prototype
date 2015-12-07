package edu.edwinhollen.doremi;

/**
 * Created by Edwin on 10/23/15
 */
public enum ScalePattern {
    /**
     * These are just some static scale patterns
     */

    MAJOR(ScaleStep.ROOT, ScaleStep.WHOLE, ScaleStep.WHOLE, ScaleStep.HALF, ScaleStep.WHOLE, ScaleStep.WHOLE, ScaleStep.WHOLE, ScaleStep.HALF),
    MINOR(ScaleStep.ROOT, ScaleStep.WHOLE, ScaleStep.HALF, ScaleStep.WHOLE, ScaleStep.WHOLE, ScaleStep.WHOLE, ScaleStep.WHOLE, ScaleStep.HALF),
    DORIAN(ScaleStep.ROOT, ScaleStep.WHOLE, ScaleStep.HALF, ScaleStep.WHOLE, ScaleStep.WHOLE, ScaleStep.WHOLE, ScaleStep.HALF, ScaleStep.WHOLE),
    CHROMATIC(ScaleStep.ROOT, ScaleStep.HALF, ScaleStep.HALF, ScaleStep.HALF, ScaleStep.HALF, ScaleStep.HALF, ScaleStep.HALF, ScaleStep.HALF, ScaleStep.HALF, ScaleStep.HALF, ScaleStep.HALF, ScaleStep.HALF);

    private final ScaleStep[] steps;
    ScalePattern(ScaleStep... steps){
        this.steps = steps;
    }
    public ScaleStep[] getValue(){
        return this.steps;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}