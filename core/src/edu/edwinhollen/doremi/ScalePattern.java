package edu.edwinhollen.doremi;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Edwin on 10/23/15
 */
public class ScalePattern {
    private static final int WHOLE_STEP = 2, HALF_STEP = 1, ROOT = 0;
    List<Integer> scaleSteps;
    public ScalePattern(Integer... scaleSteps){
        this.scaleSteps = Arrays.asList(scaleSteps);
    }

    public ScalePattern(char[] scaleStepsChars){
        this.scaleSteps = new LinkedList<>();
        for(char c : scaleStepsChars){
            switch(c){
                case 'W':
                    this.scaleSteps.add(WHOLE_STEP);
                    break;
                case 'H':
                    this.scaleSteps.add(HALF_STEP);
                    break;
                case 'R':
                    this.scaleSteps.add(ROOT);
                default:
                    break;
            }
        }
    }
}
