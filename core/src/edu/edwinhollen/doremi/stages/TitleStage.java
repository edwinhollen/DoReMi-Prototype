package edu.edwinhollen.doremi.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.edwinhollen.doremi.ButtonActor;
import edu.edwinhollen.doremi.DoReMi;

/**
 * Created by Edwin on 10/22/15
 */
public class TitleStage extends BaseStage {
    boolean stringSnapped = false;

    Action violinGroupRockAction;
    Actor violin, violinString, startButton, optionsButton, aboutButton;
    Group violinGroup, subButtonsGroup;
    Music orchestraWarmUp;
    Sound stringSnap;
    public TitleStage(Viewport viewport, Batch batch){
        super(viewport, batch, DoReMi.Palette.red);

        setBackButton(false);

        // load music in a thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                orchestraWarmUp = Gdx.audio.newMusic(Gdx.files.internal("orchestra_tuning.mp3"));
                orchestraWarmUp.setLooping(true);
                orchestraWarmUp.setVolume(0.00f);
                orchestraWarmUp.play();
            }
        }).start();

        // load sound effect(s)
        stringSnap = Gdx.audio.newSound(Gdx.files.internal("violin_string_snap.mp3"));

        // violin actor
        violin = new Actor(){
            TextureRegion textureRegion = new TextureRegion(new Texture(Gdx.files.internal("images/violin.png")));
            @Override
            public void draw(Batch batch, float parentAlpha) {
                setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
                batch.setColor(getColor());
                batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }

            @Override
            public boolean remove() {
                this.textureRegion.getTexture().dispose();
                return super.remove();
            }
        };

        // violin string actor
        violinString = new Actor(){
            TextureRegion textureRegion = new TextureRegion(new Texture(Gdx.files.internal("images/violin_string.png")));

            @Override
            public void draw(Batch batch, float parentAlpha) {
                setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
                setOrigin(getWidth(), 0);
                batch.setColor(getColor());
                batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }

            @Override
            public boolean remove() {
                this.textureRegion.getTexture().dispose();
                return super.remove();
            }
        };
        violinString.setPosition(65, 40);
        violinString.setRotation(20.0f);
        violinString.setScaleX(1.5f);
        violinString.setVisible(false);

        // violin group
        // incl violin and string
        violinGroup = new Group(){
            @Override
            public void draw(Batch batch, float parentAlpha) {
                setOrigin(violin.getWidth()/2, violin.getHeight()/2);
                batch.setColor(getColor());
                super.draw(batch, parentAlpha);
                batch.setColor(0, 0, 0, 1);
            }
        };
        violinGroup.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(stringSnapped) return true;
                stringSnap.play(0.5f);
                if (orchestraWarmUp != null) {
                    orchestraWarmUp.stop();
                }
                violinString.setVisible(true);
                violinGroup.removeAction(violinGroupRockAction);
                violinString.addAction(
                    Actions.parallel(
                            Actions.rotateTo(0.0f, 0.8f, Interpolation.elasticOut),
                            Actions.scaleTo(1.0f, 1.0f, 0.6f, Interpolation.elasticOut)
                    )
                );
                stringSnapped = true;
                return true;
            }
        });
        violinGroupRockAction = Actions.forever(Actions.sequence(
                Actions.rotateBy(4.0f, 2.0f, Interpolation.fade),
                Actions.rotateBy(-4.0f, 2.0f, Interpolation.fade)
        ));
        violinGroup.addAction(violinGroupRockAction);
        violinGroup.setColor(DoReMi.Palette.green);
        violinGroup.setPosition(getViewport().getWorldWidth()/2 - 110, getViewport().getWorldHeight()/2 + 20);
        violinGroup.addActor(violin);
        violinGroup.addActor(violinString);

        addActor(violinGroup);

        // start button
        startButton = new ButtonActor("Start game", new Runnable() {
            @Override
            public void run() {
                DoReMi.changeStage(PuzzleStage.class);
            }
        });
        startButton.setPosition(viewport.getWorldWidth() / 2 - startButton.getWidth() / 2, 60);
        addActor(startButton);

        // sub buttons
        subButtonsGroup = new Group();
        subButtonsGroup.setWidth(startButton.getWidth());
        subButtonsGroup.setHeight(startButton.getHeight());
        subButtonsGroup.setPosition(startButton.getX(), startButton.getY() - startButton.getHeight() - 3);

        // options button
        optionsButton = new ButtonActor("Options", new Runnable() {
            @Override
            public void run() {
                DoReMi.changeStage(OptionsStage.class);
            }
        });
        optionsButton.setWidth((subButtonsGroup.getWidth() - 3) / 2);
        optionsButton.setPosition(0, 0);
        subButtonsGroup.addActor(optionsButton);

        // about button
        aboutButton = new ButtonActor("About", new Runnable() {
            @Override
            public void run() {
                DoReMi.changeStage(AboutStage.class);
            }
        });
        aboutButton.setWidth((subButtonsGroup.getWidth() - 3) / 2);
        aboutButton.setPosition(subButtonsGroup.getWidth() - aboutButton.getWidth(), 0);
        subButtonsGroup.addActor(aboutButton);

        addActor(subButtonsGroup);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // fade in the orchestra warm up music
        if(orchestraWarmUp != null){
            orchestraWarmUp.setVolume(Math.min(orchestraWarmUp.getVolume() + (0.015f * delta), 1.0f));
        }
    }

    @Override
    public void dispose() {
        orchestraWarmUp.dispose();
        stringSnap.dispose();
        super.dispose();
    }
}
