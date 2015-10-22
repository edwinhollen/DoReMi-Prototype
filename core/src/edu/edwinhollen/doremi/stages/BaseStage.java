package edu.edwinhollen.doremi.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.edwinhollen.doremi.DoReMi;

/**
 * Created by Edwin on 10/22/15
 */
public abstract class BaseStage extends Stage {
    protected Color backgroundColor;
    public BaseStage(Viewport viewport, Batch batch){
        this(viewport, batch, DoReMi.Palette.black);
    }
    public BaseStage(Viewport viewport, Batch batch, Color backgroundColor) {
        super(viewport, batch);
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(this.backgroundColor.r, this.backgroundColor.g, this.backgroundColor.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.draw();
    }
}
