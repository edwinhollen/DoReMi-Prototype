package edu.edwinhollen.doremi.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.edwinhollen.doremi.DoReMi;
import edu.edwinhollen.doremi.LabelActor;
import edu.edwinhollen.doremi.MinusButtonActor;
import edu.edwinhollen.doremi.PlusButtonActor;

/**
 * Created by Edwin on 10/22/15
 */
public class OptionsStage extends BaseStage {
    private Options options;

    LabelActor noteDiversityLabel, rangeDifficultyLabel;
    LabelActor noteDiversityValueLabel, rangeDifficultyValueLabel;
    PlusButtonActor noteDiversityUp, rangeDifficultyUp;
    MinusButtonActor noteDiversityDown, rangeDifficultyDown;

    Sound littlePop;

    final float labelAlignX = 16f;
    final float valueAlignX = 242f;
    final float buttonAlignYAdjustment = -24f;
    final float minusButtonX = 170f;
    final float plusButtonX = 275f;


    public OptionsStage(Viewport viewport, Batch batch) {
        super(viewport, batch, DoReMi.Palette.green);
        this.options = new Options();

        this.littlePop = Gdx.audio.newSound(Gdx.files.internal("littlepop.mp3"));

        // Note diversity
        {
            noteDiversityLabel = new LabelActor("Note diversity", DoReMi.Palette.black, Align.left);
            noteDiversityLabel.setPosition(labelAlignX, getViewport().getWorldHeight() - 32);

            noteDiversityValueLabel = new LabelActor(this.options.getNoteDiversity().toString(), DoReMi.Palette.black, Align.center);
            noteDiversityValueLabel.setPosition(valueAlignX, noteDiversityLabel.getY());

            noteDiversityDown = new MinusButtonActor(new Runnable() {
                @Override
                public void run() {
                    if (options.decreaseNoteDiversity()) {
                        littlePop.play(1.0f, 0.75f, 1.0f);
                    } else {
                        littlePop.play(1.0f, 0.5f, 1.0f);
                    }
                    noteDiversityValueLabel.text = options.getNoteDiversity().toString();
                    noteDiversityValueLabel.addAction(Actions.sequence(Actions.moveBy(0, -5f, 0.1f), Actions.moveBy(0, 5f, 0.1f)));
                    options.save();
                }
            });
            noteDiversityDown.setPosition(minusButtonX, noteDiversityLabel.getY() + buttonAlignYAdjustment);

            noteDiversityUp = new PlusButtonActor(new Runnable() {
                @Override
                public void run() {
                    if (options.increaseNoteDiversity()) {
                        littlePop.play(1.0f, 1.25f, 1.0f);
                    } else {
                        littlePop.play(1.0f, 1.5f, 1.0f);
                    }
                    noteDiversityValueLabel.text = options.getNoteDiversity().toString();
                    noteDiversityValueLabel.addAction(Actions.sequence(Actions.moveBy(0, 5f, 0.1f), Actions.moveBy(0, -5f, 0.1f)));
                    options.save();
                }
            });
            noteDiversityUp.setPosition(plusButtonX, noteDiversityLabel.getY() + buttonAlignYAdjustment);

            addActor(noteDiversityLabel);
            addActor(noteDiversityValueLabel);
            addActor(noteDiversityUp);
            addActor(noteDiversityDown);
        }

        // Range difficulty
        {
            rangeDifficultyLabel = new LabelActor("Range difficulty", DoReMi.Palette.black, Align.left);
            rangeDifficultyLabel.setPosition(labelAlignX, getViewport().getWorldHeight() - 32 - 48);

            rangeDifficultyValueLabel = new LabelActor(options.getRangeDifficulty().toString(), DoReMi.Palette.black, Align.center);
            rangeDifficultyValueLabel.setPosition(valueAlignX, rangeDifficultyLabel.getY());

            rangeDifficultyDown = new MinusButtonActor(new Runnable() {
                @Override
                public void run() {
                    if(options.decreaseRangeDifficulty()){
                        littlePop.play(1.0f, 0.75f, 1.0f);
                    }else{
                        littlePop.play(1.0f, 0.5f, 1.0f);
                    }
                    rangeDifficultyValueLabel.text = options.getRangeDifficulty().toString();
                    rangeDifficultyValueLabel.addAction(Actions.sequence(Actions.moveBy(0, -5f, 0.1f), Actions.moveBy(0, 5f, 0.1f)));
                    options.save();
                }
            });
            rangeDifficultyDown.setPosition(minusButtonX, rangeDifficultyLabel.getY()+buttonAlignYAdjustment);

            rangeDifficultyUp = new PlusButtonActor(new Runnable() {
                @Override
                public void run() {
                    if (options.increaseRangeDifficulty()) {
                        littlePop.play(1.0f, 1.25f, 1.0f);
                    } else {
                        littlePop.play(1.0f, 1.5f, 1.0f);
                    }
                    rangeDifficultyValueLabel.text = options.getRangeDifficulty().toString();
                    rangeDifficultyValueLabel.addAction(Actions.sequence(Actions.moveBy(0, 5f, 0.1f), Actions.moveBy(0, -5f, 0.1f)));
                    options.save();
                }
            });
            rangeDifficultyUp.setPosition(plusButtonX, rangeDifficultyLabel.getY()+buttonAlignYAdjustment);

            addActor(rangeDifficultyLabel);
            addActor(rangeDifficultyValueLabel);
            addActor(rangeDifficultyDown);
            addActor(rangeDifficultyUp);
        }
    }
}
