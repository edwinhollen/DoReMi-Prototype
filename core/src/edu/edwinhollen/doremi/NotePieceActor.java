package edu.edwinhollen.doremi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Edwin on 10/26/15
 */
public class NotePieceActor extends Actor {
    static final Texture notePieceTemplate = new Texture(Gdx.files.internal("note_piece_template.png"));
    static final TextureRegion
        left_closed = new TextureRegion(notePieceTemplate, 0, 0, 26, 46),
        left_female = new TextureRegion(notePieceTemplate, 27, 0, 26, 46),
        right_closed = new TextureRegion(notePieceTemplate, 54, 0, 26, 46),
        right_male = new TextureRegion(notePieceTemplate, 81, 0, 32, 46);
    static final TextureRegion staff = new TextureRegion(new Texture(Gdx.files.internal("staff.png")));
    final NotePieceOrientation orientation;
    final Note note;

    public NotePieceActor(NotePieceOrientation orientation, final Note note){
        this.orientation = orientation;
        this.note = note;

        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Notes.play(note);
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                setPosition(getX() + x - getWidth(), getY() + y - getHeight());
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion leftSide, rightSide;
        switch(this.orientation){
            case left:
                leftSide = left_closed;
                rightSide = right_closed;
                break;
            case middle:
                leftSide = left_female;
                rightSide = right_male;
                break;
            case right:
                leftSide = left_female;
                rightSide = right_closed;
                break;
            default:
                leftSide = left_closed;
                rightSide = right_closed;
        }

        setSize(leftSide.getRegionWidth() + rightSide.getRegionWidth(), leftSide.getRegionHeight());
        setOrigin(Align.center);
        batch.draw(leftSide, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX()/2, getScaleY(), getRotation());
        batch.draw(rightSide, getX() + leftSide.getRegionWidth(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX()/2, getScaleY(), getRotation());

        DoReMi.Fonts.normal.setColor(DoReMi.Palette.black);
        DoReMi.Fonts.normal.draw(batch, this.note.toString(), getX() + getWidth() / 2 - 10, getY() + getHeight() / 2, getWidth(), Align.center, false);
    }

    public enum NotePieceOrientation{
        left,
        middle,
        right
    }
}
