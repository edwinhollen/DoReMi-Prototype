package edu.edwinhollen.doremi;

/**
 * Created by Edwin on 10/23/15
 */
public enum Accidental {
    sharp, flat, natural;

    public static Accidental fromChar(Character c) {
        switch (c) {
            case '#':
                return Accidental.sharp;
            case 'b':
                return Accidental.flat;
            default:
                return Accidental.natural;
        }
    }

    public static Character toChar(Accidental a) {
        switch (a) {
            case sharp:
                return '#';
            case flat:
                return 'b';
            default:
                return 'n';
        }
    }
}
