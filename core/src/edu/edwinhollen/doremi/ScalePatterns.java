package edu.edwinhollen.doremi;

/**
 * Created by Edwin on 10/23/15
 */
public enum ScalePatterns {
    major(new ScalePattern("RWWHWWWH".toCharArray())),
    minor(new ScalePattern("RWHWWHWW".toCharArray())),
    dorian(new ScalePattern("RWHWWWHW".toCharArray()));

    ScalePatterns(ScalePattern sp) {
    }
}
