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
public class MinusButtonActor extends Actor {
    static final Texture minusButtonTexture = new Texture(Gdx.files.internal("minusbutton.png"));
    static final TextureRegion
            up = new TextureRegion(minusButtonTexture, 0, 0, minusButtonTexture.getWidth(), minusButtonTexture.getHeight()),
            down = new TextureRegion(minusButtonTexture, 0, 0, minusButtonTexture.getWidth(), minusButtonTexture.getHeight());
    final Runnable onClick;

    public MinusButtonActor(final Runnable onClick){
        setSize(up.getRegionWidth(), up.getRegionHeight());
        this.onClick = onClick;
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                onClick.run();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(up, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
