package edu.edwinhollen.doremi;

/**
 * Created by Edwin on 10/22/15
 */
public class Notes {

    public static final String[] chromatic = {

    };

    public static final class ScalePatterns{
        public final static int
            R = 0, // root
            W = 2, // whole step
            H = 1; // half step
        public final static int[]
            major = {R, W, W, H, W, W, W, H},
            minor = {R, W, H, W, W, H, W, W};
    }

    public class Note{
        NoteName noteName;
        Accidental accidental;
        Integer octave;

        public Note(NoteName noteName, Accidental accidental, Integer octave) {
            this.noteName = noteName;
            this.accidental = accidental;
            this.octave = octave;
        }

        public Note(NoteName noteName, Accidental accidental) {
            this(noteName, accidental, null);
        }

        public Note(NoteName noteName) {
            this(noteName, Accidental.natural, null);
        }

        /**
         * Parse a note string
         * The string should be in [note][accidental?][octave] format
         * @param noteString The string to parse
         */
        public Note(String noteString){

        }
    }

    public enum NoteName{
        a, b, c, d, e, f, g
    }

    public enum Accidental{
        sharp, flat, natural
    }
}
