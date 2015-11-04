package edu.edwinhollen.doremi.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.edwinhollen.doremi.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Edwin on 10/22/15
 */
public class PuzzleStage extends BaseStage {
    Actor listenButton;
    Group solutionSlotActors;
    Group notePieceActors;
    Puzzle p;
    Sound clickUp, clickDown;

    final Texture outlines = new Texture(Gdx.files.internal("note_pieces_together_outlines.png"));

    public PuzzleStage(Viewport viewport, Batch batch) {
        super(viewport, batch, DoReMi.Palette.gray);

        // load sounds
        clickDown = Gdx.audio.newSound(Gdx.files.internal("click_down.mp3"));
        clickUp = Gdx.audio.newSound(Gdx.files.internal("click_up.mp3"));

        p = new Puzzle(Puzzle.RangeDifficulty.easy, Puzzle.NoteDiversity.low);

        System.out.println(p.toString());



        listenButton = new ListenButtonActor();
        listenButton.setPosition(16, viewport.getWorldHeight() - listenButton.getHeight() - 16);
        addActor(listenButton);

        // add solution slots
        solutionSlotActors = new Group();
        // slot 0
        SolutionSlotActor ss0 = new SolutionSlotActor(NotePieceOrientation.left, 0);
        ss0.setPosition(0, 0);
        solutionSlotActors.addActor(ss0);
        // slot 1
        SolutionSlotActor ss1 = new SolutionSlotActor(NotePieceOrientation.middle, 1);
        ss1.setPosition(51, 0);
        solutionSlotActors.addActor(ss1);
        // slot 2
        SolutionSlotActor ss2 = new SolutionSlotActor(NotePieceOrientation.middle, 2);
        ss2.setPosition(102, 0);
        solutionSlotActors.addActor(ss2);
        // slot 3
        SolutionSlotActor ss3 = new SolutionSlotActor(NotePieceOrientation.right, 3);
        ss3.setPosition(153, 0);
        solutionSlotActors.addActor(ss3);

        solutionSlotActors.setPosition(72, viewport.getWorldHeight() - 46 - 16);

        addActor(solutionSlotActors);


        // add solution notes actors
        notePieceActors = new Group();
        List<Note> solutionNotes = p.getSolutionNotes();
        for(int i = 0; i < solutionNotes.size(); i++){
            NotePieceActor actorToAdd;
            NotePieceOrientation orientation;

            if(i == 0){
                orientation = NotePieceOrientation.left;
            }else if(i == solutionNotes.size() - 1){
                orientation = NotePieceOrientation.right;
            }else{
                orientation = NotePieceOrientation.middle;
            }

            actorToAdd = new NotePieceActor(orientation, solutionNotes.get(i), i);
            actorToAdd.setPosition(viewport.getWorldWidth() / 2 + Pick.integer(-40, 40), viewport.getWorldHeight() / 2 + Pick.integer(-40, 40));
            actorToAdd.addAction(Actions.moveBy(Pick.integer(-30, 30), Pick.integer(-30, 30), 1.0f, Interpolation.fade));
            actorToAdd.setZIndex(0);
            notePieceActors.addActor(actorToAdd);
        }

        // add extra notes actors
        for(Note extraNote : p.getExtraNotes()){
            NotePieceActor extra = new NotePieceActor(Pick.pick(NotePieceOrientation.values()), extraNote);
            extra.setPosition(viewport.getWorldWidth() / 2 + Pick.integer(-40, 40), viewport.getWorldHeight() / 2 + Pick.integer(-40, 40));
            extra.setZIndex(0);
            extra.addAction(Actions.moveBy(Pick.integer(-30, 30), Pick.integer(-30, 30), 1.0f, Interpolation.fade));
            notePieceActors.addActor(extra);
        }

        addActor(notePieceActors);
    }

    protected boolean checkSolved(){
        System.out.println("checking if solved");
        List<Note> proposedSolution = new LinkedList<>();
        for(Actor a : solutionSlotActors.getChildren()){
            SolutionSlotActor ssa = (SolutionSlotActor) a;

            if(!ssa.isOccupied()){
                System.out.println("ssa #"+ssa.solutionSlot+" is not occupied, not solved");
                return false;
            }
            /*
            if(!ssa.occupiedBy.note.equals(p.getSolutionNotes().get(ssa.solutionSlot))){
                System.out.println("the note in slot "+ssa.solutionSlot+" is not correct, not solved");
                return false;
            }
            */
            proposedSolution.add(ssa.occupiedBy.getNote());
        }
        System.out.println("Proposed solution "+proposedSolution.toString());
        for(int i = 0; i < proposedSolution.size(); i++){
            String status;
            if(!proposedSolution.get(i).equals(p.getSolutionNotes().get(i))){
                System.out.println("Note in slot "+i+" is incorrect");
                return false;
            }
        }
        System.out.println("You solved it! Yay!");
        return true;
    }

    /**
     * Actors
     */

    public class ListenButtonActor extends Actor{
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
                    super.touchDown(event, x, y, pointer, button);
                    isDown = true;
                    for(int i = 0; i < p.getSolutionNotes().size(); i++){
                        final int finalI = i;
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                Notes.play(p.getSolutionNotes().get(finalI));
                            }
                        }, 0.5f * i);
                    }
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
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

    public class NotePieceActor extends Actor {
        final Texture notePieceTemplate = new Texture(Gdx.files.internal("note_pieces_together.png"));
        final TextureRegion
            closed_closed = new TextureRegion(notePieceTemplate, 0, 0, 52, 46),
            closed_female = new TextureRegion(notePieceTemplate, 0, 46, 52, 46),
            female_closed = new TextureRegion(notePieceTemplate, 0, 92, 52, 46),
            female_male = new TextureRegion(notePieceTemplate, 0, 138, 58, 46),
            closed_male = new TextureRegion(notePieceTemplate, 0, 184, 58, 46);
        final NotePieceOrientation orientation;
        final Note note;
        final Action wiggleAction = Actions.forever(Actions.sequence(Actions.rotateBy(5.0f, 0.5f), Actions.rotateBy(-5f, 0.5f)));
        boolean dragged = false;
        final Integer solutionSlot;


        public NotePieceActor(NotePieceOrientation orientation, final Note note, Integer solutionSlot){
            this.orientation = orientation;
            this.note = note;
            this.solutionSlot = solutionSlot;

            addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchDown(event, x, y, pointer, button);
                    toFront();
                    return true;
                }

                @Override
                public void touchDragged(InputEvent event, float x, float y, int pointer) {
                    super.touchDragged(event, x, y, pointer);
                    moveBy(x - getWidth() / 2, y - getHeight() / 2);
                    dragged = true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    if(!dragged) Notes.play(note);
                    dragged = false;
                    Vector2 myVector = new Vector2(getX(), getY());
                    for(Actor a : solutionSlotActors.getChildren()){
                        SolutionSlotActor ssa = (SolutionSlotActor) a;
                        Vector2 ssaVector = new Vector2(solutionSlotActors.getX() + ssa.getX(), solutionSlotActors.getY() + ssa.getY());
                        if(ssa.isOccupied() && ssa.occupiedBy == event.getRelatedActor()){
                            if(myVector.dst(ssaVector) >= 20){
                                ssa.evict();
                            }else{
                                continue;
                            }
                        }

                        // snap into place
                        if(myVector.dst(ssaVector) < 20){
                            moveBy(ssaVector.x - getX(), ssaVector.y - getY());
                            ssa.occupy((NotePieceActor) event.getTarget());
                            clickDown.play(1.0f, 0.8f, 1.0f);
                            clickDown.play(1.0f, 1.0f, 1.0f);
                            break;
                        }
                    }
                }
            });
        }

        public Note getNote() {
            return note;
        }

        public NotePieceActor(NotePieceOrientation orientation, final Note note){
            this(orientation, note, null);
        }

        @Override
        public void act(float delta) {
            super.act(delta);
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

            if(dragged){
                batch.setColor(DoReMi.Palette.black.r, DoReMi.Palette.black.g, DoReMi.Palette.black.b, 0.5f);
                batch.draw(tr, getX(), getY() - 3, getOriginX(), getOriginY() - 3, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }

            batch.setColor(1, 1, 1, 1);
            batch.draw(tr, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

            DoReMi.Fonts.normal.setColor(DoReMi.Palette.black);
            DoReMi.Fonts.normal.draw(batch, this.note.toString(), getX() + getWidth() / 2 - 10, getY() + getHeight() / 2, getWidth(), Align.center, false);
        }
    }

    public class SolutionSlotActor extends Actor{
        // static final Texture outlines = new Texture(Gdx.files.internal("note_pieces_together_outlines.png"));
        final TextureRegion
                left = new TextureRegion(outlines, 0, 184, 58, 46),
                middle = new TextureRegion(outlines, 0, 138, 58, 46),
                right = new TextureRegion(outlines, 0, 92, 52, 46);

        NotePieceActor occupiedBy = null;
        final Integer solutionSlot;

        NotePieceOrientation orientation;
        public SolutionSlotActor(NotePieceOrientation orientation, Integer solutionSlot){
            this.orientation = orientation;
            this.solutionSlot = solutionSlot;
        }

        @Override
        public void act(float delta) {
            super.act(delta);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            TextureRegion tr;
            switch(this.orientation){
                case left:
                    tr = left;
                    break;
                case middle:
                    tr = middle;
                    break;
                default:
                    tr = right;
                    break;
            }
            setSize(tr.getRegionWidth(), tr.getRegionHeight());
            batch.draw(tr, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }

        public void occupy(NotePieceActor notePieceActor){
            this.occupiedBy = notePieceActor;
            checkSolved();
        }

        public void evict(){
            this.occupiedBy = null;
        }

        public boolean isOccupied(){
            return this.occupiedBy != null;
        }
    }

    public enum NotePieceOrientation{
        left,
        middle,
        right
    }
}
