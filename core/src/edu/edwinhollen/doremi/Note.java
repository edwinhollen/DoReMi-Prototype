package edu.edwinhollen.doremi;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Edwin on 10/23/15
 */
public class Note {
    Integer octave = null;
    Chromatic chromatic;

    public Note(Chromatic chromatic, Integer octave){
        this.chromatic = chromatic;
        this.octave = octave;
    }

    public Note(String noteString){
        char[] parts = noteString.toLowerCase().toCharArray();
        String notePart = String.format("%c%c", parts[0], parts[1]);
        switch(notePart){
            case "cn":
                this.chromatic = Chromatic.C_NATURAL;
                break;
            case "c#":
                this.chromatic = Chromatic.C_SHARP;
                break;
            case "dn":
                this.chromatic = Chromatic.D_NATURAL;
                break;
            case "eb":
                this.chromatic = Chromatic.E_FLAT;
                break;
            case "en":
                this.chromatic = Chromatic.E_NATURAL;
                break;
            case "fn":
                this.chromatic = Chromatic.F_NATURAL;
                break;
            case "f#":
                this.chromatic = Chromatic.F_SHARP;
                break;
            case "gn":
                this.chromatic = Chromatic.G_NATURAL;
                break;
            case "ab":
                this.chromatic = Chromatic.A_FLAT;
                break;
            case "an":
                this.chromatic = Chromatic.A_NATURAL;
                break;
            case "bb":
                this.chromatic = Chromatic.B_FLAT;
                break;
            case "bn":
                this.chromatic = Chromatic.B_NATURAL;
                break;
        }
        this.octave = parts.length > 2 ? Integer.parseInt(Character.toString(parts[2])) : null;
    }

    public Integer getOctave() {
        return octave;
    }

    public Chromatic getChromatic() {
        return chromatic;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Note)) return false;
        Note otherNote = (Note) obj;
        return this.equalsIgnoreOctave(otherNote) && this.octave.equals(otherNote.octave);
    }

    public boolean equalsIgnoreOctave(Note otherNote){
        return this.chromatic.equals(otherNote.chromatic);
    }

    @Override
    public String toString() {
        return String.format("%s%s", this.chromatic.toString(), this.octave == null ? "" : this.octave.toString());
    }

    public AssetDescriptor<Sound> getAssetDescriptor(){
        return new AssetDescriptor<>(String.format("instruments/piano/%s.mp3", this.toString()), Sound.class);
    }
}
