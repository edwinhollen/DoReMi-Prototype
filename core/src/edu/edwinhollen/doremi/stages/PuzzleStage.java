package edu.edwinhollen.doremi.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.edwinhollen.doremi.*;

import java.util.*;

/**
 * Created by Edwin on 10/22/15
 */
public class PuzzleStage extends BaseStage {
    Actor listenButton;
    Group solutionSlotActors;
    Group notePieceActors;
    Puzzle p;
    AssetDescriptor<TextureAtlas> spriteSheet;
    AssetDescriptor<Sound> clickUp, clickDown, pop, yeah;
    AssetDescriptor<Texture> outlines, hearButton, notePiecesSpread, noteHead;
    AssetManager assetManager;

    Options options;

    @Override
    public void dispose() {
        super.dispose();
        assetManager.dispose();
    }

    @Override
    public void draw() {
        if(assetManager.update()) super.draw();
    }

    public PuzzleStage(Viewport viewport, Batch batch) {
        super(viewport, batch, DoReMi.Palette.gray);

        // get user options
        this.options = new Options();

        // generate puzzle based on user options
        // p = new Puzzle(this.options.getRangeDifficulty(), this.options.getNoteDiversity());
        List<Note> testNotes = new LinkedList<>();
        for(Chromatic c : Chromatic.values()){
            testNotes.add(new Note(c, 2));
        }
        p = new Puzzle(testNotes, new LinkedList<Note>());
        // p = new Puzzle(new Scale(Chromatic.C_NATURAL, 2, ScalePattern.MINOR).getNotes(), new LinkedList<Note>());
        System.out.println(p.toString());

        // initialize asset manager
        assetManager = new AssetManager();

        // load sprite sheet
        spriteSheet = new AssetDescriptor<TextureAtlas>(Gdx.files.internal("pack.pack"), TextureAtlas.class);
        assetManager.load(spriteSheet);
        assetManager.finishLoading();

        // load note sounds
        for(Note n : p.getAllNotes()){
            assetManager.load(n.getAssetDescriptor());
        }

        // load ui sounds
        clickDown = new AssetDescriptor<>("click_down.mp3", Sound.class);
        assetManager.load(clickDown);
        clickUp = new AssetDescriptor<>("click_up.mp3", Sound.class);
        assetManager.load(clickUp);
        pop = new AssetDescriptor<>("pop.mp3", Sound.class);
        assetManager.load(pop);
        yeah = new AssetDescriptor<>("yeah.mp3", Sound.class);
        assetManager.load(yeah);

        listenButton = new ListenButtonActor();
        listenButton.setPosition(32, viewport.getWorldHeight() - listenButton.getHeight() - 16);
        addActor(listenButton);

        // add solution slots
        solutionSlotActors = new Group();
        // slot 0
        SolutionSlotActor ss0 = new SolutionSlotActor(NotePieceOrientation.LEFT, 0);
        ss0.setPosition(0, 0);
        solutionSlotActors.addActor(ss0);
        // slot 1
        SolutionSlotActor ss1 = new SolutionSlotActor(NotePieceOrientation.MIDDLE, 1);
        ss1.setPosition(51, 0);
        solutionSlotActors.addActor(ss1);
        // slot 2
        SolutionSlotActor ss2 = new SolutionSlotActor(NotePieceOrientation.MIDDLE, 2);
        ss2.setPosition(102, 0);
        solutionSlotActors.addActor(ss2);
        // slot 3
        SolutionSlotActor ss3 = new SolutionSlotActor(NotePieceOrientation.RIGHT, 3);
        ss3.setPosition(153, 0);
        solutionSlotActors.addActor(ss3);

        solutionSlotActors.setPosition(108, viewport.getWorldHeight() - 46 - 16);

        addActor(solutionSlotActors);


        // add solution notes actors
        notePieceActors = new Group();
        List<Note> solutionNotes = p.getSolutionNotes();
        for(int i = 0; i < solutionNotes.size(); i++){
            NotePieceActor actorToAdd;
            NotePieceOrientation orientation;

            if(i == 0){
                orientation = NotePieceOrientation.LEFT;
            }else if(i == solutionNotes.size() - 1){
                orientation = NotePieceOrientation.RIGHT;
            }else{
                orientation = NotePieceOrientation.MIDDLE;
            }

            actorToAdd = new NotePieceActor(orientation, solutionNotes.get(i), i);
            actorToAdd.setPosition(viewport.getWorldWidth() / 2 + Pick.integer(-40, 40), viewport.getWorldHeight() / 2 - Pick.integer(70));
            actorToAdd.addAction(Actions.moveBy(Pick.integer(-30, 30), Pick.integer(-30, 30), 1.0f, Interpolation.fade));
            actorToAdd.setZIndex(0);
            notePieceActors.addActor(actorToAdd);
        }

        // add extra notes actors
        for(Note extraNote : p.getExtraNotes()){
            NotePieceActor extra = new NotePieceActor(Pick.pick(NotePieceOrientation.values()), extraNote);
            extra.setPosition(viewport.getWorldWidth() / 2 + Pick.integer(-40, 40), viewport.getWorldHeight() / 2 - Pick.integer(70));
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
            if(!proposedSolution.get(i).equals(p.getSolutionNotes().get(i))){
                System.out.println("Note in slot "+i+" is incorrect");
                return false;
            }
        }
        System.out.println("You solved it! Yay!");
        assetManager.get(yeah).play(0.5f);

        for(int i = 0; i < solutionSlotActors.getChildren().size; i++){
            final SolutionSlotActor ssa = (SolutionSlotActor) solutionSlotActors.getChildren().get(i);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    ssa.occupiedBy.addAction(Actions.forever(Actions.sequence(Actions.moveBy(0, 5f, 0.25f), Actions.moveBy(0, -5f, 0.25f))));
                }
            }, 0.2f * i);

        }

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                DoReMi.changeStage(PuzzleStage.class);
            }
        }, 5.0f);
        return true;
    }

    /**
     * Actors
     */

    public class ListenButtonActor extends Actor{
        boolean isDown = false;
        /*private TextureRegion up = new TextureRegion(
                assetManager.get(spriteSheet).createSprite("button_hear"),
                0,
                0,
                assetManager.get(hearButton).getWidth(),
                assetManager.get(hearButton).getHeight()
        );
        */
        private Sprite up = assetManager.get(spriteSheet).createSprite("button_hear");

        public ListenButtonActor(){
            setSize(up.getWidth(), up.getHeight());
            setOrigin(Align.center);
            addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchDown(event, x, y, pointer, button);
                    addAction(Actions.repeat(3, Actions.sequence(Actions.rotateTo(-5f, 0.2f), Actions.rotateTo(5f, 0.2f), Actions.rotateTo(0f, 0.1f))));
                    for(int i = 0; i < p.getSolutionNotes().size(); i++){
                        final int finalI = i;
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                assetManager.get(p.getSolutionNotes().get(finalI).getAssetDescriptor()).play();
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
            TextureRegion tr = up;
            batch.draw(tr, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

        }
    }

    public class NotePieceActor extends Actor {
        /*
        final TextureRegion
            closed_closed = new TextureRegion(assetManager.get(notePiecesSpread), 0, 0, 52, 46),
            // closed_female = new TextureRegion(assetManager.get(notePiecesSpread), 0, 46, 52, 46),
            female_closed = new TextureRegion(assetManager.get(notePiecesSpread), 0, 92, 52, 46),
            female_male = new TextureRegion(assetManager.get(notePiecesSpread), 0, 138, 58, 46),
            closed_male = new TextureRegion(assetManager.get(notePiecesSpread), 0, 184, 58, 46);
            */
        private Sprite
            closed_closed = assetManager.get(spriteSheet).createSprite("notepiece_closed_closed"),
            female_closed = assetManager.get(spriteSheet).createSprite("notepiece_female_closed"),
            female_male = assetManager.get(spriteSheet).createSprite("notepiece_female_male"),
            closed_male = assetManager.get(spriteSheet).createSprite("notepiece_closed_male");
        final NotePieceOrientation orientation;
        final Note note;
        // final Action wiggleAction = Actions.forever(Actions.sequence(Actions.rotateBy(5.0f, 0.5f), Actions.rotateBy(-5f, 0.5f)));
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
                    NotePieceActor npa = (NotePieceActor) event.getTarget();
                    if(!dragged && notePieceActorIsInASolutionSlot(npa)){
                        assetManager.get(pop).play();
                        evictNotePieceFromSolutionSlot(npa);
                    }
                    dragged = true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    if(!dragged){
                        assetManager.get(note.getAssetDescriptor()).play();
                        addAction(Actions.sequence(Actions.scaleTo(0.9f, 0.9f, 0.05f), Actions.scaleTo(1.0f, 1.0f, 0.2f)));
                    }
                    dragged = false;
                    Vector2 myVector = new Vector2(getX(), getY());
                    for(Actor a : solutionSlotActors.getChildren()){
                        SolutionSlotActor ssa = (SolutionSlotActor) a;
                        Vector2 ssaVector = new Vector2(solutionSlotActors.getX() + ssa.getX(), solutionSlotActors.getY() + ssa.getY());
                        if(ssa.isOccupied() && ssa.occupiedBy == event.getTarget()){
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
                            assetManager.get(clickDown).play(1.0f, 0.8f, 1.0f);
                            assetManager.get(clickDown).play(1.0f, 1.0f, 1.0f);
                            break;
                        }
                    }
                }
            });
        }

        public boolean notePieceActorIsInASolutionSlot(NotePieceActor npa){
            for(Actor a : solutionSlotActors.getChildren()){
                SolutionSlotActor ssa = (SolutionSlotActor) a;
                if(ssa.isOccupied() && ssa.occupiedBy.equals(npa)) return true;
            }
            return false;
        }

        public void evictNotePieceFromSolutionSlot(NotePieceActor npa){
            for(Actor a : solutionSlotActors.getChildren()){
                SolutionSlotActor ssa = (SolutionSlotActor) a;
                if(ssa.isOccupied() && ssa.occupiedBy.equals(npa)){
                    ssa.evict();
                    return;
                }
            }
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
                case LEFT:
                    tr = closed_male;
                    break;
                case MIDDLE:
                    tr = female_male;
                    break;
                case RIGHT:
                    tr = female_closed;
                    break;
                default:
                    tr = closed_closed;
            }

            setSize(tr.getRegionWidth(), tr.getRegionHeight());
            setOrigin(Align.center);

            if(dragged){
                // draw shadow
                batch.setColor(DoReMi.Palette.black.r, DoReMi.Palette.black.g, DoReMi.Palette.black.b, 0.5f);
                batch.draw(tr, getX(), getY() - 3, getOriginX(), getOriginY() - 3, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }

            batch.setColor(1, 1, 1, 1);
            batch.draw(tr, Math.round(getX()), Math.round(getY()), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

            // batch.draw(noteHead, getX() + 8, ((getY() + 4) + Scale.indexOf(note) % 2 + Scale.indexOf(note) * 2) - noteHead.getRegionHeight() / 2, getOriginX(), getOriginY(), noteHead.getRegionWidth(), noteHead.getRegionHeight(), getScaleX(), getScaleY(), getRotation());
            float noteY;
            boolean drawFlat = false;
            boolean drawSharp = false;
            boolean drawNoteLine = false;
            switch(this.note.getChromatic()){
                case C_NATURAL:
                    noteY = 0;
                    drawNoteLine = true;
                    break;
                case C_SHARP:
                    noteY = 0;
                    drawSharp = true;
                    drawNoteLine = true;
                    break;
                case D_NATURAL:
                    noteY = 2;
                    break;
                case E_FLAT:
                    noteY = 5;
                    drawFlat = true;
                    break;
                case E_NATURAL:
                    noteY = 5;
                    break;
                case F_NATURAL:
                    noteY = 9;
                    break;
                case F_SHARP:
                    noteY = 9;
                    drawSharp = true;
                    break;
                case G_NATURAL:
                    noteY = 13;
                    break;
                case A_FLAT:
                    noteY = 17;
                    drawFlat = true;
                    break;
                case A_NATURAL:
                    noteY = 17;
                    break;
                case B_FLAT:
                    noteY = 20;
                    drawFlat = true;
                    break;
                case B_NATURAL:
                    noteY = 20;
                    break;
                default:
                    noteY = -1;
            }

            final float absNoteX = Math.round(getX() + 16);
            final float absNoteY = Math.round(getY() + (note.getOctave() - 2) * 22) + Math.round(noteY);

            batch.draw(assetManager.get(spriteSheet).createSprite("note"), absNoteX, absNoteY);
            if(drawNoteLine){
                batch.draw(assetManager.get(spriteSheet).createSprite("noteline"), absNoteX - 2, absNoteY);
            }

            if(drawFlat){
                batch.draw(assetManager.get(spriteSheet).createSprite("flat"), absNoteX - 7, absNoteY);
            }else if(drawSharp){
                batch.draw(assetManager.get(spriteSheet).createSprite("sharp"), absNoteX - 7, absNoteY);
            }

            if(false) {
                DoReMi.Fonts.normal.setColor(DoReMi.Palette.black);
                DoReMi.Fonts.normal.draw(batch, this.note.toString(), getX() + getWidth() / 2 - 10, getY() + getHeight() / 2, getWidth(), Align.center, false);
            }
        }
    }

    public class SolutionSlotActor extends Actor{
        // static final Texture outlines = new Texture(Gdx.files.internal("note_pieces_together_outlines.png"));
        final Sprite
                left = assetManager.get(spriteSheet).createSprite("notepiece_outline_closed_male"),
                middle = assetManager.get(spriteSheet).createSprite("notepiece_outline_female_male"),
                right = assetManager.get(spriteSheet).createSprite("notepiece_outline_female_closed");

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
            Sprite s;
            switch(this.orientation){
                case LEFT:
                    s = left;
                    break;
                case MIDDLE:
                    s = middle;
                    break;
                default:
                    s = right;
                    break;
            }
            setSize(s.getWidth(), s.getHeight());
            batch.draw(s, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }

        public void occupy(NotePieceActor notePieceActor){
            this.occupiedBy = notePieceActor;
            checkSolved();
        }

        public void evict(){
            this.occupiedBy = null;
            System.out.println("eviction");
        }

        public boolean isOccupied(){
            return this.occupiedBy != null;
        }
    }

}
