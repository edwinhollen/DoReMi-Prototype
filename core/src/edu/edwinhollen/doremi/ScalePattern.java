package edu.edwinhollen.doremi;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Edwin on 10/23/15
 */
public class ScalePattern {
    List<Integer> scaleSteps;
    public ScalePattern(Integer... scaleSteps){
        this.scaleSteps = Arrays.asList(scaleSteps);
    }
}
