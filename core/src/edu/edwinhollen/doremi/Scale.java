package edu.edwinhollen.doremi;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Edwin on 10/24/15
 */
public class Scale {
    public static final List<Note> chromatic = new LinkedList<>(Arrays.asList(
        new Note("cn"), new Note("c#"), new Note("dn"), new Note("eb"),
        new Note("en"), new Note("fn"), new Note("f#"), new Note("gn"),
        new Note("ab"), new Note("an"), new Note("bb"), new Note("bn")
    ));
    List<Note> notes = new LinkedList<>();

    /**
     * Constructs a scale given a root note and scale pattern
     * @param rootNote the root note
     * @param pattern the pattern to follow up the scale
     */
    public Scale(Note rootNote, ScalePattern pattern) {
        ScaleIterator scaleIterator = new ScaleIterator(rootNote, pattern);
        do {
            notes.add(scaleIterator.next());
        } while (this.notes.size() < pattern.scaleSteps.size());
        notes.add(new Note(rootNote.note, rootNote.accidental, rootNote.octave + 1));
    }

    /**
     * Gets an arpeggio of this scale (root, 3, 5, root)
     * @return a list of notes in the arpeggio
     */
    public List<Note> getArpeggio(){
        return Arrays.asList(notes.get(0), notes.get(2), notes.get(4), notes.get(7));
    }

    @Override
    public String toString() {
        return Arrays.toString(this.notes.toArray());
    }

    /**
     * ScaleIterator iterates up through a scale given a scale pattern
     * If given, octave is considered; otherwise, defaults to 0
     */
    private static class ScaleIterator implements Iterator<Note> {
        int currentNoteIndex = 0;
        int currentPatternIndex = 0;
        int currentOctave = 0;

        private Note rootNote;
        private ScalePattern pattern;

        public ScaleIterator(Note rootNote, ScalePattern pattern){
            this.rootNote = rootNote;
            this.currentOctave = this.rootNote.octave == null ? 0 : this.rootNote.octave;
            this.pattern = pattern;

            while(!chromatic.get(currentNoteIndex).equalsIgnoreOctave(rootNote)){
                currentNoteIndex++;
            }
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Note next() {
            Note noteToReturn = new Note(chromatic.get(currentNoteIndex % chromatic.size()).note, chromatic.get(currentNoteIndex).accidental, currentOctave);
            currentPatternIndex = (currentPatternIndex + 1) % this.pattern.scaleSteps.size();
            currentNoteIndex += (pattern.scaleSteps.get(currentPatternIndex));
            if(currentNoteIndex >= chromatic.size()){
                currentOctave++;
                currentNoteIndex = 0;
            }
            return noteToReturn;
        }
    }
}
