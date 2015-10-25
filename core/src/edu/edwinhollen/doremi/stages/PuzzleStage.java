package edu.edwinhollen.doremi.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.edwinhollen.doremi.DoReMi;
import edu.edwinhollen.doremi.Note;
import edu.edwinhollen.doremi.Puzzle;

import java.util.List;

/**
 * Created by Edwin on 10/22/15
 */
public class PuzzleStage extends BaseStage {
    public PuzzleStage(Viewport viewport, Batch batch) {
        super(viewport, batch, DoReMi.Palette.gray);

        Puzzle p = new Puzzle(Puzzle.RangeDifficulty.easy, Puzzle.NoteDiversity.low);
        System.out.println(p.toString());
        final List<Note> solutionNotes = p.getSolutionNotes();
        for(int i = 0; i < solutionNotes.size(); i++){
            final int finalI = i;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    Sound s = Gdx.audio.newSound(Gdx.files.internal(String.format("instruments/piano/%s.mp3", solutionNotes.get(finalI).toString())));
                    s.play();
                }
            }, 1.0f * i);
        }
    }
}
