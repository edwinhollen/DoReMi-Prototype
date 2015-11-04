package edu.edwinhollen.doremi;

/**
 * Created by Edwin on 10/23/15
 */
public class Note {
    NoteName note;
    Accidental accidental;
    Integer octave;

    public Note(NoteName note, Accidental accidental, Integer octave){
        this.note = note;
        this.accidental = accidental;
        this.octave = octave;
    }

    public Note(NoteName note, Accidental accidental){
        this(note, accidental, null);
    }

    public Note(NoteName note){
        this(note, Accidental.natural);
    }

    public Note(String noteString){
        char[] parts = noteString.toLowerCase().toCharArray();
        Integer parsedOctave = null;
        NoteName parsedNoteName;
        Accidental parsedAccidental = Accidental.natural;

        switch(parts.length){
            case 3:
                parsedOctave = Integer.parseInt(String.valueOf(parts[2]));
            case 2:
                parsedAccidental = Accidental.fromChar(parts[1]);
            default:
                parsedNoteName = NoteName.valueOf(String.valueOf(parts[0]));
        }

        this.note = parsedNoteName;
        this.accidental = parsedAccidental;
        this.octave = parsedOctave;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Note)) return false;
        Note otherNote = (Note) obj;
        return this.equalsIgnoreOctave(otherNote) && this.octave.equals(otherNote.octave);
    }

    public boolean equalsIgnoreOctave(Note otherNote){
        return otherNote.note.equals(this.note) && otherNote.accidental.equals(this.accidental);
    }

    @Override
    public String toString() {
        return String.format("%s%s%s", this.note.toString(), this.accidental.toString(), this.octave == null ? "" : this.octave.toString());
    }
}
