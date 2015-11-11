package edu.edwinhollen.doremi;

/**
 * Created by Edwin on 11/11/15
 */
public enum Chromatic {
    C_SHARP,
    D_NATURAL,
    E_FLAT,
    E_NATURAL,
    F_NATURAL,
    F_SHARP,
    G_NATURAL,
    A_FLAT,
    A_NATURAL,
    B_FLAT,
    B_NATURAL,
    C_NATURAL;

    @Override
    public String toString() {
        switch(this){
            case C_SHARP:
                return "c#";
            case D_NATURAL:
                return "dn";
            case E_FLAT:
                return "eb";
            case E_NATURAL:
                return "en";
            case F_NATURAL:
                return "fn";
            case F_SHARP:
                return "f#";
            case G_NATURAL:
                return "gn";
            case A_FLAT:
                return "ab";
            case A_NATURAL:
                return "an";
            case B_FLAT:
                return "bb";
            case B_NATURAL:
                return "bn";
            case C_NATURAL:
                return "cn";
        }
        return super.toString();
    }
}
