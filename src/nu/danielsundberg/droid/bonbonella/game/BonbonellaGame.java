package nu.danielsundberg.droid.bonbonella.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import nu.danielsundberg.droid.bonbonella.BonbonellaGameController;
import nu.danielsundberg.droid.bonbonella.game.actors.Bonbonella;
import nu.danielsundberg.droid.bonbonella.game.levels.Level;
import nu.danielsundberg.droid.bonbonella.game.levels.Level1;
import nu.danielsundberg.droid.bonbonella.screens.BonbonellaGameScreen;

/**
 * Game handler for an instance of a bonbonella game
 */
public class BonbonellaGame extends Stage implements ContactListener {

    public final static Vector2 GRAVITY = new Vector2(0, -9.82f);

    private BonbonellaGameController controller;

    private World world;
    private Bonbonella bonbonella;
    private Level level;
    private GameState gameState;

    public static float UNIT_WIDTH = BonbonellaGameScreen.VIRTUAL_WIDTH/160; // 6.4 meters width
    public static float UNIT_HEIGHT = BonbonellaGameScreen.VIRTUAL_HEIGHT/160; // 3.75 meters height

    private static final float WORLD_TO_BOX=0.01f;
    private static final float BOX_TO_WORLD=100f;

    public static float convertToBox(float value){
        return value*WORLD_TO_BOX;
    }

    public static float convertToWorld(float value) {
        return value*BOX_TO_WORLD;
    }

    private final float GAMETOBOXCONSTANT = (Gdx.graphics.getWidth()/Gdx.graphics.getHeight()*100);

    public BonbonellaGame(BonbonellaGameController controller) {
        this.controller = controller;
        world = new World(GRAVITY, true);
        world.setContactListener(this);
        world.setAutoClearForces(true);


        bonbonella = new Bonbonella(world, controller);
        addActor(bonbonella);

        gameState = GameState.PLAY;
    }

    public Bonbonella getBonbonella() {
        return this.bonbonella;
    }

    public Level getLevel() {
        return this.level;
    }

    public World getWorld() {
        return this.world;
    }

    @Override
    public void act(float timeSinceLastRender) {
        super.act(timeSinceLastRender);



        if (level == null) {
            level = new Level1(world, controller);
            addActor(level);
        } else {
            if (bonbonella.getX() > level.getFinishX()) {
                gameState = GameState.LEVEL;
            }
        }

        if (gameState == GameState.LEVEL) {
            level = level.getNextLevel();
            gameState = GameState.PLAY;
        }

        if (gameState == GameState.PLAY) {

            if(Gdx.input.isTouched()) {
                float touchX = Gdx.input.getX();
                float touchY = Gdx.input.getY();
                if(touchX < Gdx.graphics.getWidth()/2) {
                    bonbonella.addForce(convertToBox(-10f), 0);
                } else {
                    bonbonella.addForce(convertToBox(10f),0);
                }
                if(touchY < Gdx.graphics.getHeight()/2) {
                    bonbonella.addImpulse(0, convertToBox(20f));
                }
            }

            world.step(1 / 60f, 6, 2);
            world.clearForces();
            bonbonella.act(timeSinceLastRender);
            level.act(timeSinceLastRender);
        }

        if (bonbonella.getLives() == 0) {
            gameState = GameState.GAMEOVER;

        }

    }


    public GameState getGameState() {
        return this.gameState;
    }

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    public enum GameState {
        PLAY, LEVEL, GAMEOVER
    }


}
