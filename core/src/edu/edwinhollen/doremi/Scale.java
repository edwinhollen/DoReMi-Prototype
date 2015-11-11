package edu.edwinhollen.doremi;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Edwin on 10/24/15
 */
public class Scale {

    private Integer currentOctave = null;
    private Integer currentNoteIndex = null;
    private Integer currentPatternIndex = 0;
    private ScalePattern pattern;

    private List<Note> notes = new LinkedList<>();

    public Scale(Chromatic rootNote, Integer rootOctave, ScalePattern pattern){
        this.currentOctave = rootOctave;
        this.pattern = pattern;

        currentNoteIndex = 0;
        while(!Chromatic.values()[currentNoteIndex].equals(rootNote)){
            currentNoteIndex++;
        }

        while(this.notes.size() < 8){
            this.notes.add(new Note(
                    Chromatic.values()[this.currentNoteIndex],
                    this.currentOctave
            ));
            this.currentPatternIndex++;
            this.currentNoteIndex += this.pattern.scaleSteps.get(currentPatternIndex % this.pattern.scaleSteps.size());
            if(this.currentNoteIndex >= Chromatic.values().length){
                this.currentOctave++;
                this.currentNoteIndex = this.currentNoteIndex % Chromatic.values().length;
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
