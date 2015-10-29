package edu.edwinhollen.doremi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Edwin on 10/26/15
 */
public class NotePieceActor extends Actor {
    static final Texture notePieceTemplate = new Texture(Gdx.files.internal("note_pieces_together.png"));
    static final TextureRegion
        closed_closed = new TextureRegion(notePieceTemplate, 0, 0, 52, 46),
        closed_female = new TextureRegion(notePieceTemplate, 0, 46, 52, 46),
        female_closed = new TextureRegion(notePieceTemplate, 0, 92, 52, 46),
        female_male = new TextureRegion(notePieceTemplate, 0, 138, 58, 46),
        closed_male = new TextureRegion(notePieceTemplate, 0, 184, 58, 46);
    final NotePieceOrientation orientation;
    final Note note;
    final Action wiggleAction = Actions.forever(Actions.sequence(Actions.rotateBy(5.0f, 0.5f), Actions.rotateBy(-5f, 0.5f)));

    static int zIndex = 0;

    public NotePieceActor(NotePieceOrientation orientation, final Note note){
        this.orientation = orientation;
        this.note = note;

        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Notes.play(note);
                setZIndex(++zIndex);
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                moveBy(x - getWidth() / 2, y - getHeight() / 2);

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion tr;
        switch(this.orientation){
            case left:
                tr = closed_male;
                break;
            case middle:
                tr = female_male;
                break;
            case right:
                tr = female_closed;
                break;
            default:
                tr = closed_closed;
        }

        setSize(tr.getRegionWidth(), tr.getRegionHeight());
        setOrigin(Align.center);
        batch.draw(tr, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

        DoReMi.Fonts.normal.setColor(DoReMi.Palette.black);
        DoReMi.Fonts.normal.draw(batch, this.note.toString(), getX() + getWidth() / 2 - 10, getY() + getHeight() / 2, getWidth(), Align.center, false);
    }

    public enum NotePieceOrientation{
        left,
        middle,
        right
    }
}
