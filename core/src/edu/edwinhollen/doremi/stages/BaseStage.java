package edu.edwinhollen.doremi.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.edwinhollen.doremi.DoReMi;

/**
 * Created by Edwin on 10/22/15
 */
public abstract class BaseStage extends Stage {
    protected Color backgroundColor;
    boolean backButton = true;
    Actor backButtonActor;

    final TextureRegion arrowLeft = new TextureRegion(new Texture(Gdx.files.internal("images/arrow_left.png")));

    public BaseStage(Viewport viewport, Batch batch){
        this(viewport, batch, DoReMi.Palette.black);
    }

    protected void setBackButton(boolean bb){
        this.backButton = bb;
        Gdx.input.setCatchBackKey(bb);
    }

    public BaseStage(Viewport viewport, Batch batch, Color backgroundColor) {
        super(viewport, batch);
        this.backgroundColor = backgroundColor;

        this.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.ESCAPE){
                    Gdx.app.exit();
                }
                return true;
            }
        });

        backButtonActor = new Actor(){
            @Override
            public void draw(Batch batch, float parentAlpha) {
                batch.draw(arrowLeft, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }
        };

        addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(backButton && keycode == Input.Keys.BACK){
                    DoReMi.changeStage(TitleStage.class);
                }
                return super.keyDown(event, keycode);
            }
        });
        backButtonActor.setSize(arrowLeft.getRegionWidth(), arrowLeft.getRegionHeight());
        backButtonActor.setPosition(0, getViewport().getWorldHeight() - arrowLeft.getRegionHeight());
        backButtonActor.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                DoReMi.changeStage(TitleStage.class);
                return true;
            }
        });
        addActor(backButtonActor);


    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(this.backgroundColor.r, this.backgroundColor.g, this.backgroundColor.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.backButtonActor.setVisible(backButton);
        super.draw();
    }
}
