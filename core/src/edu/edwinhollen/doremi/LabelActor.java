package edu.edwinhollen.doremi;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Edwin on 10/22/15
 */
public class LabelActor extends Actor {
    public String text;
    int align;
    public LabelActor(String text, Color color, int align){
        this.text = text;
        setColor(color);
        this.align = align;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        DoReMi.Fonts.normal.setColor(getColor());
        DoReMi.Fonts.normal.draw(batch, this.text, getX(), getY(), getWidth(), this.align, false);

    }
}
