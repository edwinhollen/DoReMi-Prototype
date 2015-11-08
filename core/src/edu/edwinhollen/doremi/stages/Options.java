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
    final static OptionsEntry NOTE_DIVERSITY = new OptionsEntry("note_diversity", Puzzle.NoteDiversity.low.toString());
    final static OptionsEntry RANGE_DIFFICULTY = new OptionsEntry("range_difficulty", Puzzle.RangeDifficulty.easy.toString());

    private Preferences preferences;

    public Options(){
        this.preferences = Gdx.app.getPreferences("DoReMi");
    }

    public Puzzle.NoteDiversity getNoteDiversity(){
        if(!this.preferences.contains(NOTE_DIVERSITY.getKey())){
            this.preferences.putString(NOTE_DIVERSITY.getKey(), NOTE_DIVERSITY.getValue());
        }
        return Puzzle.NoteDiversity.valueOf(this.preferences.getString(NOTE_DIVERSITY.getKey()));
    }

    public void setRangeDifficulty(Puzzle.RangeDifficulty newRangeDifficulty){
        this.preferences.putString("range_difficulty", newRangeDifficulty.toString());
    }

    public Puzzle.RangeDifficulty getRangeDifficulty(){
        if(!this.preferences.contains(RANGE_DIFFICULTY.getKey())){
            this.preferences.putString(RANGE_DIFFICULTY.getKey(), RANGE_DIFFICULTY.getValue());
        }
        return Puzzle.RangeDifficulty.valueOf(this.preferences.getString(NOTE_DIVERSITY.getKey()));
    }

    public void setNoteDiversity(Puzzle.NoteDiversity newNoteDiversity){
        this.preferences.putString("note_diversity", newNoteDiversity.toString());
    }

    protected static class OptionsEntry implements Map.Entry{
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
