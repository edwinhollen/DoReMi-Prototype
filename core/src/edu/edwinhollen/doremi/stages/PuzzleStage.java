package edu.edwinhollen.doremi.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.edwinhollen.doremi.*;

import java.util.List;

/**
 * Created by Edwin on 10/22/15
 */
public class PuzzleStage extends BaseStage {
    Actor listenButton;

    public PuzzleStage(Viewport viewport, Batch batch) {
        super(viewport, batch, DoReMi.Palette.gray);

        Puzzle p = new Puzzle(Puzzle.RangeDifficulty.easy, Puzzle.NoteDiversity.low);
        System.out.println(p.toString());

        listenButton = new ListenButtonActor();
        listenButton.setPosition(16, viewport.getWorldHeight() - listenButton.getHeight() - 16);
        addActor(listenButton);

        // add solution notes actors
        List<Note> solutionNotes = p.getSolutionNotes();
        final int last = solutionNotes.size() - 1;
        for(int i = 0; i < solutionNotes.size(); i++){
            NotePieceActor actorToAdd;
            if(i == 0){
                actorToAdd = new NotePieceActor(NotePieceActor.NotePieceOrientation.left, solutionNotes.get(i));
            }else if(i == solutionNotes.size() - 1){
                System.out.println("add right");
                actorToAdd = new NotePieceActor(NotePieceActor.NotePieceOrientation.right, solutionNotes.get(i));
            }else{
                actorToAdd = new NotePieceActor(NotePieceActor.NotePieceOrientation.middle, solutionNotes.get(i));

            }
            actorToAdd.setPosition(viewport.getWorldWidth() / 2 + Pick.integer(-40, 40), viewport.getWorldHeight() / 2 + Pick.integer(-40, 40));
            actorToAdd.addAction(Actions.moveBy(Pick.integer(-30, 30), Pick.integer(-30, 30), 1.0f, Interpolation.fade));
            addActor(actorToAdd);
        }

    }

    public static class ListenButtonActor extends Actor{
        boolean isDown = false;
        private Texture tx = new Texture(Gdx.files.internal("button_hear.png"));
        private TextureRegion
            regionUp = new TextureRegion(tx, 0, 0, 51, 56),
            regionDown = new TextureRegion(tx, 0, 56, 51, 56);

        public ListenButtonActor(){
            setSize(regionUp.getRegionWidth(), regionUp.getRegionHeight());
            addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    isDown = true;
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    isDown = false;
                }
            });
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            TextureRegion tr = isDown ? regionDown : regionUp;
            batch.draw(tr, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

        }
    }
}
