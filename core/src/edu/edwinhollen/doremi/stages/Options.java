package edu.edwinhollen.doremi.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import edu.edwinhollen.doremi.Puzzle;

import java.util.Map;

/**
 * Created by Edwin on 11/7/15
 */
public class Options {

    // defaults
    public final OptionsEntry NOTE_DIVERSITY = new OptionsEntry("note_diversity", Puzzle.NoteDiversity.low.toString());
    public final OptionsEntry RANGE_DIFFICULTY = new OptionsEntry("range_difficulty", Puzzle.RangeDifficulty.easy.toString());

    private Preferences preferences;

    public Options(){
        this.preferences = Gdx.app.getPreferences("DoReMi");
    }

    public void save(){
        this.preferences.flush();
    }

    public Puzzle.NoteDiversity getNoteDiversity(){
        if(!this.preferences.contains(NOTE_DIVERSITY.getKey())){
            this.preferences.putString(NOTE_DIVERSITY.getKey(), NOTE_DIVERSITY.getValue());
            this.preferences.flush();
        }
        return Puzzle.NoteDiversity.valueOf(this.preferences.getString(NOTE_DIVERSITY.getKey()));
    }

    public void setRangeDifficulty(Puzzle.RangeDifficulty newRangeDifficulty){
        this.preferences.putString(RANGE_DIFFICULTY.getKey(), newRangeDifficulty.toString());
    }

    public Puzzle.RangeDifficulty getRangeDifficulty(){
        if(!this.preferences.contains(RANGE_DIFFICULTY.getKey())){
            this.preferences.putString(RANGE_DIFFICULTY.getKey(), RANGE_DIFFICULTY.getValue());
            this.preferences.flush();
        }
        return Puzzle.RangeDifficulty.valueOf(this.preferences.getString(RANGE_DIFFICULTY.getKey()));
    }

    public void setNoteDiversity(Puzzle.NoteDiversity newNoteDiversity){
        this.preferences.putString(NOTE_DIVERSITY.getKey(), newNoteDiversity.toString());
    }

    public boolean increaseNoteDiversity(){
        int current = Puzzle.NoteDiversity.valueOf(getNoteDiversity().toString()).ordinal();
        if(current < Puzzle.NoteDiversity.values().length - 1){
            this.preferences.putString(NOTE_DIVERSITY.getKey(), Puzzle.NoteDiversity.values()[current + 1].toString());
            return true;
        }
        return false;
    }

    public boolean decreaseNoteDiversity(){
        int current = Puzzle.NoteDiversity.valueOf(getNoteDiversity().toString()).ordinal();
        if(current > 0){
            this.preferences.putString(NOTE_DIVERSITY.getKey(), Puzzle.NoteDiversity.values()[current - 1].toString());
            return true;
        }
        return false;
    }

    public boolean increaseRangeDifficulty(){
        int current = Puzzle.RangeDifficulty.valueOf(getRangeDifficulty().toString()).ordinal();
        if(current < Puzzle.RangeDifficulty.values().length - 1){
            this.preferences.putString(RANGE_DIFFICULTY.getKey(), Puzzle.RangeDifficulty.values()[current + 1].toString());
            return true;
        }
        return false;
    }

    public boolean decreaseRangeDifficulty(){
        int current = Puzzle.RangeDifficulty.valueOf(getRangeDifficulty().toString()).ordinal();
        if(current > 0){
            this.preferences.putString(RANGE_DIFFICULTY.getKey(), Puzzle.RangeDifficulty.values()[current - 1].toString());
            return true;
        }
        return false;
    }

    protected class OptionsEntry implements Map.Entry{
        private String key;
        private String value;

        public OptionsEntry(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String getKey() {
            return this.key;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        @Override
        public String setValue(Object value) {
            String old = this.value;
            this.value = (String) value;
            return old;
        }
    }
}
