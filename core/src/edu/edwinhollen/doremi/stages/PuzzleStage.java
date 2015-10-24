package edu.edwinhollen.doremi.stages;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.edwinhollen.doremi.DoReMi;
import edu.edwinhollen.doremi.Note;
import edu.edwinhollen.doremi.ScalePatterns;
import edu.edwinhollen.doremi.Scales;

/**
 * Created by Edwin on 10/22/15
 */
public class PuzzleStage extends BaseStage {
    public PuzzleStage(Viewport viewport, Batch batch) {
        super(viewport, batch, DoReMi.Palette.gray);

        Scales.Scale s = new Scales.Scale(new Note("cn"), ScalePatterns.major);
        System.out.println(s.toString());
    }
}
