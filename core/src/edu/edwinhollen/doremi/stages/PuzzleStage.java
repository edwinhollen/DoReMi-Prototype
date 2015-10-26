package edu.edwinhollen.doremi.stages;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.edwinhollen.doremi.*;

import java.util.List;

/**
 * Created by Edwin on 10/22/15
 */
public class PuzzleStage extends BaseStage {
    public PuzzleStage(Viewport viewport, Batch batch) {
        super(viewport, batch, DoReMi.Palette.gray);

        Puzzle p = new Puzzle(Puzzle.RangeDifficulty.easy, Puzzle.NoteDiversity.low);
        System.out.println(p.toString());
        /*
        final List<Note> solutionNotes = p.getSolutionNotes();
        for(int i = 0; i < solutionNotes.size(); i++){
            final int finalI = i;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    Sound s = Gdx.audio.newSound(Gdx.files.internal(String.format("instruments/piano/%s.mp3", solutionNotes.get(finalI).toString())));
                    s.play();
                }
            }, 1.0f * i);
        }
        */


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
}
