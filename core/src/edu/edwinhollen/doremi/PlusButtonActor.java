package edu.edwinhollen.doremi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by Edwin on 11/8/15
 */
public class PlusButtonActor extends Actor{
    static final Texture plusButtonTexture = new Texture(Gdx.files.internal("plusbutton.png"));
    static final TextureRegion
            up = new TextureRegion(plusButtonTexture, 0, 0, 39, 40),
            down = new TextureRegion(plusButtonTexture, 0, 40, 39, 40);
    final Runnable onClick;
    public PlusButtonActor(final Runnable onClick){
        this.onClick = onClick;
        setSize(up.getRegionWidth(), up.getRegionHeight());
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                onClick.run();
                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(up, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
