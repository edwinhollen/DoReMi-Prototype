package edu.edwinhollen.doremi.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.edwinhollen.doremi.*;

/**
 * Created by Edwin on 10/22/15
 */
public class OptionsStage extends BaseStage {

    private Preferences preferences;
    private AssetManager assetManager;
    private Label noteDiversity, rangeDifficulty;
    private AssetDescriptor<Sound> littlePop;
    private Sprite plusButton, minusButton;

    public final static String RANGE_DIFFICULTY_KEY = "rangeDifficulty";
    public final static String NOTE_DIVERSITY_KEY = "noteDiversity";

    @Override
    public void act(float delta) {
        super.act(delta);
        assetManager.update();
    }

    @Override
    public void dispose() {
        super.dispose();
        assetManager.dispose();
        preferences.flush();
    }

    private SequenceAction getButtonAction() {
        return Actions.sequence(Actions.scaleTo(0.95f, 0.95f, 0.05f), Actions.scaleTo(1.0f, 1.0f, 0.05f));
    }

    private SequenceAction getValueActionDown(){
        return Actions.sequence(Actions.moveBy(0, -8f, 0.1f), Actions.moveBy(0, 8f, 0.1f));
    }

    private SequenceAction getValueActionUp(){
        return Actions.sequence(Actions.moveBy(0, 8f, 0.1f), Actions.moveBy(0, -8f, 0.1f));
    }

    public OptionsStage(Viewport viewport, Batch batch) {
        super(viewport, batch, DoReMi.Palette.green);
        this.assetManager = new AssetManager();

        this.preferences = Gdx.app.getPreferences("DoReMi");
        if(!this.preferences.contains(NOTE_DIVERSITY_KEY)){
            this.preferences.putString("noteDiversity", Puzzle.NoteDiversity.values()[0].toString());
        }
        if(!this.preferences.contains(RANGE_DIFFICULTY_KEY)){
            this.preferences.putString(RANGE_DIFFICULTY_KEY, Puzzle.RangeDifficulty.values()[0].toString());
        }

        littlePop = new AssetDescriptor<Sound>("littlepop.mp3", Sound.class);
        plusButton = DoReMi.assets.get(DoReMi.spritesheet).createSprite("plusbutton");
        minusButton = DoReMi.assets.get(DoReMi.spritesheet).createSprite("minusbutton");

        this.assetManager.load(littlePop);

        final float labelWidth = viewport.getWorldWidth() * 0.5f;
        final float buttonWidth = viewport.getWorldWidth() * 0.08f;
        final float valueWidth = viewport.getWorldWidth() * 0.3f;
        final float littlePopUpPitch = 1.5f;
        final float littlePopDownPitch = 0.5f;

        Table actorTable = new Table();
        actorTable.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        actorTable.setPosition(0, 0);

        // Note diversity

        final Label noteDiversityLabel = new Label("Note diversity", DoReMi.labelStyle);
        actorTable.add(noteDiversityLabel).width(labelWidth);

        final Image noteDiversityDown = new Image(minusButton);
        noteDiversityDown.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                noteDiversityDown.addAction(getButtonAction());
                int currentValue = Puzzle.NoteDiversity.valueOf(preferences.getString(NOTE_DIVERSITY_KEY)).ordinal();
                if(currentValue <= 0){
                    assetManager.get(littlePop).play(1.0f, littlePopDownPitch, 1.0f);
                }else{
                    assetManager.get(littlePop).play(1.0f, 1.0f, 1.0f);
                    preferences.putString(NOTE_DIVERSITY_KEY, Puzzle.NoteDiversity.values()[currentValue - 1].toString());
                    noteDiversity.setText(preferences.getString(NOTE_DIVERSITY_KEY));
                }

                noteDiversity.addAction(getValueActionDown());

                return super.touchDown(event, x, y, pointer, button);
            }
        });
        actorTable.add(noteDiversityDown).width(buttonWidth);

        noteDiversity = new Label(preferences.getString(NOTE_DIVERSITY_KEY, Puzzle.NoteDiversity.values()[0].toString()), DoReMi.labelStyle);
        noteDiversity.setWidth(valueWidth);
        noteDiversity.setAlignment(Align.center);
        actorTable.add(noteDiversity).width(valueWidth);

        final Image noteDiversityUp = new Image(plusButton);
        noteDiversityUp.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                noteDiversityUp.addAction(getButtonAction());
                int currentValue = Puzzle.NoteDiversity.valueOf(preferences.getString(NOTE_DIVERSITY_KEY)).ordinal();
                if(currentValue >= Puzzle.NoteDiversity.values().length - 1){
                    assetManager.get(littlePop).play(1.0f, littlePopUpPitch, 1.0f);
                }else{
                    assetManager.get(littlePop).play(1.0f, 1.0f, 1.0f);
                    preferences.putString(NOTE_DIVERSITY_KEY, Puzzle.NoteDiversity.values()[currentValue + 1].toString());
                    noteDiversity.setText(preferences.getString(NOTE_DIVERSITY_KEY));
                }

                noteDiversity.addAction(getValueActionUp());

                return super.touchDown(event, x, y, pointer, button);
            }
        });
        actorTable.add(noteDiversityUp).width(buttonWidth);

        actorTable.row();

        // Range difficulty
        Label rangeDifficultyLabel = new Label("Range difficulty", DoReMi.labelStyle);
        actorTable.add(rangeDifficultyLabel).width(labelWidth);

        final Image rangeDifficultyDown = new Image(minusButton);
        rangeDifficultyDown.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rangeDifficultyDown.addAction(getButtonAction());
                int currentValue = Puzzle.RangeDifficulty.valueOf(preferences.getString(RANGE_DIFFICULTY_KEY)).ordinal();
                if(currentValue <= 0){
                    assetManager.get(littlePop).play(1.0f, littlePopDownPitch, 1.0f);
                }else{
                    assetManager.get(littlePop).play(1.0f, 1.0f, 1.0f);
                    preferences.putString(RANGE_DIFFICULTY_KEY, Puzzle.RangeDifficulty.values()[currentValue - 1].toString());
                    rangeDifficulty.setText(preferences.getString(RANGE_DIFFICULTY_KEY));
                }

                rangeDifficulty.addAction(getValueActionDown());

                return super.touchDown(event, x, y, pointer, button);
            }
        });
        actorTable.add(rangeDifficultyDown).width(buttonWidth);

        rangeDifficulty = new Label(preferences.getString(RANGE_DIFFICULTY_KEY, Puzzle.RangeDifficulty.values()[0].toString()), DoReMi.labelStyle);
        rangeDifficulty.setWidth(valueWidth);
        rangeDifficulty.setAlignment(Align.center);
        actorTable.add(rangeDifficulty).width(valueWidth);

        final Image rangeDifficultyUp = new Image(plusButton);
        rangeDifficultyUp.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rangeDifficultyUp.addAction(getButtonAction());
                int currentValue = Puzzle.RangeDifficulty.valueOf(preferences.getString(RANGE_DIFFICULTY_KEY)).ordinal();
                if(currentValue >=  Puzzle.RangeDifficulty.values().length - 1){
                    assetManager.get(littlePop).play(1.0f, littlePopUpPitch, 1.0f);
                }else{
                    assetManager.get(littlePop).play(1.0f, 1.0f, 1.0f);
                    preferences.putString(RANGE_DIFFICULTY_KEY, Puzzle.RangeDifficulty.values()[currentValue + 1].toString());
                    rangeDifficulty.setText(preferences.getString(RANGE_DIFFICULTY_KEY));
                }

                rangeDifficulty.addAction(getValueActionUp());

                return super.touchDown(event, x, y, pointer, button);
            }
        });
        actorTable.add(rangeDifficultyUp).width(buttonWidth);

        actorTable.row();

        addActor(actorTable);

    }
}
