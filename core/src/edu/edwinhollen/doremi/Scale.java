package edu.edwinhollen.doremi;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Edwin on 10/24/15
 */
public class Scale {

    public static final List<Note> chromatic = Arrays.asList(
        new Note(NoteName.c, Accidental.natural),
        new Note(NoteName.c, Accidental.sharp),
        new Note(NoteName.d, Accidental.natural),
        new Note(NoteName.e, Accidental.flat),
        new Note(NoteName.e, Accidental.natural),
        new Note(NoteName.f, Accidental.natural),
        new Note(NoteName.f, Accidental.sharp),
        new Note(NoteName.g, Accidental.natural),
        new Note(NoteName.a, Accidental.flat),
        new Note(NoteName.a, Accidental.natural),
        new Note(NoteName.b, Accidental.flat),
        new Note(NoteName.b, Accidental.natural)
    );

    private Integer currentOctave = null;
    private Integer currentNoteIndex = null;
    private Integer currentPatternIndex = 0;
    private ScalePattern pattern;

    private List<Note> notes = new LinkedList<>();

    public Scale(Note rootNote, ScalePattern pattern){
        this.currentOctave = rootNote.octave;
        this.pattern = pattern;

        currentNoteIndex = 0;
        while(!chromatic.get(currentNoteIndex).equalsIgnoreOctave(rootNote)){
            currentNoteIndex++;
        }

        while(this.notes.size() < 8){
            this.notes.add(new Note(
                    chromatic.get(this.currentNoteIndex).note,
                    chromatic.get(this.currentNoteIndex).accidental,
                    this.currentOctave
            ));
            this.currentPatternIndex++;
            this.currentNoteIndex += this.pattern.scaleSteps.get(currentPatternIndex % this.pattern.scaleSteps.size());
            if(this.currentNoteIndex >= chromatic.size()){
                this.currentOctave++;
                this.currentNoteIndex = this.currentNoteIndex % chromatic.size();
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
