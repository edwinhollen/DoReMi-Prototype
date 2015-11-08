package edu.edwinhollen.doremi.stages;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.edwinhollen.doremi.ButtonActor;
import edu.edwinhollen.doremi.DoReMi;

/**
 * Created by Edwin on 10/22/15
 */
public class OptionsStage extends BaseStage {
    private Options options;

    ButtonActor noteDiversityButton, rangeDifficultyButton;

    public OptionsStage(Viewport viewport, Batch batch) {
        super(viewport, batch, DoReMi.Palette.green);
        this.options = new Options();

        noteDiversityButton = new ButtonActor(String.format("Note diversity: %s", this.options.getNoteDiversity().toString()), new Runnable() {
            @Override
            public void run() {
                System.out.println("test");
            }
        });
        noteDiversityButton.setPosition(getViewport().getWorldWidth()/2 - noteDiversityButton.getWidth() / 2, 0);
        addActor(noteDiversityButton);
    }
}
