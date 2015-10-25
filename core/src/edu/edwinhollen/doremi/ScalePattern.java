package edu.edwinhollen.doremi;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Edwin on 10/23/15
 */
public class ScalePattern {
    private static final int WHOLE_STEP = 2, HALF_STEP = 1, ROOT = 0;
    final List<Integer> scaleSteps;
    final String name;
    public ScalePattern(String name, Integer... scaleSteps){
        this.name = name;
        this.scaleSteps = Arrays.asList(scaleSteps);
    }

    public ScalePattern(String name, char[] scaleStepsChars){
        this.name = name;
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


    @Override
    public String toString() {
        return this.name;
    }
}
