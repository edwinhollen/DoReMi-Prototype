package edu.edwinhollen.doremi;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.edwinhollen.doremi.stages.PuzzleStage;
import edu.edwinhollen.doremi.stages.TitleStage;

import java.lang.reflect.InvocationTargetException;

public class DoReMi extends ApplicationAdapter {
	static SpriteBatch batch;
	static Stage currentStage;
	static Viewport viewport;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		viewport = new FitViewport(336, 210);

		changeStage(PuzzleStage.class);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(currentStage != null){
			currentStage.act(Gdx.graphics.getDeltaTime());
			currentStage.draw();
		}
	}

	public static void changeStage(Class<? extends Stage> newStageClass){
		try {
			if(currentStage != null) currentStage.dispose();
			try {
				currentStage = newStageClass.getConstructor(Viewport.class, Batch.class).newInstance(viewport, batch);
			} catch (InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
			currentStage.setViewport(viewport);
			Gdx.input.setInputProcessor(currentStage);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void resize(int width, int height) {
		if(currentStage != null) currentStage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose() {
		super.dispose();
		if(currentStage != null) currentStage.dispose();
		batch.dispose();
		Fonts.dispose();
	}

	public static final class Palette{
		public static final Color
			yellow = Color.valueOf("e8c95d"),
			red = Color.valueOf("d16b54"),
			green = Color.valueOf("a9d8c8"),
			black = Color.valueOf("433447"),
			gray = Color.valueOf("b9b09f");
	}

	public static final class Fonts{
		public static final BitmapFont normal = new BitmapFont(Gdx.files.internal("ubuntu_mono_italic.fnt"));
		public static void dispose() {
			normal.dispose();
		}
	}
}
