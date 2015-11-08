package edu.edwinhollen.doremi.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.edwinhollen.doremi.DoReMi;

/**
 * Created by Edwin on 10/22/15
 */
public class OptionsStage extends BaseStage {
    private Preferences prefs;
    public OptionsStage(Viewport viewport, Batch batch) {
        super(viewport, batch, DoReMi.Palette.green);
        this.prefs = Gdx.app.getPreferences("DoReMi");
    }
}
