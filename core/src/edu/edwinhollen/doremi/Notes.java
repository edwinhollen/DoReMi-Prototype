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

    public enum NoteName{
        a, b, c, d, e, f, g
    }

    public enum Accidental{
        sharp, flat, natural
    }

    public static Accidental charToAccidental(Character c){
        switch(c){
            case '#':
                return Accidental.sharp;
            case 'b':
                return Accidental.flat;
            default:
                return Accidental.natural;
        }
    }

    public static Character accidentalToChar(Accidental a){
        switch(a){
            case sharp:
                return '#';
            case flat:
                return 'b';
            default:
                return 'n';
        }
    }
}
