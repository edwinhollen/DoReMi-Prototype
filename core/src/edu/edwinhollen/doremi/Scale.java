package edu.edwinhollen.doremi;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Edwin on 10/24/15
 */
public class Scale {

    private List<Note> notes = new LinkedList<>();

    public Scale(Chromatic rootChromatic, Integer rootOctave, ScalePattern pattern){
        int currentChromaticIndex = rootChromatic.ordinal();
        int currentOctave = rootOctave;

        for(int patternIndex = 0; patternIndex < pattern.getValue().length; patternIndex++){
            currentChromaticIndex += pattern.getValue()[patternIndex].getValue();
            if(currentChromaticIndex >= Chromatic.values().length){
                currentChromaticIndex = currentChromaticIndex % Chromatic.values().length;
                currentOctave += 1;
            }
            this.notes.add(new Note(Chromatic.values()[currentChromaticIndex], currentOctave));
        }
    }

    public List<Note> getNotes() {
        return notes;
    }

    public List<Note> getArpeggio(){
        return Arrays.asList(this.notes.get(0), this.notes.get(2), this.notes.get(4), this.notes.get(7));
    }

}
