package edu.edwinhollen.doremi;

/**
 * Created by Edwin on 10/23/15
 */
public class Note {
    Notes.NoteName noteName;
    Notes.Accidental accidental;
    Integer octave;

    /**
     * Construct a note given note name, accidental, and octave
     * @param noteName the note name
     * @param accidental the accidental
     * @param octave the octave
     */
    public Note(Notes.NoteName noteName, Notes.Accidental accidental, Integer octave) {
        this.noteName = noteName;
        this.accidental = accidental;
        this.octave = octave;
    }

    /**
     * Construct a note given a note name and accidental
     * @param noteName the note name
     * @param accidental the accidental
     */
    public Note(Notes.NoteName noteName, Notes.Accidental accidental) {
        this(noteName, accidental, null);
    }

    /**
     * Construct a note given a name
     * Accidental is assumed natural, and octave assumed null
     * @param noteName the note name
     */
    public Note(Notes.NoteName noteName) {
        this(noteName, Notes.Accidental.natural, null);
    }


    /**
     * Construct a note from a note string
     * @see edu.edwinhollen.doremi.Note#valueOf(String)
     * @param noteString the note string to parse
     */
    public Note(String noteString) {
        Note v = valueOf(noteString);
        this.noteName = v.noteName;
        this.accidental = v.accidental;
        this.octave = v.octave;
    }

    /**
     * Parse a note string
     * The string should be in [note][accidental][octave] format
     * @param noteString The string to parse
     */
    public static Note valueOf(String noteString) {
        char[] parts = noteString.toLowerCase().toCharArray();

        Notes.NoteName name = Notes.NoteName.valueOf(String.valueOf(parts[0]));
        Notes.Accidental accidental = Notes.charToAccidental(parts[1]);
        Integer octave;
        try {
            octave = Integer.parseInt(String.valueOf(parts[2]));
        }catch(IndexOutOfBoundsException e){
            octave = null;
        }

        return new Note(name, accidental, octave);
    }

    @Override
    public String toString() {
        String pattern = this.octave == null ? "%s%s" : "%s%s%s";
        return String.format(pattern, this.noteName, Notes.accidentalToChar(this.accidental), this.octave);
    }
}
