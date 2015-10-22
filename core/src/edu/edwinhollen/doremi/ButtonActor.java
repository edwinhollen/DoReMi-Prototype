package edu.edwinhollen.doremi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by Edwin on 10/22/15
 */
public class ButtonActor extends Actor {
    final static float coolDown = 0.5f;
    boolean isDown = false;
    long lastDown = -1;

    final Texture buttonTemplate = new Texture(Gdx.files.internal("button_template.png"));
    final Sound clickDown = Gdx.audio.newSound(Gdx.files.internal("click_down.mp3"));
    final Sound clickUp = Gdx.audio.newSound(Gdx.files.internal("click_up.mp3"));
    final TextureRegion
            upLeft = new TextureRegion(buttonTemplate, 0, 0, 21, 40),
            upMiddle = new TextureRegion(buttonTemplate, 22, 0, 1, 40),
            upRight = new TextureRegion(buttonTemplate, 24, 0, 21, 40),
            downLeft = new TextureRegion(buttonTemplate, 0, 41, 21, 40),
            downMiddle = new TextureRegion(buttonTemplate, 22, 41, 1, 40),
            downRight = new TextureRegion(buttonTemplate, 24, 41, 21, 40);
    String text;
    Runnable onClick;

    public ButtonActor(String text, final Runnable onClick){
        this.text = text;
        this.onClick = onClick;
        setSize(188, 40);
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!isDown) clickDown.play();
                isDown = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        isDown = false;
                        clickUp.play();
                        onClick.run();
                    }
                }, coolDown);
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion left, middle, right;
        Color textColor;
        if(isDown){
            left = downLeft;
            middle = downMiddle;
            right = downRight;
            textColor = DoReMi.Palette.gray;
        }else{
            left = upLeft;
            middle = upMiddle;
            right = upRight;
            textColor = DoReMi.Palette.black;
        }
        batch.setColor(1, 1, 1, 1);
        batch.draw(left, getX(), getY());
        batch.draw(middle, getX() + left.getRegionWidth(), getY(), getWidth() - left.getRegionWidth() - right.getRegionWidth(), getHeight());
        batch.draw(right, getX() + getWidth() - right.getRegionWidth(), getY());
        DoReMi.Fonts.normal.setColor(textColor);
        DoReMi.Fonts.normal.draw(batch, text, getX(), getY() + getHeight()/2 + 6, getWidth(), Align.center, false);
    }

    @Override
    public boolean remove() {
        buttonTemplate.dispose();
        clickDown.dispose();
        clickUp.dispose();
        return super.remove();
    }
}
