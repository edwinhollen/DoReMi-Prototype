package edu.edwinhollen.doremi;

/**
 * Created by Edwin on 10/23/15
 */
public class ScalePatterns {
    /**
     * These are just some static scale patterns
     */
    public static final ScalePattern
            major = new ScalePattern("major", "RWWHWWWH".toCharArray()),
            minor = new ScalePattern("minor", "RWHWWWWH".toCharArray()),
            dorian = new ScalePattern("dorian", "RWHWWWHW".toCharArray()),
            chromatic = new ScalePattern("chromatic", "RHHHHHHHHHHH".toCharArray());
}
