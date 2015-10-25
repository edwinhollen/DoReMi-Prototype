package edu.edwinhollen.doremi.stages;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.edwinhollen.doremi.*;

/**
 * Created by Edwin on 10/22/15
 */
public class PuzzleStage extends BaseStage {
    public PuzzleStage(Viewport viewport, Batch batch) {
        super(viewport, batch, DoReMi.Palette.gray);

        Scale s = new Scale(new Note("c"), ScalePatterns.major);
        System.out.println(s);
    }
}
