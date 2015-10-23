package edu.edwinhollen.doremi;

/**
 * Created by Edwin on 10/22/15
 */
public class Notes {
    public static final int R = 0, H = 1, W = 2;
    public static final class ScalePatterns{
        public final static ScalePattern
            major =     new ScalePattern(R,W,W,H,W,W,W,H),
            minor =     new ScalePattern(R,W,H,W,W,H,W,W),
            dorian =    new ScalePattern(R,W,H,W,W,W,H,W);
    }

}
