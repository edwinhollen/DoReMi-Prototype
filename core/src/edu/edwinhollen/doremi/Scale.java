package edu.edwinhollen.doremi;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Edwin on 10/24/15
 */
public class Scale {

    private final ScalePattern pattern;
    private List<Note> notes = new LinkedList<>();

    public Scale(Chromatic rootNote, Integer rootOctave, ScalePattern pattern){
        Integer currentOctave = rootOctave;
        this.pattern = pattern;

        Integer currentNoteIndex = 0;
        while(!Chromatic.values()[currentNoteIndex].equals(rootNote)){
            currentNoteIndex++;
        }

        while(this.notes.size() < 8){
            this.notes.add(new Note(
                    Chromatic.values()[currentNoteIndex],
                    currentOctave
            ));
            Integer currentPatternIndex = 0;
            currentPatternIndex++;
            currentNoteIndex += this.pattern.scaleSteps.get(currentPatternIndex % this.pattern.scaleSteps.size());
            if(currentNoteIndex >= Chromatic.values().length){
                currentOctave++;
                currentNoteIndex = currentNoteIndex % Chromatic.values().length;
            }
        }
    }

    public List<Note> getNotes() {
        return notes;
    }

    public List<Note> getArpeggio(){
        return Arrays.asList(this.notes.get(0), this.notes.get(2), this.notes.get(4), this.notes.get(7));
    }

}
