package edu.edwinhollen.doremi;

import edu.edwinhollen.doremi.stages.Options;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Edwin on 10/24/15
 */
public class Puzzle {
    final static int OCTAVE_MIN = 1, OCTAVE_MAX = 3;

    final RangeDifficulty rangeDifficulty;
    final NoteDiversity noteDiversity;
    final Note solutionRootNote;
    final ScalePattern solutionScalePattern;
    final List<Note> solutionNotes, extraNotes;

    private Options options;

    /**
     * Debug purposes
     * @param solutionNotes
     * @param extraNotes
     */
    public Puzzle(List<Note> solutionNotes, List<Note> extraNotes){
        this.solutionNotes = solutionNotes;
        this.extraNotes = extraNotes;
        solutionScalePattern = null;
        solutionRootNote = solutionNotes.get(0);
        noteDiversity = null;
        rangeDifficulty = null;
    }

    public Puzzle(RangeDifficulty rd, NoteDiversity nd){
        this.rangeDifficulty = rd;
        this.noteDiversity = nd;

        System.out.println(String.format("Creating a puzzle range difficulty %s, note diversity %s", rd.toString(), nd.toString()));

        // Determine start and end octaves
        int octaveStart, octaveEnd;
        switch(this.rangeDifficulty){
            case medium:
                octaveStart = Pick.integer(0, OCTAVE_MAX - 1);
                octaveEnd = OCTAVE_MAX;
                break;
            case hard:
                octaveStart = OCTAVE_MIN;
                octaveEnd = OCTAVE_MAX;
                break;
            default:
                octaveStart = Pick.integer(OCTAVE_MIN, OCTAVE_MAX);
                octaveEnd = octaveStart;
                break;
        }

        // Set solution notes
        solutionRootNote = Pick.pick(Scale.chromatic);
        solutionRootNote.octave = Pick.integer(octaveStart, octaveEnd);

        switch(this.noteDiversity){
            case medium: {
                solutionScalePattern = Pick.pick(ScalePatterns.major, ScalePatterns.minor);
                solutionNotes = Pick.pick(new Scale(solutionRootNote, solutionScalePattern).getNotes(), 4);
                break;
            }
            case high: {
                solutionScalePattern = ScalePatterns.chromatic;
                solutionNotes = Pick.pick(new Scale(solutionRootNote, ScalePatterns.chromatic).getNotes(), 4);
                break;
            }
            default: {
                    solutionScalePattern = ScalePatterns.major;
                    solutionNotes = new Scale(solutionRootNote, solutionScalePattern).getArpeggio();
                    break;
            }
        }

        extraNotes = new LinkedList<>();
        List<Note> extraNotesPool = new LinkedList<>();
        for(Note note : Scale.chromatic){
            boolean canUse = true;
            for(Note solutionNote : solutionNotes){
                if(note.equalsIgnoreOctave(solutionNote)){
                    canUse = false;
                    break;
                }
            }
            if(canUse){
                extraNotesPool.add(note);
            }
        }

        while(extraNotes.size() < 4){
            int i = Pick.integer(extraNotesPool.size()-1);
            Note noteToAdd = extraNotesPool.get(i);
            noteToAdd.octave = Pick.integer(OCTAVE_MIN, OCTAVE_MAX);
            extraNotes.add(noteToAdd);
            extraNotesPool.remove(i);
        }
    }

    public List<Note> getSolutionNotes(){
        return this.solutionNotes;
    }

    public List<Note> getExtraNotes(){
        return this.extraNotes;
    }

    @Override
    public String toString() {
        return String.format("Puzzle is based on %s %s \n Solution:\t%s\nExtras:\t%s",
                this.solutionRootNote.toString(),
                this.solutionScalePattern == null ? "?" : this.solutionScalePattern.toString(),
                Arrays.toString(this.solutionNotes.toArray()),
                Arrays.toString(this.extraNotes.toArray())
        );
    }

    /**
     * Range difficulty
     * easy - single octave
     * medium - two octaves
     * hard - three octaves
     */
    public enum RangeDifficulty{
        easy, medium, hard
    }

    /**
     * Note diversity
     * easy - major arpeggio
     * medium - major or minor scale
     * hard - chromatic scale
     */
    public enum NoteDiversity{
        low, medium, high
    }
}
